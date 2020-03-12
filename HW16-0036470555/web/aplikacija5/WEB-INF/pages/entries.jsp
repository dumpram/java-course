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
<h1>List of entries of author ${author.nick}</h1>
<c:choose>
<c:when test="${!entries.isEmpty()}">
<ol>
<c:forEach var="entry" items="${entries}">
<li><a href="${author.nick}/${entry.id}">${entry.title}</a>
</li>
</c:forEach>
</ol>
</c:when>
<c:otherwise>
No entries.
</c:otherwise>
</c:choose>
<p>
<c:choose>
	<c:when test='<%=request.getAttribute("author").equals(session.getAttribute("current.user"))%>'>
	<a href="${author.nick}/new">Add new entry</a>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
</p> 
<a href="/aplikacija5/">Return to index page.</a>
</html>