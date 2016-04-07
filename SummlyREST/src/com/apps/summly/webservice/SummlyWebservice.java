package com.apps.summly.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.apps.summly.webservice.db.DBInterface;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;

@Path("/summly")
public class SummlyWebservice 
{
	JsonParser jsonParser = new JsonParser();
	
	@GET
	@Path("/GetPersonInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPersonInfo()
	{
		String content = "{\"Persons\": "+ "[{\"id\""+":"+"\"rirani\",";
		       content += "\"firstName\""+":"+"\"Romin\",";
		       content += "\"lastName\""+":"+"\"Irani\"},";
		       content += "{\"id\""+":"+"\"skrish\",";
		       content += "\"firstName\""+":"+"\"Sathya\",";
		       content += "\"lastName\""+":"+"\"Narayanan\"}]}";
		
	 return content;
	}
	
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMsg(@PathParam("param") String msg) {
 
		
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
 
	@GET		
	@Path("/GetLatestBusinessNews")
	@Produces(MediaType.APPLICATION_JSON)
	public String getActiveBusinessNews()
	{
		
		DBInterface dbInstance = null;
		DBCollection activeNewsCollec;
		
		String feeds=null;
		String records=null;
		String allResponse = null;
		
		
		try
		{
			Gson gson = new Gson();
			//Getting the db instance
			dbInstance = new DBInterface();
			activeNewsCollec = dbInstance.initializeMongoDB();
			feeds= dbInstance.printSingleArgsRecordFromDB(activeNewsCollec, "newsId","Business-0704201618:16:56");		
			records = "{\"Business\":"+feeds+"}";
			allResponse = gson.toJson(records);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(dbInstance!=null)
			{
				//mongoClient.close();				
			}
		}
		
		return records;
		
	}
	
	@GET		
	@Path("/GetLatestSportsNews")
	@Produces(MediaType.APPLICATION_JSON)
	public String getActiveSportsNews()
	{
		
		DBInterface dbInstance = null;
		DBCollection activeNewsCollec;
		
		String feeds=null;
		String records=null;
		String allResponse = null;
		
		
		try
		{
			Gson gson = new Gson();
			//Getting the db instance
			dbInstance = new DBInterface();
			activeNewsCollec = dbInstance.initializeMongoDB();
			feeds= dbInstance.printSingleArgsRecordFromDB(activeNewsCollec, "newsId","Sports-0704201620:53:26");		
			records = "{\"Sports\":"+feeds+"}";
			allResponse = gson.toJson(records);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(dbInstance!=null)
			{
				//mongoClient.close();				
			}
		}
		
		return records;
		
	}

	
	
	
	
}
