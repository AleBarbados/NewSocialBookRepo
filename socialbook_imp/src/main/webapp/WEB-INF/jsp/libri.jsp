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
                    <td><img width="60px" height="60px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/book/${book.image}"></td>
                </tr>
                <tr>
                    <td><h2>${book.title}</h2></td>
                </tr>
                <tr>
                    <td>PREZZO: ${book.price_euro}</td>
                </tr>
                <c:choose>
                    <c:when test="${book.catalogue == true}">
                        <tr>
                            <td>CATALOGO</td>
                        </tr>
                        <tr>
                            <td>
                                <form action="catalogueManagerServlet1" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" value="Rimuovi">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>NON CATALOGO</td>
                        </tr>
                        <tr>
                            <td>
                                <form action="catalogueManagerServlet1" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" value="Inserisci">
                                </form>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <td>
                        <form action="catalogueManagerServlet1" method="get">
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <input type="hidden" name="operazione" value="modifica">
                            <input type="submit" value="Modifica prezzo">
                        </form>
                    </td>
                </tr>
            </table>
            <br>
        </c:forEach>
        <br><br>

        <form action="catalogueManagerServlet1" method="get">
            <input type="hidden" name="operazione" value="creazione">
            <input type="submit" value="Nuovo libro">
        </form>
</body>
</html>
