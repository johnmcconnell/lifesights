<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.google.appengine.api.datastore.KeyFactory"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="blog/headerlib.jsp" />
<script language="javascript" type="text/javascript"
	src="/js/missions.js"></script>
<link rel="stylesheet" type="text/css" href="/css/missions.css"></link>
<title>Modern War Missions</title>
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h1>Available Missions</h1><%@include file="html/NewMissionModal.html"%>
		</div>
		<div class="row">
			<div class="span10">
				<c:choose>
					<c:when test="${empty missions}">
						<p>There are no missions at this time</p>
					</c:when>
					<c:otherwise>
						<h3>Missions:</h3>
						<table>
							<tr>
								<td>Name</td>
								<td>Levels</td>
								<td>Created On</td>
								<td>Updated On</td>
							</tr>
							<c:forEach items="${missions}" var="mission">
								<lib:missionSummary mission="${mission}" />
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

</body>
</html>