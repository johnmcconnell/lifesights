package com.lifesights.builder;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.AssignExpr.Operator;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.annotations.Persistent;

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
		JsonNode models = node.get("models");

		Iterator<JsonNode> modelIter = models.getElements();
		while (modelIter.hasNext()) {
			JsonNode modelNode = modelIter.next();
			
			AbstractModel model = AbstractModel.toAbstractModel(modelNode);
			
			out.println("========== Client Code ==========\n");
			out.println(ClientBuilder.toJavaScript(model));
			out.println("========== Server Code ==========\n");
			out.println(ServerBuilder.toJavaCode(model));
			out.println("========== Model  Code ==========\n");
			out.println(ModelBuilder.toJavaCode(model));
		}

	}

	private static String helpMessage() {
		String[] lines = new String[] {
				"both filename:build the server and client for the model",
				"server filename:build only the server for the model",
				"client filename:build only the client for the model" };
		StringBuilder sb = new StringBuilder();

		sb.append("Usage:\n");

		for (String line : lines) {
			String[] split = line.split(":");
			sb.append(String.format("\t%1$-24s %2$1s\n", split[0], split[1]));
		}

		return sb.toString();
	}
}
