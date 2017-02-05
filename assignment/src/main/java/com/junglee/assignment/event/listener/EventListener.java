package com.junglee.assignment.event.listener;

import com.junglee.assignment.event.Event;

public interface EventListener<T extends Event> {
    Class<T> getEventType();

    void onEvent(Event event);
}
