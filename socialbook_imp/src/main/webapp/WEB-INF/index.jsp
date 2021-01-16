<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>prova</title>
</head>

<body>
    <h1>Catalogo:</h1>
    <table>
        <tr>
            <th>Titolo libro</th>
            <th>Prezzo</th>
            <th>Catalogo</th>
            <th>INSERIMENTO</th>
            <th>MODIFICA</th>
            <th>RIMOZIONE</th>
        </tr>
        <jsp:useBean id="books" scope="request" type="java.util.ArrayList"/>
        <c:forEach items="${books}" var="book">
        <tr>
            <td>${book.title}</td>
            <td>${book.price_euro}</td>
            <td>${book.catalogue}</td>
            <td>
                <c:choose>
                    <c:when test="${book.catalogue == false}">
                        <form action="catalogueManagerServlet" method="get">
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <input type="submit" name="inserimento" value="Inserisci">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <h3>Gia' presente</h3>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="catalogueManagerServlet" method="get">
                    <input type="hidden" name="isbn" value="${book.isbn}">
                    <input type="hidden" name="price_cent" value="${book.price_cent}">
                    <input type="submit" name="modifica" value="Modifica prezzo">
                </form>
            </td>
            <td>
                <c:choose>
                    <c:when test="${book.catalogue == true}">
                        <form action="catalogueManagerServlet" method="get">
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <input type="submit" name="rimozione" value="Rimuovi">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <h3>Non presente</h3>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>

</html>