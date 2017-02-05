package com.junglee.assignment.event.listener.table;

import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.entity.GameTable.TableState;
import com.junglee.assignment.event.Event;
import com.junglee.assignment.event.listener.EventListener;
import com.junglee.assignment.event.table.TableAvailableEvent;
import com.junglee.assignment.game.TableManager;

public class TableAvailableListener implements EventListener<TableAvailableEvent>{

	@Override
	public Class<TableAvailableEvent> getEventType() {
		return TableAvailableEvent.class;
	}

	@Override
	public void onEvent(Event event) {
		TableDTO tabledto = ((TableAvailableEvent)event).getTableDTO();
		if(TableState.WAITING_FOR_PLAYERS.name().equalsIgnoreCase(tabledto.getState())){
			TableManager.getInstance().getAvailableTables().put(tabledto.getTableId(), tabledto);
			TableManager.getInstance().getTables().put(tabledto.getTableId(), tabledto);
		}
		
	}

}
