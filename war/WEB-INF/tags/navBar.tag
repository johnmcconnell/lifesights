<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="data" required="true"
	type="com.mcconnell.weights.data.NavigationBar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!-- Static navbar -->
<div class="navbar navbar-default" role="navigation">
	<div class="navbar-collapse collapse">
		<ul class="nav navbar-nav">
			<c:set var="base" value=""></c:set>
			<c:forEach items="${data.mappings}" var="map">
				<c:choose>
					<c:when test="${map.y == data.last}">
						<li class="active"><a href="..${map.x}">${map.y}</a></li>
					</c:when>
					<c:when test="${map.y == data.first}">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle"
								data-toggle="collapse" data-target=".navbar-collapse">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="..${map.x}">${map.y}</a>
						</div>
					</c:when>
					<c:otherwise>
						<li><a href="..${map.x}">${map.y}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown">Dropdown <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li class="divider"></li>
					<li class="dropdown-header">Nav header</li>
					<li><a href="#">Separated link</a></li>
					<li><a href="#">One more separated link</a></li>
				</ul></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li class="active"><a href="#">Sign up</a></li>
			<li><a href="#">Sign up</a></li>
		</ul>
	</div>
	<!--/.nav-collapse -->
</div>