<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
	<h1>${poll.getPollTitle()}</h1>
	<p>${poll.getPollMessage()}</p>
	<ol>
		<c:forEach var="u" items="${options}">
		<li><a href="glasanje-glasaj?id=${u.getId()}">${u.getOptionTitle()}</a></li>
		</c:forEach>
	</ol>
</body>
</html>