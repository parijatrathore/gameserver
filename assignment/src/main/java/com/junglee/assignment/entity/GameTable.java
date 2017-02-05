package com.junglee.assignment.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class GameTable implements Serializable{

	public enum TableState {
		
		WAITING_FOR_PLAYERS,
		IN_GAME,
		DESTYROYED

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8920104996466410815L;
	
	private Integer id;
	private TableState tableState;
	private Date startTime;
	private Date endTime;
	private Player playerWon;
	private List<GameParticipation> participation;
	
	public GameTable(){
		
	}

	public GameTable(TableState tableState, Date startTime, Date endTime) {
		this.tableState = tableState;
		this.startTime = startTime;
		this.endTime = endTime;
		participation = new ArrayList<>();
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

	@Enumerated(EnumType.STRING)
	@Column(name = "table_state")
	public TableState getTableState() {
		return tableState;
	}

	public void setTableState(TableState tableState) {
		this.tableState = tableState;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", nullable = false, length = 19, updatable = false, insertable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", nullable = false, length = 19, updatable = false, insertable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Transient
	public Player getPlayerWon() {
		return playerWon;
	}

	public void setPlayerWon(Player playerWon) {
		this.playerWon = playerWon;
	}

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.ALL }, mappedBy = "table")
	public List<GameParticipation> getParticipation() {
		return participation;
	}

	public void setParticipation(List<GameParticipation> participation) {
		this.participation = participation;
	}
	
	
	
}
