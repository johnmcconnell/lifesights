<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="keep track of goals throughout your life">
<meta name="author" content="John McConnell">
<%@ include file="../html/headerLibs.html"%>
<t:javascript-link src="blog-app.js" />
<title>Cyborg</title>
</head>
<body>
	<script type="text/x-handlebars">
		<%@ include file="blog/RootView.jsp"%>
	</script>

	<script type="text/x-handlebars" id="entries">
		<%@ include file="blog/JournalView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="entry">
		<%@ include file="blog/JournalEntryView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="about">
		<%@ include file="blog/AboutView.jsp"%>
	</script>

</body>
</html>