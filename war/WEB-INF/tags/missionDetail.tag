<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="mission" required="true"
	type="com.mcconnell.modernwar.model.Mission"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table>
	<tr>
		<td>Name</td>
		<td>Levels</td>
		<td>Created On</td>
		<td>Updated On</td>
	</tr>
	<tr>
		<td>${fn:escapeXml(mission.name)}</td>
		<td>${fn:escapeXml(mission.totalLevels)}</td>
		<td>${fn:escapeXml(mission.createdOn)}</td>
		<td>${fn:escapeXml(mission.updatedOn)}</td>
	</tr>
</table>