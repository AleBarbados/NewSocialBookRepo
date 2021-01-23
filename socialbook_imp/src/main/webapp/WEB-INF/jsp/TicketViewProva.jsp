<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 21/01/21
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <td>Data di apertura: ${ticket.open_date}</td>
    </tr>
    <tr>
        <td>Data chiusura: ${ticket.close_date}</td>
    </tr>
    <tr>
        <td>Status: ${ticket.t_status}</td>
    </tr>
    <c:forEach var="message" items="${messages}">
        <c:if test="${message.sender == 0}">
             <div  class="customer-message"> </c:if>
        <c:if test="${message.sender == 1}">
             <div  class="admin-message"> </c:if>

                 <tr>${message}</tr>
                 </c:forEach>

                 <form id = "insertMessageForm" name ="insertMessageForm" method="post" action="new-message-servlet">
                     <textarea name="message" placeholder="Aggiungi un messaggio ..." cols="30" rows="30"></textarea>
                     <button type="submit" value="Send"></button>
                 </form>


</table>
</body>
</html>
