package com.mcconnell.webapps.goaltracker.model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class DataBindingTests {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		String goal_content = "{\"name\":\"My first Goal\",\"updatedOn\":1386454290965,\"description\":\"I will not do this thing every day.\",\"index\":0}";
		ObjectMapper mapper = new ObjectMapper();
		DailyGoal goal = mapper.readValue(goal_content,DailyGoal.class);
		System.out.println(goal);
	}

}
