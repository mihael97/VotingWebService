<%@ page contentType="text/html; charset=UTF-8
	" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@  page session="true"%>
<!DOCTYPE>
<html>
<body>
	<h2>Please select one of supported polls</h2>
	<ol>
		<c:forEach var="structure" items="${requestScope.allItems }">
			<li><a
				href="<%=request.getContextPath()%>/servleti/glasanje?pollID=${structure.getId()}">${structure.getTitle()}</a></li>
		</c:forEach>
	</ol>

</body>
</html>