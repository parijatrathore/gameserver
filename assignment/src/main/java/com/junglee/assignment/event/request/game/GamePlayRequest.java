package com.junglee.assignment.event.request.game;

import com.junglee.assignment.event.request.EventRequest;

public class GamePlayRequest extends EventRequest {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1661442340516576345L;

	private String				username;
	private Integer				tableId;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getTableId() {
		return tableId;
	}

	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

}
