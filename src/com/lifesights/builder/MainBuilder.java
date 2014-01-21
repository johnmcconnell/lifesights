package com.lifesights.builder;

import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.AssignExpr.Operator;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

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
		out.println(createModelAST(model));
		/*
		 * out.println("========== Client Code ==========\n");
		 * out.println(ClientBuilder.toJavaScript(model));
		 * out.println("========== Server Code ==========\n");
		 * out.println(ServerBuilder.toJavaCode(model));
		 * out.println("========== Model  Code ==========\n");
		 * out.println(ModelBuilder.toJavaCode(model));
		 */

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

	private static CompilationUnit createModelAST(AbstractModel model)
			throws ParseException, IOException {
		CompilationUnit cu = new CompilationUnit();
		// set the package
		cu.setPackage(new PackageDeclaration(ASTHelper
				.createNameExpr("com.data.model")));

		// create the type declaration
		ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(
				ModifierSet.PUBLIC, false, model.getName());

		// create the super class
		List<ClassOrInterfaceType> extendsList = new LinkedList<ClassOrInterfaceType>();
		extendsList.add(new ClassOrInterfaceType("DataObject"));
		type.setExtends(extendsList);

		ASTHelper.addTypeDeclaration(cu, type);

		for (AbstractData data : model.getData()) {
			ASTHelper.addMember(type,
					getterMethod(data.getType(), data.getName()));
			ASTHelper.addMember(type,
					setterMethod(data.getType(), data.getName()));
		}
		
		return cu;
	}

	private static MethodDeclaration getterMethod(String typeName, String name) {
		ClassOrInterfaceType type = new ClassOrInterfaceType(typeName);
		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				type, "get" + capitalize(name));
		BlockStmt statement = new BlockStmt();
		Expression thisEx = new ThisExpr();
		Expression fieldEx = new FieldAccessExpr(thisEx, name);
		ReturnStmt rStmt = new ReturnStmt(fieldEx);

		ASTHelper.addStmt(statement, rStmt);
		method.setBody(statement);
		return method;
	}

	private static MethodDeclaration setterMethod(String typeName, String name) {
		ClassOrInterfaceType type = new ClassOrInterfaceType(typeName);
		MethodDeclaration method = new MethodDeclaration(ModifierSet.PUBLIC,
				ASTHelper.VOID_TYPE, "set" + capitalize(name));
		Parameter param = ASTHelper.createParameter(type, name);
		ASTHelper.addParameter(method, param);
		BlockStmt statement = new BlockStmt();
		Expression thisEx = new ThisExpr();
		Expression fieldEx = new FieldAccessExpr(thisEx, name);
		Expression assignEx = new AssignExpr(fieldEx, new NameExpr(name),
				Operator.assign);

		ASTHelper.addStmt(statement, assignEx);
		method.setBody(statement);
		return method;
	}

	public static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
