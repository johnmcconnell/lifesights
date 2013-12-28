package com.mcconnell.webapp.weights.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.mcconnell.webapp.weights.model.Exercise;

public class JsonDataTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Exercise exercise = new Exercise("test1");
		exercise.getSteps().add("step1");
		String exerciseContent = mapper.writeValueAsString(exercise);
		System.out.println(exerciseContent);
		Exercise e = mapper.readValue(exerciseContent, Exercise.class);
		System.out.println(e);
	}

}
