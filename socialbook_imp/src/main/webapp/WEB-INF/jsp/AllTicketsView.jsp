<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 22/01/21
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All tickets</title>
</head>
<body>
<form action="ticket-view" method="get">
    <input type="submit" name="newTicket" value="Nuovo Ticket">
    <c:choose>
    <c:when test="${tickets}!=null">
    <table>
        <c:forEach items="${tickets}" var="ticket">
            <tr>
                <td><a href="TicketViewServlet?id=${ticket.id_ticket}">Ticket :${ticket.id_ticket}</a></td>
                <td>
                    <input type="submit" name="delete" value="Elimina">
                </td>
            </tr>
        </c:forEach>
    </table>
    </c:when>
    <c:otherwise>
    Non ci sono Tickets
    </c:otherwise>
    </c:choose>
</form>
</body>
</html>
