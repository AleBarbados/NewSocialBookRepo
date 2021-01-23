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

    <c:choose>                                          <!-- apro scelta -->
        <c:when test="${admin}!= null">                 <!-- apro prima scelta, c'è l'admin attivo-->
            <c:choose>
            <c:when test="${ticketR} != null">
            <table id = "tableTicketRole" name = "tableTicketRole">
                <tr>Ticket disponibili</tr>
                <c:forEach items="${ticketsR}" var="ticketR">
                    <tr>
                        <td><a href="TicketViewServlet?id=${ticketR.id_ticket}">Ticket :${ticketR.id_ticket}</a></td>
                        <td>
                            <input type="submit" name="accept" value="Accetta incarico">
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
                <c:when test="${ticket}!= null">

            <table id = "tableTicket" name = "tableTicket">
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
        <c:otherwise>                                                  <!-- apro possibilità non ticket admin-->
            Nessun ticket disponibile
        </c:otherwise>                                                 <!-- chiudo possibilità admin non ticket-->
    </c:choose>                                                        <!-- chiudo possibilità ticket/ non ticket admin-->
    </c:when>                                                          <!-- chiudo possibilità admin-->
        <c:otherwise>                                                  <!-- apro possibilità customer-->
            <input type="submit" name="newTicket" value="Nuovo Ticket">
            <c:choose>                                                 <!-- apro scelta tra ticket/non ticket customer-->
                <c:when test="${tickets}!=null">                       <!-- apro possibilità ticket per customer-->
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
                </c:when>                                               <!-- chiudo possibilità tiket per customer-->
                <c:otherwise>                                           <!-- apro possibilità non ticket per customer-->
                Non ci sono Tickets
                </c:otherwise>                                          <!-- chiudo possibilità non ticket per customer-->
            </c:choose>                                                 <!-- chiudo scelta tra ticket/non ticket-->
        </c:otherwise>                                                  <!-- chiudo alternativa customer-->
    </c:choose>                                                         <!-- chiudo scelta admin/customer-->

</form>
</body>
</html>
