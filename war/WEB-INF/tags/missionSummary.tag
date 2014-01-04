<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="mission" required="true" type="com.mcconnell.modernwar.model.Mission" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tr>
<td><a href="missions/${fn:escapeXml(mission.urlKey)}">${fn:escapeXml(mission.name)}</a></td>
<td>${fn:escapeXml(mission.totalLevels)}</td>
<td>${fn:escapeXml(mission.createdOn)}</td>
<td>${fn:escapeXml(mission.updatedOn)}</td>
</tr>
