package com.junglee.assignment.persistence.rdbms;

import java.io.Serializable;

import org.hibernate.LockOptions;
import org.hibernate.Session;

import com.junglee.assignment.persistence.DBHandler;
import com.junglee.assignment.persistence.config.HibernateUtil;

public class MysqlDBHandler implements DBHandler {
	
	private static final MysqlDBHandler _instance = new MysqlDBHandler();
	private Session session = null;
	
	private MysqlDBHandler() {
		
	}
	
	public static MysqlDBHandler getInstance(){
		return new MysqlDBHandler();
	}

	@Override
	public void saveOrUpdate(Object obj) {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.close();
	}

	@Override
	public Object delete(Object obj) {
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(obj);
		session.close();
		return obj;
	}
	
	@Override
	public Object getById(Class<?> className, Serializable id){
		session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Object obj = session.get(className, id, LockOptions.READ);
		session.close();
		return obj;
	}

}
