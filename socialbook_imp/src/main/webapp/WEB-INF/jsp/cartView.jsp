<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 26/01/21
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carrello</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<h1>Carrello:</h1>
    <c:choose>
            <c:when test="${cart.getBooks().size() == 0}">

                Nessun elemento nel carrello

            </c:when>
    <c:otherwise>
        <c:forEach items="${cart.getBooks()}" var="book">
        <table>
            <tr>
                <td><img width="60px" height="60px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}"></td>
            </tr>
            <tr>
                <td><a href="paginaLibroServlet?libro=${book.isbn}"><h2>${book.title}</h2></a></td>
            </tr>
            <tr>
                    <td>PREZZO: ${book.price_euro}</td>
            </tr>
            <tr>
                <td>
                    <form action="FollowEditBooklist" method="get">
                        <input type="hidden" name="isbn" value="${book.isbn}">
                        <input type="submit" name="addPreferiti" value="Aggiungi ai Preferiti">
                    </form>
                    <form action="ScegliBooklistServlet" method="get">
                        <input type="hidden" name="isbn" value="${book.isbn}">
                        <input type="submit" value="Aggiungi a Booklist">
                    </form>
                    <form action="show-cart-servlet" method="get">
                        <input type="hidden" name="isbn" value="${book.isbn}">
                        <input type="submit" name = "delete" value="Elimina">
                 </form>
              </td>
            </tr>
        </table>
    </c:forEach>
    <div>
        Totale carrello: ${cart.getPrice()}
        <a href="payment-servlet"> Procedi all'acquisto</a>
    </div>

</c:otherwise>
</c:choose>
</body>
</html>
