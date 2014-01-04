<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="level" required="true"
	type="com.mcconnell.modernwar.model.Level"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
	<c:when test="${level.placeholder}">
		<tr>
			<td>${fn:escapeXml(level.index)}</td>
			<td>none</td>
			<td>none</td>
			<td>none</td>
			<td></td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td>${fn:escapeXml(level.index)}</td>
			<td>${fn:escapeXml(level.name)}</td>
			<td>${fn:escapeXml(level.totalRuns)}</td>
			<td>${fn:escapeXml(level.updatedOn)}</td>
		</tr>
	</c:otherwise>
</c:choose>


