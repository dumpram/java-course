<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Pogreška</title>
<style type="text/css">
.user{
	font-style: italic;
	font-size: 2em;
	color: white;
	background:blue;
	height: 40px;
	width:100%;
	top: 0px;
	margin: 0px;
  	padding: 0px;
  	left: 0px;
  	right: 0px;
}
</style>
</head>
<body>
	<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<div class="user" align="right"><%=session.getAttribute("current.user").toString() %></div>
	</c:when>
	<c:otherwise>
		<div class="user" align="right">Anonimni korisnik</div>
	</c:otherwise>
	</c:choose>
	<h1>Dogodila se pogreška</h1>
	<p>
		<c:out value="${poruka}"></c:out>
	</p>
	<p>
		<a href="listaj">Povratak na početnu stranicu</a>
</body>
</html>