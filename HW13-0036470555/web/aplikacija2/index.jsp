<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
	<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
	<a href="colors.jsp">Background color chooser</a><br>
	<a href="squares?a=100&b=120">GetSquares</a><br>
	<a href="powers?a=1&b=100&n=3">GetPowers</a><br>
	<a href="app-info.jsp">AppInfo</a></br>
	<a href="report.jsp">Report</a><br>
	<a href="stories/funny.jsp">Doskočica</a><br>
	<a href="glasanje">Glasanje za bendove</a>
</body>
</html>