package com.monkey.wikidata.mongodb;

import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongodbCurd {
	public MongoClient mongoClient = null;
	public MongoDatabase db = null;
	public String dbName = null;

	
	public MongodbCurd(String dbName) {
		this.dbName = dbName;
	}
	
	public void init() {
		try{
			mongoClient = new MongoClient("localhost", 27017);
		}catch(Exception e) {
			e.printStackTrace();
		}
		db = mongoClient.getDatabase(dbName);
	}
	
	public void destroy() {
		if(mongoClient != null) {
			mongoClient.close();
		}
		mongoClient = null;
		db = null;
		System.gc();
	}
	
	public void queryAll() {
	    System.out.println("查询users的所有数据：");
	    //db游标
	}
	
	public void add(String collection, Document doc) {
		try {
			db.getCollection(collection).insertOne(doc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addBatch(String collection, List<Document> docs) {
		try {
			db.getCollection(collection).insertMany(docs);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findOne(String collection) {
		try {
			db.getCollection(collection).find();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
