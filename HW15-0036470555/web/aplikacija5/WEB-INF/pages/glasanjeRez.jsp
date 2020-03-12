<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" cellspacing="0" class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="u" items="${results}">
			<tr>
				<td>${u.getOptionTitle()}</td>
				<td>${u.getVotesCount()}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>
	<h2>Razno</h2>
	<p>Pobjednici ankete:</p>
	<ul>
		<c:forEach var="winner" items="${winners}">
		<li><a href="${winner.getOptionLink()}" target="_blank">${winner.getOptionTitle()}</a></li>
		</c:forEach>
		</ul>
</body>
</html>