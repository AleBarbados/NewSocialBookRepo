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
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<form action="ticket-view-servlet" method="get">

    <c:choose>
        <c:when test="${customerManager != null || systemManager != null}">
            <h1>Ticket disponibili size: ${ticketsR.size()}</h1>
            <h1>Ticket dell'admin size: ${tickets.size()}</h1>

            <c:choose>
            <c:when test="${ticketsR != null && ticketsR.size() > 0}">
            <table id = "tableTicketRole" name = "tableTicketRole">
                <tr>Ticket disponibili</tr>
                <c:forEach items="${ticketsR}" var="ticketR">
                    <tr>
                        <td><a href="ticket-view-servlet?id=${ticketR.id_ticket}">Ticket :${ticketR.id_ticket}</a></td>
                        <td>
                            <a href="ticket-view-servlet?id=${ticketR.id_ticket}&name=accept">Accetta</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            </c:when>
        <c:otherwise>
            Nessun nuovo ticket
        </c:otherwise>
        </c:choose>
            <c:choose>
                <c:when test="${tickets != null && tickets.size() > 0}">

            <table id = "tableTicket" name = "tableTicket">
                <c:forEach items="${tickets}" var="ticket">
                    <tr>
                        <td><a href="ticket-view-servlet?id=${ticket.id_ticket}">Ticket :${ticket.id_ticket}</a></td>
                        <td>
                            <a href="ticket-view-servlet?id=${ticket.id_ticket}&name=delete">Elimina</a>

                        </td>
                    </tr>
                </c:forEach>
            </table>

                </c:when>
        <c:otherwise>
            Nessun ticket disponibile
        </c:otherwise>
    </c:choose>
    </c:when>
        <c:otherwise>
            <a href="ticket-view-servlet?id=null&name=newTicket">New Ticket </a>
            <c:choose>
                <c:when test="${tickets != null && tickets.size() > 0}"> entro in ticket non null
                <table>
                    <tr><c:out value="${tickets.size()}" ></c:out></tr>
                    <c:forEach items="${tickets}" var="ticket">
                        <tr>
                            <td><a href="ticket-view-servlet?id=${ticket.id_ticket}">Ticket :${ticket.id_ticket}</a></td>
                            <td>
                                <a href="ticket-view-servlet?id=${ticket.id_ticket}&name=delete">Elimina</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                </c:when>
                <c:otherwise>
                Non ci sono Tickets
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</form>
</body>
</html>
