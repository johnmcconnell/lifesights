package com.lifesights.builder;

public class ServerBuilder {
	private static String serverCode = "@Controller\n" +
"@RequestMapping(%1$sController.BASE_URL)\n" +
"public class %1$sController extends DataController<%1$s> {\n" +
	"\tpublic static final Class<%1$s> CLASS = %1$s.class;\n" +
	"\tpublic static final String BASE_URL = \"%2$s\";\n" +

	"\tpublic %1$sController() {\n" +
		"\t\tsuper(CLASS);\n" +
	"\t}\n" +
"}\n";
	public static String toJavaCode(AbstractModel model) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(serverCode, model.getName(), model.getUrl()));
		return sb.toString();
	}
}
