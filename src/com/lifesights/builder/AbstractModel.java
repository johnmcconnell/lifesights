package com.lifesights.builder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

public class AbstractModel {
	private String dirPath;
	private String name;
	private String url;
	private List<AbstractData> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<AbstractData> getData() {
		return data;
	}

	public void setData(List<AbstractData> data) {
		this.data = data;
	}

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

	@Override
	public String toString() {
		return "AbstractModel [name=" + name + ", url=" + url + ", data="
				+ data + "]";
	}

	public static AbstractModel toAbstractModel(JsonNode node) throws Exception {
	
		AbstractModel model = new AbstractModel();
		
		model.setDirPath(node.get("dir"));
		model.setName(node.get("name"));
		model.setUrl(node.get("url"));
		model.setData(node.get("data"));

		return model;
	}

	private void setDirPath(JsonNode dirNode) throws Exception {
		if (dirNode == null) {
			throw new Exception("the model does not have a dir field!");
		}
		this.setDirPath(dirNode.getTextValue());
	}

	public void setUrl(JsonNode urlNode) throws Exception {
		if (urlNode == null)
			throw new Exception("the model does not have a url field!");
		this.setUrl(urlNode.getTextValue());
	}

	public void setName(JsonNode nameNode) throws Exception {
		if (nameNode == null)
			throw new Exception("the model does not have a name field!");
		this.setName(nameNode.getTextValue());
	}

	public void setData(JsonNode dataNode) throws Exception {
		if (dataNode == null)
			throw new Exception("the model does not have a data field!");
		List<AbstractData> data = new LinkedList<AbstractData>();
		this.setData(data);
		
		Iterator<JsonNode> elements = dataNode.getElements();
		while (elements.hasNext()) {
			JsonNode json = elements.next();
			data.add(AbstractData.toAbstractData(json));
		}
	}

}
