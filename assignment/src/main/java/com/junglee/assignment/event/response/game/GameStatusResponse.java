package com.junglee.assignment.event.response.game;

import com.junglee.assignment.event.response.EventResponse;

public class GameStatusResponse extends EventResponse {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1148842745023021329L;

	private int					tableId;
	private int					playerNum;
	private String				messsage;
	
	public GameStatusResponse(){
		
	}

	public GameStatusResponse(int tableId, int playerNum) {
		super();
		this.tableId = tableId;
		this.playerNum = playerNum;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getMesssage() {
		return messsage;
	}

	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}

	
}
