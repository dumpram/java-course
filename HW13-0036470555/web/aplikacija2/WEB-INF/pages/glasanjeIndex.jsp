<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach var="u" items="${bands}">
		<li><a href="glasanje-glasaj?id=${u.getId()}">${u.getBandName()}</a></li>
		</c:forEach>
	</ol>
</body>
</html>