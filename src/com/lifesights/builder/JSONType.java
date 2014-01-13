package com.lifesights.builder;

import org.codehaus.jackson.JsonNode;



public enum JSONType {

	Array,
	Object,
	Null,
	String;
	
	public static JSONType toType(JsonNode node) throws Exception {
		if (node.isArray()) {
			return Array;
		} else if (node.isObject()) {
			return Object;
		} else if (node.isNull()) {
			return Null;
		} else if (node.isTextual()) {
			return String;
		}
		throw new Exception("unsupported JsonType:" + node.getTextValue());
	}
}
