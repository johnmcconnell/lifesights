package com.lifesights.builder;

import java.util.HashSet;

import org.codehaus.jackson.JsonNode;

public class AbstractData {
	public static final String BOTH = "b";
	public static final String CLIENT = "c";
	public static final String SERVER = "s";
	private static HashSet<String> scopes;
	static {
		scopes = new HashSet<String>();
		scopes.add(BOTH);
		scopes.add(CLIENT);
		scopes.add(SERVER);
	}
	private String name;
	private String type;
	private String scope;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public boolean onServer() {
		return (BOTH.equals(scope) || SERVER.equals(scope));
	}
	
	@Override
	public String toString() {
		return "AbstractData [name=" + name + ", type=" + type + ", scope="
				+ scope + "]";
	}
	public static AbstractData toAbstractData(JsonNode json) throws Exception {
		AbstractData data = new AbstractData();
		String[] values = json.getTextValue().split(":");
		if (values.length == 3) {
			data.setType(values[0]);
			data.setName(values[1]);
			if (!scopes.contains(values[2]))
				throw new Exception("data does not use a valid scope either b,s,c used:" + values[3]);
			data.setType(values[2]);
		} else if (values.length == 2) {
			data.setType(values[0]);
			data.setName(values[1]);
			data.setScope(BOTH);
		}
		
		
		return data;
	}
	
}
