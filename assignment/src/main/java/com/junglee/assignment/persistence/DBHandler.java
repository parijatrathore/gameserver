package com.junglee.assignment.persistence;

import java.io.Serializable;


public interface DBHandler {

	public void saveOrUpdate(Object obj);
	
	public Object delete(Object obj);
	
	public Object getById(Class<?> className, Serializable id);

}
