package com.lifesights.builder;

public class ModelBuilder {
	private static String imports  = "import javax.jdo.annotations.PersistenceCapable;\n" + 
	"import javax.jdo.annotations.Persistent;\n\n" +
	"import com.lifesights.webapp.DataObject;\n\n";
	private static String classDeclaration ="@PersistenceCapable(detachable = \"true\")\n" +
	"public class %s extends DataObject {\n";
	private static String classEnd = "}";
	private static String fieldDeclaration = "\t@Persistent\n" + "\tprivate %s %s;\n";
	private static String updateDeclaration = "\t@Override\n" +
	"\tpublic void update(DataObject from) {\n" +
	"\t\tif (getClass() != from.getClass()) {\n" +
	"\t\t\treturn;\n" +
	"\t\t}\n" +
	"\t\t%1$s other = (%1$s) from;\n";
	private static String updateEnd ="\t}\n";
	private static String bindStatement = "\t\tthis.%1$s = other.%1$s;\n";
	
	public static String toJavaScriptCode(AbstractModel model) {
		return null;
	}
	
	public static String toJavaCode(AbstractModel model) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(imports);
		sb.append(String.format(classDeclaration, model.getName()));
		
		for (AbstractData field : model.getData()) {
			sb.append(String.format(fieldDeclaration, field.getType(), field.getName()));
		}
		
		sb.append(String.format(updateDeclaration, model.getName()));
		
		for (AbstractData field : model.getData()) {
			sb.append(String.format(bindStatement,field.getName()));
		}
		
		sb.append(updateEnd);
		
		sb.append(classEnd);
		return sb.toString();
	}
}
