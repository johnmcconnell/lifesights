package com.lifesights.builder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.lifesights.utils.Utils;

public class MainBuilder {
	private static String HELP_MESSAGE = helpMessage();
	private static PrintStream out = System.out;
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			out.println(HELP_MESSAGE);
			System.exit(0);
		}
		String command = args[0];
		InputStream file = new FileInputStream(args[1]); // create file
		
		ObjectMapper mapper = Utils.getMapper();
		
		JsonNode node = mapper.readTree(file);
		
		AbstractModel model = AbstractModel.toAbstractModel(node);
		out.println("========== Client Code ==========\n");
		out.println(ClientBuilder.toJavaScript(model));
		out.println("========== Server Code ==========\n");
		out.println(ServerBuilder.toJavaCode(model));
		out.println("========== Model  Code ==========\n");
		out.println(ModelBuilder.toJavaCode(model));
		
	}

	private static String helpMessage() {
		String[] lines = new String[] { "both filename:build the server and client for the model",
				"server filename:build only the server for the model",
				"client filename:build only the client for the model"};
		StringBuilder sb = new StringBuilder();
		
		sb.append("Usage:\n");
		
		for (String line : lines) {
			String[] split = line.split(":");
			sb.append(String.format("\t%1$-24s %2$1s\n", split[0], split[1]));
		}
		
		
		
		return sb.toString();
	}
}
