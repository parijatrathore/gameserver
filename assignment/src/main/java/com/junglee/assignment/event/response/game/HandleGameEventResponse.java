package com.junglee.assignment.event.response.game;

import com.junglee.assignment.event.response.EventResponse;

public class HandleGameEventResponse extends EventResponse {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4366942955199121564L;

	private String				username;
	private int					tableId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

}
