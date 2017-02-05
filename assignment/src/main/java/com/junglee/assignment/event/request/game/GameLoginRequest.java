package com.junglee.assignment.event.request.game;

import javax.validation.constraints.NotNull;

import com.junglee.assignment.event.request.EventRequest;

public class GameLoginRequest extends EventRequest {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1063986155552728519L;

	@NotNull
	private String				username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
