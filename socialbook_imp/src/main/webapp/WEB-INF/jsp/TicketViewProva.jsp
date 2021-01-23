<%@ page import="java.sql.Date" %>
<%@ page import="socialbook.model.Ticket" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 21/01/21
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>VisualizzazionevTicket</title>
</head>
<body>
<table>

    <tr>
        <td><h2>Ticket numero: ${ticket.id_ticket}</h2></td>
    </tr>
    <tr>
        <td><h6>Issue: ${ticket.issue}</h6></td>
    </tr>
    <tr>

        <td>Data di apertura: ${ticket.getStringOpen_date()} </td>
    </tr>
    <tr>
        <td>Data chiusura: ${ticket.getStringClose_date()}</td>
    </tr>
    <tr>
        <td>Status: ${ticket.status.name()}</td>
    </tr>
    <tr><h3>Messaggi:</h3></tr>
    <c:forEach var="message" items="${messages}">
    <c:if test="${message.sender == false}">
    <div  class="customer-message"> </c:if>
        <c:if test="${message.sender == true}">
        <div  class="admin-message"> </c:if>

                 <tr><td>${message.message_body}<td></tr>
                 </c:forEach>
</table>
                 <form id = "insertMessageForm" name ="insertMessageForm" method="post" action="new-message-servlet">
                     <textarea name="message" placeholder="Aggiungi un messaggio ..." cols="30" rows="30"></textarea>
                     <button type="submit" value="Send">Invia</button>
                 </form>
<a href="all-ticket-servlet" >Torna ai ticket</a>

</body>
</html>
