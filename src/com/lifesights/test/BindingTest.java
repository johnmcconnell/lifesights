package com.lifesights.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.lifesights.utils.Utils;
import com.lifesights.webapp.DataObject;
import com.lifesights.webapp.TransactionRequest;
import com.lifesights.webapp.UserObject;

public class BindingTest {
	private String json = "{\"username\":\"john\",\"password\":\"willy321\"}";

	@Test
	public void test() {
		UserObject o = new UserObject("john","willy321","aafeq");
		//UserObject o = Utils.jsonToObject(json, UserObject.class);
		
		TransactionRequest req = new TransactionRequest();
		req.setData(o);
		req.setUser(o);
		String val = Utils.objectToJson(req);
		System.out.println(val);
	}

	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		String goal_content = "{\"name\":\"My first Goal\",\"updatedOn\":1386454290965,\"description\":\"I will not do this thing every day.\",\"index\":0}";
		String example_req = "{\"data\":{\"id\":\"new\",\"updatedOn\":\"Sat Jan 25 2014 18:56:49 GMT-0600 (Central Standard Time)\",\"author\":\"author\",\"title\":\"title\",\"something\":\"type here\",\"published\":false,\"tags\":[]},\"user\":null}"; 
		ObjectMapper mapper = new ObjectMapper();
		TransactionRequest req = Utils.jsonToObject(example_req, TransactionRequest.class);
		System.out.println(Utils.objectToJson(req));
	}
}
