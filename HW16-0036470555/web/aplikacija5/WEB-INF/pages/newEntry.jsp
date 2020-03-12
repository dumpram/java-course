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
<h1>New entry</h1>
<form action="new" method="post">
	<table class="podatak">
		<tr>
			<c:if test="${form.hasError('title')}">
				<div class="greska">
					<c:out value="${form.getError('title') }"></c:out>
				</div>
			</c:if>
			<td>Title</td>
			<td><input type="text" name="title"
				value='<c:out value="${form.title}"></c:out>' size="30"><br></td>
		</tr>

		<tr>
			<td>Text</td>
			<td><textarea name="text" rows="4" cols="50">${form.text}</textarea></td>
		</tr>
		<c:if test="${form.hasError('text')}">
			<div class="greska">
				<c:out value="${form.getError('text') }"></c:out>
			</div>
		</c:if>
	</table>
	<input type="submit" name="metoda" value="Add"> <input
			type="submit" name="metoda" value="Cancel">
</form>
</html>
