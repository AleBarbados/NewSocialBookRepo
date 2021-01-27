<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Libri</title>
</head>

<body>
<h1>Tutti i libri</h1>
<c:forEach items="${books}" var="book">
    <table>
        <tr>
            <td><img width="60px" height="60px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}"></td>
        </tr>
        <tr>
            <td><a href="paginaLibroServlet?libro=${book.isbn}"><h2>${book.title}</h2></a></td>
        </tr>
        <tr>
            <c:if test="${book.catalogue}">
                <td>PREZZO: ${book.price_euro}</td>
            </c:if>
        </tr>
        <c:choose>
            <c:when test="${book.catalogue}">
                <tr>
                    <td>CATALOGO</td>
                </tr>
                <c:choose>
                    <c:when test="${catalogueManager != null}">
                        <tr>
                            <td>
                                <form action="catalogueManagerServlet1" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" value="Rimuovi">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${personalCustomer != null}">
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
                                <form action = "show-cart-servlet" method = "get">
                                <input type = "hidden" name = "isbn" value = "${book.isbn}">
                                <input type = "submit" name = "addCart" value = "Aggiungi al carrello">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:when>
            <c:otherwise>
                <tr>
                    <td>NON CATALOGO</td>
                </tr>
                <c:choose>
                    <c:when test="${catalogueManager != null}">
                        <tr>
                            <td>
                                <form action="catalogueManagerServlet1" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" value="Inserisci">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${personalCustomer != null}">
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
                                <form action = "show-cart-servlet" method = "get">
                                    <input type = "hidden" name = "isbn" value = "${book.isbn}">
                                    <input type = "submit" name = "addCart" value="Aggiungi al carrello">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <c:if test="${book.catalogue}">
            <c:choose>
                <c:when test="${catalogueManager != null}">
                    <tr>
                        <td>
                            <form action="catalogueManagerServlet1" method="get">
                                <input type="hidden" name="isbn" value="${book.isbn}">
                                <input type="hidden" name="operazione" value="modifica">
                                <input type="submit" value="Modifica prezzo">
                            </form>
                        </td>
                    </tr>
                </c:when>
            </c:choose>
        </c:if>
    </table>
    <br>
</c:forEach>
<br><br>
<c:choose>
    <c:when test="${catalogueManager != null}">
        <form action="catalogueManagerServlet1" method="get">
            <input type="hidden" name="operazione" value="creazione">
            <input type="submit" value="Nuovo libro">
        </form>
    </c:when>
</c:choose>
</body>
</html>
