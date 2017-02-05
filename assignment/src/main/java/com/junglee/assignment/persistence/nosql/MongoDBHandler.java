package com.junglee.assignment.persistence.nosql;

import java.io.Serializable;

import org.bson.Document;

import com.junglee.assignment.persistence.DBHandler;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBHandler implements DBHandler{

	private MongoClient mongoClient = null;
	private String dbname = null;
	private String collectionName = null;
	private MongoDatabase db  = null;
	private MongoCollection<Document> collection = null;
	
	private MongoDBHandler(){
		mongoClient = new MongoClient("localhost", 27017);
	}
	
	public static MongoDBHandler getInstance(String dbName, String collectionName){
		MongoDBHandler _instance = new MongoDBHandler();
		_instance.dbname = dbName;
		_instance.collectionName = collectionName;
		_instance.db = _instance.mongoClient.getDatabase(dbName);
		_instance.collection = _instance.db.getCollection(collectionName);
		return _instance;
	}

	@Override
	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object delete(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Class<?> className, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
