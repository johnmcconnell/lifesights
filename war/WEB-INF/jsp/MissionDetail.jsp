<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="lib" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="javascript" type="text/javascript"
	src="/js/missionDetail.js"></script>
<title>Modern War Mission</title>
<jsp:include page="blog/headerlib.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<c:choose>
			<c:when test="${empty mission}">
				<div class="jumbotron">
					<h1>Sorry!</h1>
				</div>
				<p>We're sorry that mission does not exist.</p>
			</c:when>
			<c:otherwise>
				<div class="jumbotron">
					<lib:missionDetail mission="${mission}" />
				</div>
				<div class="container">
					<!-- Example row of columns -->
					<div class="row">
						<div class="col-md-4">
							<h2>Levels</h2>
							<c:choose>
								<c:when test="${empty mission.levels}">
									<p>${fn:escapeXml(mission.name)} has no levels.</p>
								</c:when>
								<c:otherwise>
									<table>
									<tr>
										<td>Level</td>
										<td>Name</td>
										<td>Total Completions</td>
										<td>Updated On</td>
									</tr>
									<c:forEach items="${mission.levels}" var="level">
										<lib:levelSummary level="${level}"/>
									</c:forEach>
									</table>
								</c:otherwise>
							</c:choose>
							<lib:newLevelModal mission="${mission}"/>
							<lib:newLevelModalTrigger/>
						</div>
					</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>