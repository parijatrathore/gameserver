package com.junglee.assignment.event;

public abstract class Event {
	public final Class<? extends Event> getEventType() {
		return this.getClass();
	}
}
