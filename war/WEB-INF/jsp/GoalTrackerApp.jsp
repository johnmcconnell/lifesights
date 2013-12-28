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
<t:javascript-link src="goal-tracker-app.js" />
<title>Goal Tracker</title>
</head>
<body>
	<script type="text/x-handlebars">
		<%@ include file="goal-tracker/RootView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="goals">
		<%@ include file="goal-tracker/GoalsView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="goal">
		<%@ include file="goal-tracker/GoalView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="goal/edit">
		<%@ include file="goal-tracker/GoalEditView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="about">
		<%@ include file="goal-tracker/AboutView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="progress">
		<%@ include file="goal-tracker/ProgressView.jsp"%>
	</script>
	<script type="text/x-handlebars" id="journal-entries">
		<%@ include file="goal-tracker/JournalView.jsp"%>
	</script>
</body>
</html>