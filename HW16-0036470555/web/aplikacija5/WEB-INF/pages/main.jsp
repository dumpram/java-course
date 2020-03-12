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
<p>
	(You haven't got account?) <a href="servleti/register">Register</a>
</p>
<p>
	<c:choose>
		<c:when test='<%=session.getAttribute("current.user") == null%>'>(You have account. Please, login!)
<h1>Login</h1>
				<div class="greska">
					<c:out value="${error}"></c:out>
				</div>
	<form action="servleti/login" method="post">
		<table class="podatak">

			<tr>
				<td>Nick</td>
				<td><input type="text" name="nick" size="20" value="${nick}"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" size="20"></td>
			</tr>
		</table>
		<input type="submit" name="metoda" value="Login"><input
			type="submit" name="metoda" value="Cancel">
	</form>
		</c:when>
		<c:otherwise>
			<a href="servleti/logout">Logout</a>
		</c:otherwise>
	</c:choose>
</p>

<h1>Registered authors</h1>

<ul>
<c:forEach var="author" items="${authors}">
<li><a href="servleti/author/${author.nick}">${author.nick}</a></li>
</c:forEach>
</ul>

</html>