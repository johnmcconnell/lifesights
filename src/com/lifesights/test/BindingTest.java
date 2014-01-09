package com.lifesights.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.lifesights.utils.Utils;
import com.lifesights.webapp.UserObject;

public class BindingTest {
	private String json = "{\"username\":\"john\",\"password\":\"willy321\"}";

	@Test
	public void test() {
		//UserObject o = new UserObject("john","willy321","aafeq");
		UserObject o = Utils.jsonToObject(json, UserObject.class);
		System.out.println(o);
	}

	@Test
	public void test2() throws JsonParseException, JsonMappingException, IOException {
		String goal_content = "{\"name\":\"My first Goal\",\"updatedOn\":1386454290965,\"description\":\"I will not do this thing every day.\",\"index\":0}";
		ObjectMapper mapper = new ObjectMapper();
	}
}
