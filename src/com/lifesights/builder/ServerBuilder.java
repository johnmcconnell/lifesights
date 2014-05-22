package com.lifesights.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import japa.parser.ASTHelper;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

public class ServerBuilder {
	private static ClassOrInterfaceType superType = new ClassOrInterfaceType(
			"DataController");

	public static void printJavaCode(File dir, List<AbstractModel> models) throws ParseException,
			IOException {
		
		for (AbstractModel model : models) {
			PrintWriter writer = new PrintWriter(new File(dir,model.getName() + "Controller.java"),"UTF-8");
			writer.println(createModelAST(model).toString());
			writer.close();
		}

		return;
	}

	private static CompilationUnit createModelAST(AbstractModel model)
			throws ParseException, IOException {
		String className = model.getName() + "Controller";
		CompilationUnit cu = new CompilationUnit();
		// set the package
		cu.setPackage(new PackageDeclaration(ASTHelper.createNameExpr(model
				.getDirPath())));

		// create the type declaration
		ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(
				ModifierSet.PUBLIC, false, className);

		// extend the super class
		List<ClassOrInterfaceType> extendsList = new LinkedList<ClassOrInterfaceType>();
		superType.setTypeArgs(new LinkedList<Type>());
		superType.getTypeArgs().add(new ClassOrInterfaceType(model.getName()));
		extendsList.add(superType);
		type.setExtends(extendsList);

		cu.setImports(createImports());

		// add annotations
		List<AnnotationExpr> annots = new LinkedList<AnnotationExpr>();
		annots.add(new MarkerAnnotationExpr(ASTHelper
				.createNameExpr("Controller")));
		SingleMemberAnnotationExpr requestAnnot = new SingleMemberAnnotationExpr(
				ASTHelper.createNameExpr("RequestMapping"),
				new FieldAccessExpr(ASTHelper.createNameExpr(className),
						"BASE_URL"));
		annots.add(requestAnnot);
		type.setAnnotations(annots);

		// add static vars
		type.setMembers(createBody(model, className));

		ASTHelper.addTypeDeclaration(cu, type);

		return cu;
	}

	private static List<BodyDeclaration> createBody(AbstractModel model,
			String className) {
		List<BodyDeclaration> body = new LinkedList<BodyDeclaration>();

		VariableDeclarator dec = new VariableDeclarator(new VariableDeclaratorId("CLASS"), new NameExpr(model.getName() + ".class"));
		FieldDeclaration classField = ASTHelper.createFieldDeclaration(
				ModifierSet.STATIC + ModifierSet.PUBLIC + ModifierSet.FINAL,
				new ClassOrInterfaceType("String"), dec);
		body.add(classField);
		
		VariableDeclarator urlDec = new VariableDeclarator(new VariableDeclaratorId("BASE_URL"), new StringLiteralExpr(model.getUrl()));
		FieldDeclaration decUrlField = ASTHelper.createFieldDeclaration(
				ModifierSet.STATIC + ModifierSet.PUBLIC + ModifierSet.FINAL,
				new ClassOrInterfaceType("String"), urlDec);
		body.add(decUrlField);
		
		ConstructorDeclaration con = new ConstructorDeclaration(ModifierSet.PUBLIC, className);
		BlockStmt conBlock = new BlockStmt();
		List<Expression> args = new LinkedList<Expression>();
		args.add(ASTHelper.createNameExpr("CLASS"));
		ASTHelper.addStmt(conBlock, new MethodCallExpr(null,"super",args));
		con.setBlock(conBlock);
		
		body.add(con);
		
		return body;
	}

	private static List<ImportDeclaration> createImports() {
		List<ImportDeclaration> imports = new LinkedList<ImportDeclaration>();

		for (String pak : new String[] {
				"org.springframework.stereotype.Controller",
				"org.springframework.web.bind.annotation.RequestMapping" }) {
			ImportDeclaration imp = new ImportDeclaration();
			imp.setName(ASTHelper.createNameExpr(pak));
			imports.add(imp);
		}

		return imports;
	}
}
