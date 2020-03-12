<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Kontakt</title>
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
</head>
<body>
	<c:choose>
	<c:when test='<%=session.getAttribute("current.user") != null%>'>
		<div class="user" align="right"><%=session.getAttribute("current.user").toString() %></div>
	</c:when>
	<c:otherwise>
		<div class="user" align="right">Anonimni korisnik</div>
	</c:otherwise>
	</c:choose>
	<h1>
		<c:choose>
			<c:when test="${radionica.id.isEmpty()}">Nova radionica</c:when>
			<c:otherwise>Uređivanje radionica</c:otherwise>
		</c:choose>
	</h1>
	<form action="save" method="post">
	<input type="hidden" name="id" value="${radionica.id }">
		<table class="podatak">
			<tr>
				<td>Naziv</td>
				<td><input type="text" name="naziv"
					value='<c:out value="${radionica.naziv}"></c:out>' size="30"><br></td>
			</tr>
			<c:if test="${radionica.imaPogresku('naziv')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('naziv') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Datum</td>
				<td><input type="text" name="datum"
					value='<c:out value="${radionica.datum}"></c:out>' size="10"><br></td>
			</tr>
			<c:if test="${radionica.imaPogresku('datum')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('datum') }"></c:out>
				</div>
			</c:if>
				<tr>
					<td>Oprema</td>
			<td><select name="oprema" multiple size=${oprema.size() }>
					<c:forEach var="item" items="${oprema.keySet()}">
						<c:choose>
							<c:when test="${radionica.postoji(item)}">
								<option value="${item}" selected>${oprema.get(item)}</option>
							</c:when>
							<c:otherwise>
								<option value="${item}">${oprema.get(item)}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			</select></td>
			</tr>
			<c:if test="${radionica.imaPogresku('opremaId')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('opremaId') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Trajanje</td>
				<td><select name="trajanje">
						<c:forEach var="item" items="${trajanje.keySet()}">
							<c:choose>
								<c:when test="${radionica.trajanje.id.equals(item)}">
									<option value="${item }" selected>${trajanje.get(item)}</option>
								</c:when>
								<c:otherwise>
									<option value="${item }">${trajanje.get(item) }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
				</select></td>
			</tr>
					<c:if test="${radionica.imaPogresku('trajanjeId')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('trajanjeId') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Publika</td>
				<td><c:forEach var="item" items="${publika.keySet()}">
						<c:choose>
							<c:when test="${radionica.postojiPublika(item)}">
								<input type="checkbox" name="publika"
									value="${item}" checked>${publika.get(item)}<br>
							</c:when>
							<c:otherwise>
								<input type="checkbox" name="publika"
									value="${item}">${publika.get(item)}<br>
							</c:otherwise>
						</c:choose>
					</c:forEach></td>
			</tr>
					<c:if test="${radionica.imaPogresku('publikaId')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('publikaId') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Maksimalno polaznika</td>
				<td><input type="text" name="maksPolaznika"
					value='<c:out value="${radionica.maksPolaznika}"></c:out>' size="5"><br></td>
			</tr>
			<c:if test="${radionica.imaPogresku('maksPolaznika')}">
				<div class="greska">
					<c:out value="${radionica.dohvatiPogresku('maksPolaznika') }"></c:out>
				</div>
			</c:if>
			<tr>
				<td>Email</td>
				<td><input type="text" name="email"
					value='<c:out value="${radionica.email}"></c:out>' size="30"><br></td>
				<c:if test="${radionica.imaPogresku('email')}">
					<div class="greska">
						<c:out value="${radionica.dohvatiPogresku('email') }"></c:out>
					</div>
				</c:if>
			<tr>
				<td>Dopuna</td>
				<td><textarea name="dopuna" rows="4" cols="50">${radionica.dohvatiTekstDopune()}</textarea><br></td>
			</tr>
		</table>
		<input type="submit" name="metoda" value="Pohrani"> <input
			type="submit" name="metoda" value="Odustani">
	</form>
</body>
</html>
