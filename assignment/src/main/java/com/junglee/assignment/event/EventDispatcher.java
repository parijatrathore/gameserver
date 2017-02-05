package com.junglee.assignment.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.junglee.assignment.event.listener.EventListener;

public final class EventDispatcher {

	private static final EventDispatcher															_instance		= new EventDispatcher();
	private static final Map<Class<? extends Event>, Collection<EventListener<? extends Event>>>	listenersDict	= new HashMap<Class<? extends Event>, Collection<EventListener<? extends Event>>>();

	private EventDispatcher() {

	}

	public static EventDispatcher getInstance() {
		return _instance;
	}

	public void registerListener(EventListener<? extends Event> listener) {
		Class<? extends Event> eventType = listener.getEventType();
		Collection<EventListener<? extends Event>> listeners = listenersDict.get(eventType);

		if (listeners == null) {
			listeners = new ArrayList<EventListener<? extends Event>>();
			listenersDict.put(eventType, listeners);
		}
		listeners.add(listener);
	}

	public void dispatch(Event event) {
		Class<? extends Event> eventType = event.getEventType();
		Collection<EventListener<? extends Event>> listeners = listenersDict.get(eventType);

		if (listeners != null) {
			for (EventListener<? extends Event> listener : listeners) {
				if (listener.getEventType() == eventType) {
					listener.onEvent(event);
				}
			}
		}
	}
}
