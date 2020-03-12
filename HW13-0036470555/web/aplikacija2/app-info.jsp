<%@ page import="java.util.concurrent.TimeUnit"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	long millis = System.currentTimeMillis() - (Long) session.getServletContext().getAttribute("app-info");
	long days = TimeUnit.MILLISECONDS.toDays(millis);
	millis -= TimeUnit.DAYS.toMillis(days);
	long hours = TimeUnit.MILLISECONDS.toHours(millis);
	millis -= TimeUnit.HOURS.toMillis(hours);
	long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
	millis -= TimeUnit.MINUTES.toMillis(minutes);
	long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
	millis -= TimeUnit.SECONDS.toMillis(seconds);
	StringBuilder sb = new StringBuilder();
	sb.append(days);
	sb.append(" Days ");
	sb.append(hours);
	sb.append(" Hours ");
	sb.append(minutes);
	sb.append(" Minutes ");
	sb.append(seconds);
	sb.append(" Seconds ");
	sb.append(millis);
	sb.append(" Milliseconds ");
	session.setAttribute("time", sb.toString());
%>
<html>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
	<h1>Application summary</h1>
	<p>
		Application was running for:
		<%=session.getAttribute("time")%>
	</p>
</body>
</html>