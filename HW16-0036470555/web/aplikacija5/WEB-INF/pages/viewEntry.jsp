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
<h1>${entry.title}</h1>
<p style="border: 1px solid blue;">
${entry.text}
</p>
<p>
[Posted on: ${entry.createdAt}] [Last modified at: ${entry.lastModifiedAt}]
</p>
<h2>Comments</h2>
<ul>
<c:forEach var="comment" items="${entry.comments}">
<li>[${comment.author.getNick()}] : [${comment.postedOn}]
<p>
${comment.message}
</p>
</li>
</c:forEach>
<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<a href="${entry.id}/add_comment">Add comment</a>
	</c:when>
</c:choose>
</ul>
<c:choose>
	<c:when test='<%=request.getAttribute("author").equals(session.getAttribute("current.user"))%>'>
	<a href="${entry.id}/edit">Edit entry</a>
	</c:when>
</c:choose>
<p>
<a href="/aplikacija5/">Return to index page.</a>
</p>
</html>