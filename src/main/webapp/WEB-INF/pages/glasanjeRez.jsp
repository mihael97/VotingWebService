<%@ page contentType="text/html; charset=UTF-8
	" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  page session="true"%>


<%
	String color = "#FFFFFF"; //white
	String stored = String.valueOf(session.getAttribute("pickedBgCol"));

	if (stored != null) {
		if (stored.equals("red")) {
			color = "#FF0000";
		} else if (stored.equals("cyan")) {
			color = "#00FFFF";
		} else if (stored.equals("green")) {
			color = "#00FF00";
		}
	}
%>

<!DOCTYPE>

<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body bgcolor=<%=color%>>
	<h1>Voting results</h1>
	<p>This are voting results</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Item</th>
				<th>Number of votes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="structure" items="${allItems}">
				<tr>
					<td>${structure.getName()}</td>
					<td>${structure.getVote()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Graphical show</h2>
	<img alt="Pie-chart"
		src="<%=request.getContextPath()%>/glasanje-grafika" width="400"
		height="400" />
	<h2>Result in XLS format</h2>
	<p>
		Results in XLS format are available <a
			href="<%=request.getContextPath()%>/glasanje-xls">here</a>
	</p>
	<h2>Other</h2>
	<p>Some songs from winner/winners</p>
	<ul>
		<c:forEach var="structure" items="${best}">
			<li><a href="${structure.getLink()}" target="_blank">${structure.getName()}</a></li>
		</c:forEach>
	</ul>
</body>
</html>