<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Prijava</title>
<style type="text/css">
.greska {
	font-family: fanatasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000
}

table.podatak {
	font-style: italic;
	vertical-align: top;
}

table.podatak td {
	vertical-align: top;
}
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
<body>
	<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<div class="user" align="right"><%=session.getAttribute("current.user").toString() %></div>
	</c:when>
	<c:otherwise>
		<div class="user" align="right">Anonimni korisnik</div>
	</c:otherwise>
	</c:choose>
				<div class="greska">
					<c:out value="${error}"></c:out>
				</div>
	<form action="login" method="post">
		<table class="podatak">

			<tr>
				<td>Korsiničko ime</td>
				<td><input type="text" name="username" size="20"></td>
			</tr>
			<tr>
				<td>Lozinka</td>
				<td><input type="password" name="password" size="20"></td>
			</tr>
		</table>
		<input type="submit" name="metoda" value="Prijavi"><input
			type="submit" name="metoda" value="Odustani">
	</form>
</body>