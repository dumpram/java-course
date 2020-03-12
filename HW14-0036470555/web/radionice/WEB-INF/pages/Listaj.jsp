<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Radionice</title>
<style type="text/css">
.log{
	font-family: fanatasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;	 
	position: relative; 
	top: 1px;
	left: 1px;
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
</head>
</html>

<body>
	<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<div class="user" align="right"><%=session.getAttribute("current.user").toString() %></div>
	</c:when>
	<c:otherwise>
		<div class="user" align="right">Anonimni korisnik</div>
	</c:otherwise>
	</c:choose>
	<h1>Lista radionica</h1>
	<c:choose>
		<c:when test="${radionice.isEmpty()}">
			<p>Trenutno nemate radionica</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="radionica" items="${radionice}">
					<li><c:out value="${radionica.naziv}"></c:out> <c:out
							value="${radionica.datum}"></c:out> <c:choose>
							<c:when test='<%=session.getAttribute("current.user") != null%>'>
								<a href="edit?id=${radionica.id}">Uredi</a>
							</c:when>
						</c:choose></li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>


	<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<p>
		<a href="new">Dodaj novu radionicu</a>
		</p>
		<div class="log"><a href="logout">Logout</a></div>
		</c:when>
		<c:otherwise>
		<div class="log"><a href="login">Login</a></div>
		</c:otherwise>
		</c:choose>
	
</body>