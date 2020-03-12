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
<h1>Add comment</h1>
<form action="add_comment" method="post">
<input type="hidden" name="id" value="${form.id}">
	<table class="podatak">
		<tr>
			<td>Comment</td>
			<td><textarea name="message" rows="2" cols="50">${comment.message}</textarea></td>
		</tr>
		<c:if test="${comment.hasError('message')}">
			<div class="greska">
				<c:out value="${comment.getError('message') }"></c:out>
			</div>
		</c:if>
	</table>
	<input type="submit" name="metoda" value="Comment"> <input
			type="submit" name="metoda" value="Cancel">
</form>