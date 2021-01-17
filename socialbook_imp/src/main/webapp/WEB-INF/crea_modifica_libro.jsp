<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Creazione/modifica libro</title>
</head>

<body>
    <form action="catalogueManagerServlet2" method="get">
        <table>
            <tr>
                <td><h3>ISBN</h3></td>
                <td>
                    <input type="text" name="isbn" required
                    <c:if test="${operazione != 'creazione'}" >
                           value="${book.isbn}" disabled>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><h3>Titolo</h3></td>
                <td>
                    <input type="text" name="title" required
                    <c:if test="${operazione != 'creazione'}" >
                           value="${book.title}" disabled>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><h3>Genere</h3></td>
                <td>
                    <input type="text" name="genre" required
                    <c:if test="${operazione != 'creazione'}" >
                           value="${book.genre}" disabled>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><h3>Prezzo (centesimi)</h3></td>
                <td>
                    <input type="number" name="price_cent" required
                    <c:if test="${operazione != 'creazione'}" >
                        value="${book.price_cent}" >
                    </c:if>

                </td>
            </tr>
            <tr>
                <td><h3>Anno</h3></td>
                <td>
                    <input type="text" name="publication_year" required
                    <c:if test="${operazione != 'creazione'}" >
                           value="${book.publication_year}" disabled>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><h3>Casa editrice</h3></td>
                <td>
                    <input type="text" name="publishing_house" required
                    <c:if test="${operazione != 'creazione'}" >
                           value="${book.publishing_house}" disabled>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td><h3>Trama</h3></td>
                <td>
                    <c:choose>
                        <c:when test="${operazione != 'creazione'}">
                            <textarea name="plot" cols="30" rows="20" disabled>${book.plot}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea name="plot" placeholder="Aggiungi una descrizione ..." cols="30" rows="20" required></textarea>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>

        <input type="hidden" name="isbn_modifica" value="${book.isbn}">
        <input type="submit" value="Salva">
    </form>

</body>

</html>
