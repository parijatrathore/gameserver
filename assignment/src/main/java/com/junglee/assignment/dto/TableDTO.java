package com.junglee.assignment.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.junglee.assignment.cache.redis.JungleeRedis;
import com.junglee.assignment.entity.GameParticipation;
import com.junglee.assignment.entity.GameTable;
import com.junglee.assignment.entity.Player;
import com.junglee.assignment.entity.GameTable.TableState;

public class TableDTO implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= -6003669359283845610L;
	private final ReentrantLock				_lock;

	private int								tableId;
	private volatile Map<String, PlayerDTO>	players;
	private int								activeplayerid;
	private String							winner;
	//mark the end game the value will be the winner id
	private String							endgame;
	// current table state
	private String							state;
	private Long							startTime;
	private Long							endTime;
	private Map<String, String>				gameParticipation;

	public TableDTO() {
		_lock = new ReentrantLock();
	}

	public TableDTO(GameTable gameTable) {
		players = new HashMap<>();
		gameParticipation = new HashMap<>();
		for (GameParticipation participation : gameTable.getParticipation()) {
			Player player = participation.getPlayer();
			gameParticipation.put(player.getName(), participation.getGameState().name());
			players.put(player.getName(), new PlayerDTO(player));
		}
		this.tableId = gameTable.getId();
		this.state = gameTable.getTableState().name();
		this.startTime = gameTable.getStartTime().getTime();
		this.endTime = gameTable.getEndTime().getTime();
		_lock = new ReentrantLock();
	}

	public TableDTO(int id, String state, long startTime, long endTime, Map<String, String> participation, Map<String, PlayerDTO> players) {
		this.players = players;
		this.gameParticipation = participation;
		this.tableId = id;
		this.state = state;
		this.startTime = startTime;
		this.endTime = endTime;
		_lock = new ReentrantLock();
	}

	public int getNextActivePlayer(String currentActivePlayer) {
		int currentPlyInx = getPlayerIndexByKey(currentActivePlayer);
		int activePlayer = 0;
		if (currentPlyInx < (players.size() - 1)) {
			++activePlayer;
		}

		return activePlayer;
	}

	public int getPlayerIndexByKey(String playerKey) {
		int pos = new ArrayList<String>(players.keySet()).indexOf(playerKey);
		return pos;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public Map<String, PlayerDTO> getPlayers() {
		return players;
	}

	public void setPlayers(Map<String, PlayerDTO> players) {
		this.players = players;
	}


	public int getActiveplayerid() {
		return activeplayerid;
	}

	public void setActiveplayerid(int activeplayerid) {
		this.activeplayerid = activeplayerid;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getEndgame() {
		return endgame;
	}

	public void setEndgame(String endgame) {
		this.endgame = endgame;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Map<String, String> getGameParticipation() {
		return gameParticipation;
	}

	public void setGameParticipation(Map<String, String> gameParticipation) {
		this.gameParticipation = gameParticipation;
	}

	public void lockTable() {
		this._lock.lock();
	}

	public void unlockTable() {
		this._lock.unlock();
	}

	public void updateTableState(TableState tableState) throws IOException {
		this._lock.lock();
		this.setState(tableState.name());
		JungleeRedis.getInstance().cacheObject(this.getTableId(), this);
		this._lock.unlock();
	}

}
