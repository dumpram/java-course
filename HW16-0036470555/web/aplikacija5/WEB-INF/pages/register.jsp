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

<body>
<h1>Register form</h1>
<form action="save" method="post">
		<table class="podatak">
			<tr>
			<c:if test="${form.hasError('firstName')}">
				<div class="greska">
					<c:out value="${form.getError('firstName') }"></c:out>
				</div>
			</c:if>
				<td>First name</td>
				<td><input type="text" name="firstName"
					value='<c:out value="${form.firstName}"></c:out>' size="30"><br></td>
			</tr>
			
			<tr>
				<td>Last name</td>
				<td><input type="text" name="lastName"
					value='<c:out value="${form.lastName}"></c:out>' size="30"><br></td>
			</tr>
			<c:if test="${form.hasError('lastName')}">
				<div class="greska">
					<c:out value="${form.getError('lastName') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email"
					value='<c:out value="${form.email}"></c:out>' size="30"><br></td>
				<c:if test="${form.hasError('email')}">
					<div class="greska">
						<c:out value="${form.getError('email') }"></c:out>
					</div>
				</c:if>
				</tr>
				
			<tr>
				<td>Nick</td>
				<td><input type="text" name="nick"
					value='<c:out value="${form.nick}"></c:out>' size="30"><br></td>
			</tr>
			<c:if test="${form.hasError('nick')}">
				<div class="greska">
					<c:out value="${form.getError('nick') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"
					value='<c:out value=""></c:out>' size="30"><br></td>
			</tr>
			<c:if test="${form.hasError('password')}">
				<div class="greska">
					<c:out value="${form.getError('password') }"></c:out>
				</div>
			</c:if>
		</table>
		<input type="submit" name="metoda" value="Register"> <input
			type="submit" name="metoda" value="Cancel">
	</form>
</body>
