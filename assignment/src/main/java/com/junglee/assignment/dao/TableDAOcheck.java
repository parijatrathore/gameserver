package com.junglee.assignment.dao;

import com.junglee.assignment.entity.GameTable;
import com.junglee.assignment.persistence.rdbms.MysqlDBHandler;

public class TableDAOcheck {

	private static TableDAOcheck _instance = new TableDAOcheck();

	private TableDAOcheck() {

	}
	
	public static TableDAOcheck getInstance(){
		return _instance;
	}

	public void saveOrUpdate(GameTable table) {
		MysqlDBHandler.getInstance().saveOrUpdate(table);
	}

	public GameTable getById(Integer id) {
		return (GameTable) MysqlDBHandler.getInstance().getById(GameTable.class, id);
	}
}
