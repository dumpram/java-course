<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/aplikacija5/css/blog.css">
</head>
<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<div class="user" align="right"><%=session.getAttribute("current.user").toString()%></div>
	</c:when>
	<c:otherwise>
		<div class="user" align="right">Guest</div>
	</c:otherwise>
</c:choose>
<h1>Register successful</h1>
<p>
Congrats, you registration is successful.
</p>
<a href="/aplikacija5/">Return to index page.</a>
</html>