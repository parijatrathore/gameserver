package com.junglee.assignment.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Table;

@Entity
@Table(name = "compilations_category")
public class GameParticipation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6189007448894052239L;
	
	public enum GameState {
		WIN,
		LOSS,
		ABANDONE,
		PLAY

	}
	
	private Integer id;
	private Player player;
	private GameTable table;
	private Date joinTime;
	private Date finishTime;
	private GameState gameState;
	

	public GameParticipation(Player player, GameTable table, Date joinTime, Date finishTime, GameState gameState) {
		this.player = player;
		this.table = table;
		this.joinTime = joinTime;
		this.finishTime = finishTime;
		this.gameState = gameState;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "table_id")
	public GameTable getTable() {
		return table;
	}

	public void setTable(GameTable table) {
		this.table = table;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "join_time", nullable = false, length = 19, updatable = false, insertable = false)
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "finish_time", nullable = false, length = 19, updatable = false, insertable = false)
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "game_state")
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	
	
}
