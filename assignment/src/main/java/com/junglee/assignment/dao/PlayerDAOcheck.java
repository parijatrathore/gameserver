package com.junglee.assignment.dao;

import com.junglee.assignment.entity.Player;
import com.junglee.assignment.persistence.rdbms.MysqlDBHandler;

public class PlayerDAOcheck {
	
	private static PlayerDAOcheck _instance = new PlayerDAOcheck();
	
	private PlayerDAOcheck(){
		
	}
	
	public static PlayerDAOcheck getInstance(){
		return _instance;
	}

	public void saveOrUpdate(Player player){
		MysqlDBHandler.getInstance().saveOrUpdate(player);
	}
	
	public Player getById(Integer id){
		// check redis first
		return (Player)MysqlDBHandler.getInstance().getById(Player.class, id);
	}
}
