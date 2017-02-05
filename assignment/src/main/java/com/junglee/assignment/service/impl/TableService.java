package com.junglee.assignment.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.dao.PlayerDAOcheck;
import com.junglee.assignment.dao.TableDAOcheck;
import com.junglee.assignment.dto.PlayerDTO;
import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.entity.GameParticipation;
import com.junglee.assignment.entity.GameParticipation.GameState;
import com.junglee.assignment.entity.GameTable;
import com.junglee.assignment.entity.GameTable.TableState;
import com.junglee.assignment.entity.Player;
import com.junglee.assignment.exception.TableCreateException;
import com.junglee.assignment.exception.TableNotAvailabelException;
import com.junglee.assignment.exception.TableNotFoundException;
import com.junglee.assignment.service.ITableService;
import com.junglee.assignment.utils.DateUtils;

public class TableService implements ITableService {

	private static AtomicInteger tableIdCounter = new AtomicInteger(0);

	@Override
	public TableDTO createTable(PlayerDTO player) throws TableCreateException, IOException {
		Map<String, String> gameParticipation = new HashMap<>();
		Map<String, PlayerDTO> players = new HashMap<>();
		PlayerDTO playerDTO = (PlayerDTO) JungleeRedis.getInstance().get(String.valueOf(player.getId()));
		//		List<GameParticipation> participants = new ArrayList<>();
		//		Player playerDAO = PlayerDAOcheck.getInstance().getById(playerDTO.getId());
		//		GameTable table = new GameTable(TableState.WAITING_FOR_PLAYERS, DateUtils.getCurrentTime(), DateUtils.getCurrentTime());
		//		participants.add(new GameParticipation(playerDAO, table, DateUtils.getCurrentTime(), DateUtils.getCurrentTime(), GameState.PLAY));
		//		table.setParticipation(participants);
		//		TableDAOcheck.getInstance().saveOrUpdate(table);
		gameParticipation.put(playerDTO.getUserName(), GameState.PLAY.name());
		players.put(playerDTO.getUserName(), playerDTO);
		TableDTO tableDTO = new TableDTO(GenerateUniqueId(), TableState.WAITING_FOR_PLAYERS.name(), DateUtils.getCurrentTime().getTime(), DateUtils.getCurrentTime().getTime(), gameParticipation, players);
		JungleeRedis.getInstance().cacheObject(String.valueOf(tableDTO.getTableId()), tableDTO);
		return tableDTO;
	}

	@Override
	public TableDTO getById(int tableId) throws IOException {
		TableDTO dto = (TableDTO) JungleeRedis.getInstance().get(String.valueOf(tableId));
		if (dto == null) {
			GameTable gameTable = TableDAOcheck.getInstance().getById(tableId);
			dto = new TableDTO(gameTable);
			JungleeRedis.getInstance().cacheObject(String.valueOf(tableId), dto);
		}
		return dto;
	}

	@Override
	public String getTableState(int tableId) throws TableNotFoundException {
		TableDTO dto = (TableDTO) JungleeRedis.getInstance().get(String.valueOf(tableId));
		if (dto == null)
			throw new TableNotFoundException("Table does not exist for " + tableId);
		return dto.getState();
	}

	@Override
	public boolean isTableAvailable(int tableId) throws TableNotFoundException {
		TableDTO dto = (TableDTO) JungleeRedis.getInstance().get(String.valueOf(tableId));
		if (dto == null)
			throw new TableNotFoundException("Table does not exist for " + tableId);
		return TableState.WAITING_FOR_PLAYERS.name().equalsIgnoreCase(dto.getState());
	}

	@Override
	public TableDTO joinTable(PlayerDTO playerDTO, TableDTO tableDTO) throws TableNotAvailabelException, IOException {
		if (!TableState.WAITING_FOR_PLAYERS.name().equals(tableDTO.getState())) {
			throw new TableNotAvailabelException("Table state " + tableDTO.getState());
		}
		if(tableDTO.getPlayers().size() == 5){
			throw new TableNotAvailabelException("Table is full");
		}
		// leave DB update for now
		tableDTO.lockTable();
		tableDTO.getPlayers().put(playerDTO.getUserName(), playerDTO);
		tableDTO.getGameParticipation().put(playerDTO.getUserName(), GameState.PLAY.name());
		JungleeRedis.getInstance().cacheObject(String.valueOf(tableDTO.getTableId()), tableDTO);
		tableDTO.unlockTable();
		return tableDTO;
	}

	private int GenerateUniqueId()
	{
		return tableIdCounter.incrementAndGet();
	}
}
