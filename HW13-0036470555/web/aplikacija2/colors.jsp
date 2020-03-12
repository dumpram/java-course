<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
<a href="/aplikacija2/setcolor?col=cyan">CYAN</a>
<a href="/aplikacija2/setcolor?col=red">RED</a>
<a href="/aplikacija2/setcolor?col=green">GREEN</a>
<a href="/aplikacija2/setcolor?col=white">WHITE</a>
<p><a href="index.jsp">HOME</a></p>
</body>
</html>