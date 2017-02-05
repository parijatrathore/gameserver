package com.junglee.assignment.event.table;

import com.junglee.assignment.dto.TableDTO;
import com.junglee.assignment.event.Event;

public class TableBusyEvent extends Event {

	private TableDTO tableDTO;

	public TableBusyEvent(TableDTO tableDTO) {
		super();
		this.tableDTO = tableDTO;
	}

	public TableDTO getTableDTO() {
		return tableDTO;
	}

}
