package com.junglee.assignment.game;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import com.junglee.assignment.GameServerMain;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.exception.InsuffcientTablePlayers;
import com.junglee.assignment.exception.TableCreateException;
import com.junglee.assignment.exception.TableNotAvailabelException;
import com.junglee.assignment.exception.TableNotFoundException;
import com.junglee.assignment.logger.LoggerManager;
import com.junglee.assignment.service.ITableService;
import com.junglee.assignment.service.impl.TableService;

public class TableManager {
	private final static Logger				LOG			= LoggerManager.GetLogger(GameServerMain.class.getName());

	private volatile Map<Integer, TableDTO>	tables;
	private volatile Map<Integer, TableDTO>	availableTables;
	private final GameResponseDispatcher	gameResponseDispatcher;
	private final ITableService				tableService;
	private static final TableManager		_instance	= new TableManager();

	private TableManager() {
		tables = new ConcurrentHashMap<Integer, TableDTO>();
		availableTables = new ConcurrentHashMap<>();
		gameResponseDispatcher = new GameResponseDispatcher();
		tableService = new TableService();
	}

	public static TableManager getInstance() {
		return _instance;
	}

	public GameResponseDispatcher getGameResponseDispatcher() {
		return gameResponseDispatcher;
	}

	/*public int getPlayerIndexByKey(int playerKey, int tableKey)
	{
		int pos = new ArrayList<Integer>(tables.get(tableKey).getPlayers().keySet()).indexOf(playerKey);
		return pos;
	}
	public PlayerDTO getPlayerByIndex(int inx, int tableKey)
	{
		List<PlayerDTO> l = new ArrayList<PlayerDTO>(tables.get(tableKey).getPlayers().values());
		PlayerDTO p = l.get(inx);
		return p;
	}*/

	public Map<Integer, TableDTO> getTables() {
		return tables;
	}

	public Map<Integer, TableDTO> getAvailableTables() {
		return availableTables;
	}

	public void setAvailableTables(Map<Integer, TableDTO> availableTables) {
		this.availableTables = availableTables;
	}

	public TableDTO getTable(int tableKey) {
		return tables.get(tableKey);
	}

	public TableDTO joinTable(PlayerDTO playerDTO)
			throws TableCreateException, IOException, TableNotFoundException, TableNotAvailabelException, InsuffcientTablePlayers {
		TableDTO dto;
		if (availableTables.isEmpty()) {
			// if no available tables create new and add to existing list
			dto = tableService.createTable(playerDTO);
			availableTables.put(dto.getTableId(), dto);
			tables.put(dto.getTableId(), dto);
		} else {
			// fetch the first available table and join the player on that
			dto = availableTables.values().iterator().next();
			tableService.joinTable(playerDTO, dto);
		}
		// start game if players are 3 or more
		if (dto.getPlayers().size() >= 3) {
			GameManager.getInstance().startTableGame(dto);
			availableTables.remove(dto.getTableId());
		}
		return dto;
	}

}
