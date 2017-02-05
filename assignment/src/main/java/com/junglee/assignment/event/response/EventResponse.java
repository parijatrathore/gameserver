package com.junglee.assignment.event.response;

import java.io.Serializable;

public class EventResponse implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8669316781394354296L;

	private boolean				successful;

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
