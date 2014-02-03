package com.lifesights.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ClientBuilder {
	private static String createLib = "if (LifeSights !== undefined) {\n"
			+ "\tthrow new Error('LifeSights object is already defined');\n"
			+ "} else {\n" + "\tvar LifeSights = new Object();\n" + "}\n\n";
	private static String createModelObject = "LifeSights.%s = new Object();\n\n";
	private static String createAPI = "LifeSights.%1$s.create = function (data) {\n"
			+ "\tjson = JSON.stringify(data);\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/create',\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String createAllAPI = "LifeSights.%1$s.createAll = function (data) {\n"
			+ "\tjson = JSON.stringify(data);\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/createAll',\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String updateAPI = "LifeSights.%1$s.update = function (data) {\n"
			+ "\tjson = JSON.stringify(data);\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/update',\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String updateAllAPI = "LifeSights.%1$s.updateAll = function (data) {\n"
			+ "\tjson = JSON.stringify(data);\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/updateAll',\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String deleteAPI = "LifeSights.%1$s.update = function (id) {\n"
			+ "\tjson = '';\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/delete/' + id,\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String deleteAllAPI = "LifeSights.%1$s.update = function (data) {\n"
			+ "\tjson = JSON.stringify({data:data,user:user});\n"
			+ "\t$.ajax({\n"
			+ "\t\ttype : 'POST',\n"
			+ "\t\turl : '%2$s/deleteAll',\n"
			+ "\t\tdataType : 'json',\n"
			+ "\t\tdata : json,\n"
			+ "\t\tcontentType : 'application/json',\n"
			+ "\t\tmimeType : 'application/json'\n"
			+ "\t}).done(function(data) {\n"
			+ "\t\tconsole.log(data);\n"
			+ "\t});\n" + "}\n";
	private static String readAPI = "LifeSights.%1$s.read = function(id){\n"
			+ "\treturn $.getJSON('%2$s/read/' + id);\n" + "}\n";
	private static String readAllAPI = "LifeSights.%1$s.readAll = function(){\n"
			+ "\treturn $.getJSON('%2$s/readAll');\n" + "}\n";
	private static String startNewAPI = "LifeSights.%1$s.model = function(){\n"
			+ "\treturn new Object({id:'new', updatedOn:Date(), ";
	private static String vartype = "%s:%s,";
	private static String endNewAPI = "});\n}\n";
	private static String[] APIs = new String[] { createAPI,
			updateAPI, readAPI, readAllAPI, deleteAPI };

	public static void printJavaScript(File dir, List<AbstractModel> models) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(new File(dir,"lifesights-0.1.js"),"UTF-8");
		StringBuilder sb = new StringBuilder();
		sb.append(createLib);
		for (AbstractModel model : models) {
			sb.append(String.format(createModelObject, model.getName()));

			for (String api : APIs) {
				sb.append(String.format(api, model.getName(), model.getUrl())
						+ "\n");
			}

			sb.append(String.format(startNewAPI, model.getName()));
			for (AbstractData d : model.getData()) {
				sb.append(String.format(vartype, d.getName(), d.getType()));
			}
			sb.append(String.format(endNewAPI));
		}
		writer.print(sb.toString());
		writer.close();
	}
}
