<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
<table border = "1">
	<c:forEach var="u" items="${kvadrati.keySet()}">
		<tr><td>${u}</td> <td>${kvadrati.get(u)}</td></tr>
	</c:forEach>
	</table>
</body>	
