package com.apps.summly.webservice.db;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DBInterface
{
	private MongoClient mongodbClient;
	private DB db;
	private DBCollection dbCollec;
	private String mongoHost="localhost";
	private String databaseName="Summly";
	private String dbCollectionName="";
	private int mongoPort=27017;
	private static List<String> dbRecords;
	private DBCollection dbCollection;
	
	public static void main(String args[])
	{
		/*
		 * Sample code to retrieve and print the values from MongoDB
		 * 
		 */
		
		DBInterface driver = new DBInterface();
		driver.initializeMongoDB();
		
	}
	
	
	public DBInterface() {
		super();		
		
	}

    public DBCollection initializeMongoDB()
    {
    	
    	try
    	{
    		mongodbClient = getDBConnection(mongoHost,mongoPort);
    		db = getDatabaseInstance(mongodbClient,databaseName);
    		System.out.println("DB Initialized...");
    		
    		dbCollection=getDatabaseCollection(db, "NewsActive");
    		//printSingleArgsRecordFromDB(dbCollec,"info.area.geocode.value","600117");
    		
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
      return dbCollection;	
    }

	public MongoClient getDBConnection(String mongoHost,int mongoPort) throws UnknownHostException  
	{
		MongoClient dbClient=null;
		try
		{
			dbClient = new MongoClient(mongoHost,mongoPort);	
			System.out.println("DBConnection Established..!!");
		}catch(MongoException e)
		{
			System.out.println("Unknown MongoHost...!!! Quitting..!!!" +e.getCode());
			System.exit(0);
		}		
		
		return dbClient;
	}
	
	public DB getDatabaseInstance(MongoClient dbClient, String databaseName)
	{
		DB db=null;
		try
		{
			db = dbClient.getDB(databaseName);
		}catch(Exception e)
		{
			System.out.println("Unable to get DB Instance..!!! Quitting..~!!");
			System.exit(0);
		}
		
		return db;
	}
	
	public DBCollection getDatabaseCollection(DB db,String dbCollectionName)
	{	
		DBCollection dbCollectionRef=null;
		try
		{
			dbCollectionRef = db.getCollection(dbCollectionName);
		}catch(Exception e)
		{
			System.out.println("Unable to get DBCollection Instance..!!! Quitting!!!");
			System.exit(0);
		}
		return dbCollectionRef;
	}
	
	public String printSingleArgsRecordFromDB(DBCollection dbCollection,String Key,String Value)
	{
		BasicDBObject dbQuery;
		DBCursor cursor;
		List<DBObject> newsRecords;		
		
		dbQuery = new BasicDBObject();
		dbQuery.put(Key, Value);
		cursor = db.getCollection("NewsActive").find(dbQuery);
		newsRecords = new ArrayList<DBObject>();
		System.out.println("Key: " +Key);
		System.out.println("Value: " +Value);	
		
		
		try
		{
			newsRecords=cursor.toArray();			
			//System.out.println(newsRecords.get(0));
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			if(cursor != null)
			{
				cursor.close();
			}
		}
  
		return new Gson().toJson(newsRecords);
	}
	
	public void printAllValuesInCollection(DBCollection dbCollection)
	{
		DBCursor cursor = dbCollection.find();
		while(cursor.hasNext()) {
	        System.out.println(cursor.next());
	    }
		
	}
	
	/*
	 * 
	 * Yet to develop insert or update methods for MongoDB
	 */
	 

	
}
