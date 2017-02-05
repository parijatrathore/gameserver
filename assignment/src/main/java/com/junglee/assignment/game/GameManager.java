package com.junglee.assignment.game;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import com.junglee.assignment.GameServerMain;
import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.dao.PlayerDAOcheck;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.entity.GameTable.TableState;
import com.junglee.assignment.event.EventDispatcher;
import com.junglee.assignment.event.response.game.GameStatusResponse;
import com.junglee.assignment.event.table.TableAvailableEvent;
import com.junglee.assignment.event.table.TableBusyEvent;
import com.junglee.assignment.exception.InsuffcientTablePlayers;
import com.junglee.assignment.exception.TableNotAvailabelException;
import com.junglee.assignment.exception.TableNotFoundException;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.utils.JSONUtils;
import com.junglee.assignment.utils.Properties;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

@SuppressWarnings("unused")
public class GameManager {

	private final static Logger			LOG			= LoggerManager.GetLogger(GameServerMain.class.getName());

	private ThreadPoolExecutor			executor;
	private static final GameManager	_instance	= new GameManager();

	private GameManager() {
		executor = new ThreadPoolExecutor(5, 10, 120, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}

	public static GameManager getInstance() {
		return _instance;
	}

	public void destroyTable(TableDTO tableDTO) throws IOException {
		tableDTO.lockTable();
		tableDTO.updateTableState(TableState.DESTYROYED);
		tableDTO.unlockTable();
	}
	
	public void startTableGame(TableDTO dto) throws TableNotFoundException, TableNotAvailabelException, InsuffcientTablePlayers {
		executor.submit(new PlayTable(dto.getTableId()));
	}

	public class PlayTable implements Runnable {

		private TableDTO tableDTO;

		public PlayTable(int tableId) throws TableNotFoundException, TableNotAvailabelException, InsuffcientTablePlayers {
			tableDTO = (TableDTO) JungleeRedis.getInstance().get(tableId);

			if (tableDTO == null) {
				throw new TableNotFoundException("Invalid table id");
			}
			if (!TableState.WAITING_FOR_PLAYERS.name().equalsIgnoreCase(tableDTO.getState())) {
				throw new TableNotAvailabelException("Table already finished or in game");
			}
			if (tableDTO.getPlayers().size() < 3) {
				throw new InsuffcientTablePlayers("Cannot start game with less than 3 players");
			}
		}

		@Override
		public void run() {
			// set status response
			GameStatusResponse statusResponse = new GameStatusResponse(tableDTO.getTableId(), tableDTO.getPlayers().size());
			if (tableDTO.getPlayers().size() >= 3 && tableDTO.getPlayers().size() < 5) {
				// use same object but message is reset everytime
				statusResponse.setMesssage(Properties.FIVE_SECOND_MESSAGE);
				for (PlayerDTO dto : tableDTO.getPlayers().values()) {
					ClientConnection.getInstance().sendPacket(dto, statusResponse);
				}
				try {
					Thread.sleep(5000);
					if (tableDTO.getPlayers().size() < 3) {
						throw new InsuffcientTablePlayers("Cannot start game with less than 3 players");
					}
				} catch (InterruptedException e) {
					LOG.info("Wait for 5 second start interrupted");
					return;
				} catch (InsuffcientTablePlayers e) {
					LOG.info("Players left while waiting");
					return;
				}
			}
			if (tableDTO.getPlayers().size() >= 3) {
				try {
					tableDTO.lockTable();
					tableDTO.updateTableState(TableState.IN_GAME);
					// dispatch event to update TableManager
					EventDispatcher.getInstance().dispatch(new TableBusyEvent(tableDTO));
					statusResponse.setMesssage(Properties.GAME_STARTED_MESSAGE);
					for (PlayerDTO dto : tableDTO.getPlayers().values()) {
						ClientConnection.getInstance().sendPacket(dto, statusResponse);
					}
					Thread.sleep(60000);
					tableDTO.updateTableState(TableState.WAITING_FOR_PLAYERS);
					// dispatch event to update TableManager
					EventDispatcher.getInstance().dispatch(new TableAvailableEvent(tableDTO));
					statusResponse.setMesssage(Properties.GAME_ENDED_MESSAGE);
					for (PlayerDTO dto : tableDTO.getPlayers().values()) {
						ClientConnection.getInstance().sendPacket(dto, statusResponse);
						// update players game number
						dto.setGamesPlayed();
					}
				} catch (InterruptedException e) {
					LOG.info("Game play interrupted by System");
					e.printStackTrace();
					// call tablemanager and reset table
				} catch (IOException e) {
					LOG.info("Game play interrupted by System");
					e.printStackTrace();
					// call tablemanager and reset table
				} finally {
					tableDTO.unlockTable();
				}
			}
		}

	}

	

}
