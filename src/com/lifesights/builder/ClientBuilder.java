package com.lifesights.builder;

public class ClientBuilder {
	private static String createLib = "if (LifeSights !== undefined) {\n" +
			"\tthrow new Error('LifeSights object is already defined');\n" +
			"} else {\n" +
			"\tvar LifeSights = new Object();\n" +
			"}\n\n";
	private static String createModelObject = "LifeSights.%s = new Object();\n\n";
	private static String createAPI = "LifeSights.%1$s.create = function (model) {\n" +
			"\tjson = JSON.stringify(model);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/create',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String createAllAPI ="LifeSights.%1$s.createAll = function (models) {\n" +
			"\tjson = JSON.stringify(models);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/createAll',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String updateAPI = "LifeSights.%1$s.update = function (model) {\n" +
			"\tjson = JSON.stringify(model);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/update',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String updateAllAPI = "LifeSights.%1$s.updateAll = function (model) {\n" +
			"\tjson = JSON.stringify(model);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/updateAll',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String deleteAPI = "LifeSights.%1$s.update = function (model) {\n" +
			"\tjson = JSON.stringify(model);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/delete',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String deleteAllAPI = "LifeSights.%1$s.update = function (model) {\n" +
			"\tjson = JSON.stringify(model);\n" +
			"\t$.ajax({\n" +
				"\t\ttype : 'POST',\n" +
				"\t\turl : '%2$s/deleteAll',\n" +
				"\t\tdataType : 'json',\n" +
				"\t\tdata : json,\n" +
				"\t\tcontentType : 'application/json',\n" +
				"\t\tmimeType : 'application/json'\n" +
			"\t}).done(function(data) {\n" +
				"\t\tconsole.log(data);\n" +
			"\t});\n" +
		"}\n";
	private static String readAPI = "LifeSights.%1$s.read = new function({\n" +
			"\treturn $.getJSON('%2$s/read');\n" +
			"});\n";
	private static String readAllAPI = "LifeSights.%1$s.readAll = new function({\n" +
			"\treturn $.getJSON('%2$s/readAll');\n" +
			"});\n";
	private static String startNewAPI = "LifeSights.%1$s.new = new function({\n" +
			"\treturn new Object({";
	private static String vartype = "%s:%s,";
	private static String endNewAPI = "});\n});\n";
	private static String[] APIs = new String[] { createAPI, createAllAPI, updateAPI, readAPI, readAllAPI };
	public static String toJavaScript(AbstractModel model) {
		StringBuilder sb = new StringBuilder();
		sb.append(createLib);
		sb.append(String.format(createModelObject, model.getName()));
		
		for (String api : APIs) {
			sb.append(String.format(api,model.getName(),model.getUrl()) + "\n");
		}
		
		sb.append(String.format(startNewAPI,model.getName()));
		for (AbstractData d : model.getData()){
			sb.append(String.format(vartype,d.getName(),d.getType()));
		}
		sb.append(String.format(endNewAPI));
		
		return sb.toString();
	}
}
