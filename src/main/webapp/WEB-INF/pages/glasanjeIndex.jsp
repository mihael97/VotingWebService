<%@page import="hr.fer.zemris.java.strcutures.PollOptionsStructure"%>
<%@page import="java.util.List"%>
<%@page import="hr.fer.zemris.java.dao.DAOProvider"%>
<%@page import="hr.fer.zemris.java.strcutures.PollsStructure"%>
<%@ page contentType="text/html; charset=UTF-8
	" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  page session="true"%>

<!DOCTYPE>
<html>
<body>
	<h2>Please vote for one of given items by clicking on name</h2>

	<h3>
		<%
			int pollID = Integer.parseInt(request.getParameter("pollID"));
			PollsStructure struc = (PollsStructure) DAOProvider.getDao().getPollByID(pollID);
			List<PollOptionsStructure> list = DAOProvider.getDao().loadItems(pollID);
			System.out.print(struc.getTitle());
		%>
	</h3>
	<h4>
		<%
			System.out.print(struc.getMessage());
		%>
	</h4>


	<ol>
		<c:forEach var="item" items="<%=list%>">
			<li><a
				href="<%=request.getContextPath()%>\servleti\glasanje-glasaj?id=${item.getId()}&pollID=
				<%=pollID %>">${item.getOptionTitle()}</a></li>
		</c:forEach>
	</ol>
</body>
</html>