<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_13.model.Poll"%>
<%@page import="java.util.List"%>
<%
  List<Poll> unosi = (List<Poll>)request.getSession().getAttribute("polls");
%>
<html>
  <body>

  <b>Pronađene su sljedeće ankete:</b><br>

  <% if(unosi.isEmpty()) { %>
    Nema anketa. Možda trebate inicijalizirati bazu podataka. <a href="init">Inicijaliziraj</a>
  <% } else { %>
    <ul>
    <% for(Poll u : unosi) { %>
    <li>[ID=<%= u.getId() %>]<a href="glasanje?pollID=<%=u.getId() %>" ><%= u.getPollTitle() %></a> </li>  
    <% } %>  
    </ul>
  <% } %>

  </body>
</html>