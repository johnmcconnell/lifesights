<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="exercises and workouts created by users">
<meta name="author" content="John McConnell">
<%@ include file="../html/headerLibs.html"%>
<t:javascript-link src="exercise-control.js" />
<t:css-link src="exercise-control.css" />
<title>Weights Exercises</title>
</head>
<body>
	<div class="container">
		<t:navBar data="${navbar}"></t:navBar>

		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Exercises</h1>
			<p>.</p>
			<p>
				<%@include file="NewExerciseModal.jsp"%>
			</p>
		</div>

	</div>
	<!-- /container -->
</body>
</html>