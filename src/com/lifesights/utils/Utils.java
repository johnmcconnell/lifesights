package com.lifesights.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;


public class Utils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static String objectToJson(Object o) {
		String value = null;
		try {
			value = mapper.writeValueAsString(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static <T> T jsonToObject(String content, Class<T> _class) {
		T object = null;
		try {
			 object = mapper.readValue(content,_class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
