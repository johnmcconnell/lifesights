package com.lifesights.builder;

import japa.parser.ASTHelper;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.AssignExpr.Operator;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

public class ModelBuilder {
	private static ClassOrInterfaceType superType = new ClassOrInterfaceType("DataObject");
	
	public static String toJavaScriptCode(AbstractModel model) {
		return null;
	}
	
	public static String toJavaCode(AbstractModel model) throws ParseException, IOException {
		return createModelAST(model).toString();
	}
	
	private static CompilationUnit createModelAST(AbstractModel model)
			throws ParseException, IOException {
		CompilationUnit cu = new CompilationUnit();
		// set the package
		cu.setPackage(new PackageDeclaration(ASTHelper
				.createNameExpr(model.getDirPath())));

		// create the type declaration
		ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration(
				ModifierSet.PUBLIC, false, model.getName());

		// extend the super class
		List<ClassOrInterfaceType> extendsList = new LinkedList<ClassOrInterfaceType>();
		extendsList.add(superType);
		type.setExtends(extendsList);
		
		cu.setImports(createImports());
		
		// add annotations
		List<AnnotationExpr> annots = new LinkedList<AnnotationExpr>();
		annots.add(ASTHelper.createNormalAnnot("PersistenceCapable", "detachable","true"));
		type.setAnnotations(annots);

		ASTHelper.addTypeDeclaration(cu, type);

		for (AbstractData data : model.getData()) {
			ASTHelper.addMember(type, fieldDec(data));
		}
		
		for (AbstractData data : model.getData()) {
			
			ASTHelper.addMember(type,
					getterMethod(data.getType(), data.getName()));
			ASTHelper.addMember(type,
					setterMethod(data.getType(), data.getName()));
		}
		
		ASTHelper.addMember(type, updateMethod(model.getData()));
		
		return cu;
	}

	private static List<ImportDeclaration> createImports() {
		List<ImportDeclaration> imports = new LinkedList<ImportDeclaration>();
		
		for (String pak : new String[] {"javax.jdo.annotations.PersistenceCapable","javax.jdo.annotations.Persistent"}) {
			ImportDeclaration imp = new ImportDeclaration();
			imp.setName(ASTHelper.createNameExpr(pak));
			imports.add(imp);
		}
		
		return imports;
	}

	private static BodyDeclaration fieldDec(AbstractData data) {
		FieldDeclaration fieldDec = ASTHelper.createFieldDeclaration(ModifierSet.PRIVATE, new ClassOrInterfaceType(data.getType()), data.getName());
		
		List<AnnotationExpr> annots = new LinkedList<AnnotationExpr>();
		
		if (data.onServer()) {
			annots.add(ASTHelper.createMarkerAnnot("Persistent"));
		}
		
		fieldDec.setAnnotations(annots);
		return fieldDec;
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
	
	private static MethodDeclaration updateMethod(List<AbstractData> data) {
		MethodDeclaration method = new MethodDeclaration(Modifier.PUBLIC, ASTHelper.VOID_TYPE, "update");
		
		// add annotation
		List<AnnotationExpr> annots = new LinkedList<AnnotationExpr>();
		annots.add(ASTHelper.createMarkerAnnot("Override"));
		method.setAnnotations(annots);
		
		// add parameter
		Parameter param = ASTHelper.createParameter(superType, "from");
		NameExpr fromName = ASTHelper.createNameExpr("from");
		Expression thisEx = new ThisExpr();
		ASTHelper.addParameter(method, param);
		
		// add body
		BlockStmt body = new BlockStmt();
		method.setBody(body);
		
		// if class equal statement
		MethodCallExpr fromClassMethod = new MethodCallExpr(fromName,"getClass");
		MethodCallExpr thisClassMethod = new MethodCallExpr(fromName,"getClass");
		BinaryExpr isNotEqual = new BinaryExpr(fromClassMethod, thisClassMethod, BinaryExpr.Operator.notEquals);
		BlockStmt returnBlock = new BlockStmt();
		ASTHelper.addStmt(returnBlock, new ReturnStmt());
		IfStmt ifClassEqual = new IfStmt(isNotEqual, returnBlock, null);
		ASTHelper.addStmt(body, ifClassEqual);
		
		// add assignments stmts
		for (AbstractData d : data) {
			Expression fieldEx = new FieldAccessExpr(thisEx, d.getName());
			Expression fromFieldEx = new FieldAccessExpr(fromName, d.getName());
			Expression assignEx = new AssignExpr(fieldEx, fromFieldEx, Operator.assign);
			ASTHelper.addStmt(body, assignEx);
		}
		
		return method;
	}
	
	private static Expression setFieldStatement(AbstractData data) {
		Expression thisEx = new ThisExpr();
		Expression fieldEx = new FieldAccessExpr(thisEx, data.getName());
		Expression assignEx = new AssignExpr(fieldEx, ASTHelper.createNameExpr(data.getName()),
				Operator.assign);

		return assignEx;
	}

	public static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
