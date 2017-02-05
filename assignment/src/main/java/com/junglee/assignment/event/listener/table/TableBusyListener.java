package com.junglee.assignment.event.listener.table;

import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.entity.GameTable.TableState;
import com.junglee.assignment.event.Event;
import com.junglee.assignment.event.listener.EventListener;
import com.junglee.assignment.event.table.TableBusyEvent;
import com.junglee.assignment.game.TableManager;

public class TableBusyListener implements EventListener<TableBusyEvent> {

	@Override
	public Class<TableBusyEvent> getEventType() {
		return TableBusyEvent.class;
	}

	@Override
	public void onEvent(Event event) {
		TableDTO table = ((TableBusyEvent)event).getTableDTO();
		if(TableState.IN_GAME.name().equalsIgnoreCase(table.getState()))
			TableManager.getInstance().getAvailableTables().remove(table.getTableId());
	}

}
