<%@page import="java.util.ArrayList"%>
<%@page import="hr.fer.zemris.java.dao.DAOProvider"%>
<%@page import="hr.fer.zemris.java.strcutures.PollOptionsStructure"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@ page contentType="text/html; charset=UTF-8
	" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  page session="true"%>



<!DOCTYPE>
<%
	int pollID = Integer.parseInt(request.getParameter("pollID"));
%>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body>
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
			<c:forEach var="structure" items="${requestScope.allItems }">
				<tr>
					<td>${structure.getOptionTitle()}</td>
					<td>${structure.getVotes()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<h2>Graphical show</h2>
	<img alt="Pie-chart"
		src="<%=request.getContextPath()%>/servleti/glasanje-grafika?pollID=<%=pollID%>"
		width="400" height="400" />
	<h2>Result in XLS format</h2>
	<p>
		Results in XLS format are available <a
			href="<%=request.getContextPath()%>/servleti/glasanje-xls?pollID=<%=pollID%>">here</a>
	</p>
	<h2>Other</h2>
	<p>Some links from winner/winners</p>
	<ul>
		<c:forEach var="structure" items="${requestScope.best }">
			<li><a href="${structure.getOptionLink()}" target="_blank">${structure.getOptionTitle()}</a></li>
		</c:forEach>
	</ul>
</body>
</html>