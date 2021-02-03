<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 28/01/21
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders:</title>
</head>
<body>
<c:choose>
    <c:when test="${orders != null && orders.size() > 0}">

        <div id = "Orders" name = "Orders">
            <c:set var="count" value="0" scope="page" />
            <c:forEach items="${orders}" var="order">
                <ul id = "first_part_${count}">
                    <li>Ordine numero: ${order.getId_order()}</li>
                    <li><button class = "next" onclick="nextDiv()">Apri Ordine</button></li>

                </ul>
                <ul id = "second_part_${count}">
                    <li>Data ordine: ${orderer.getDate()}</li>
                    <li>Costo ordine: ${order.getStringPrice_euro()}</li>

                    <c:forEach items="${products}" var="product">
                        <c:if test="${order.getId_order() == product.getId_order()}">
                            <c:forEach var="entry" items="${product.getBook()}">
                                <li>
                                    <a href="paginaLibroServlet?libro=${entry.key}"> ${entry.value} </a>
                                </li>
                            </c:forEach>
                        </c:if>
                    </c:forEach>

                    <li><button class = "next" onclick="prevDiv()">Chiudi Ordine </button></li>

                </ul>
                <c:set var="count" value="${count + 1}" scope="page"/>            </c:forEach>
        </div>

    </c:when>
    <c:otherwise>
        Nessun ordine disponibile
    </c:otherwise>
</c:choose>
<script>
    function nextDiv() {
        document.getElementById("first-part_${count}").style.display = "none";
        document.getElementById("second-part_${count}").style.display = "block";
    }
    function prevDiv() {
        document.getElementById("second-part").style.display = "none";
        document.getElementById("first-part").style.display = "block";
    }
</script>

</body>
</html>
