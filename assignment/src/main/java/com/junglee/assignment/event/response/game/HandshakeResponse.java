package com.junglee.assignment.event.response.game;

import com.junglee.assignment.event.response.EventResponse;

public class HandshakeResponse extends EventResponse {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5950565128754455570L;

	private int					event;

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

}
