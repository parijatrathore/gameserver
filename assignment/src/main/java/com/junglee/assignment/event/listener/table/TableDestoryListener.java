package com.junglee.assignment.event.listener.table;

import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.entity.GameTable.TableState;
import com.junglee.assignment.event.Event;
import com.junglee.assignment.event.listener.EventListener;
import com.junglee.assignment.event.table.TableAvailableEvent;
import com.junglee.assignment.event.table.TableDestoryEvent;
import com.junglee.assignment.game.TableManager;

public class TableDestoryListener implements EventListener<TableDestoryEvent> {

	@Override
	public Class<TableDestoryEvent> getEventType() {
		return TableDestoryEvent.class;
	}

	@Override
	public void onEvent(Event event) {
		TableDTO tabledto = ((TableAvailableEvent)event).getTableDTO();
		if(TableState.DESTYROYED.name().equalsIgnoreCase(tabledto.getState())){
			TableManager.getInstance().getAvailableTables().remove(tabledto.getTableId());
			TableManager.getInstance().getTables().remove(tabledto.getTableId());
		}
		// TODO: dispatch messages/transfer them other table - to players who joined meanwhile we were removing table from the manager
	}

}
