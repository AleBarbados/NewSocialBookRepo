<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Page</title>
</head>
<body>
    <table>
        <tr>
            <td><img width="60px" height="60px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}"></td>
        </tr>
        <tr>
            <td><h2>${book.title}</h2></td>
        </tr>
        <tr>
            <td>${book.isbn}</td>
        </tr>
        <tr>
            <td>${book.genre}</td>
        </tr>
        <tr>
            <td>${book.price_cent}</td>
        </tr>
        <tr>
            <td>${book.publication_year}</td>
        </tr>
        <tr>
            <td>${book.publishing_house}</td>
        </tr>
        <tr>
            <td>${book.plot}</td>
        </tr>
    </table>
    <br><br>

    <table id="boh">
        <tr>
            <th>id customer</th>
            <th>vote</th>
            <th>body</th>
            <th>date</th>
        </tr>
        <c:forEach items="${recensioni}" var="rec">
            <tr>
                <td>${rec.id_customer}</td>
                <c:choose>
                    <c:when test="${rec.vote != null}">
                        <td>${rec.vote}</td>
                    </c:when>
                    <c:otherwise>
                        <td>NO VOTO</td>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${rec.body != null}">
                        <td>${rec.body}</td>
                    </c:when>
                    <c:otherwise>
                        <td>NO BODY</td>
                    </c:otherwise>
                </c:choose>
                <td>${rec.date}</td>
                <c:choose>
                    <c:when test="${customerManager != null}">
                        <td>
                            <form action="customerManagerReviewServlet" method="get">
                                <input type="hidden" name="r_id" value="${rec.id_review}">
                                <input type="submit" value="rimuovi">
                            </form>
                        </td>
                    </c:when>
                    <c:when test="${personalCustomer != null && personalCustomer.id_customer == rec.id_customer}">
                        <td>
                            <form action="reviewServlet" method="get">
                                <input type="hidden" name="rimuovi_rec" value="${rec.id_review}">
                                <input type="submit" value="rimuovi">
                            </form>
                        </td>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
    <br><br>
    <c:if test="${personalCustomer != null}">
        <form action="reviewServlet" method="get">
            <label>Voto:</label>
            <input name="voto" list="valutazioni">
            <datalist id="valutazioni">
                <option value="1">
                <option value="2">
                <option value="3">
                <option value="4">
                <option value="5">
            </datalist>

            <input type="hidden" name="book" value="${book.isbn}">
            <input type="hidden" name="utente" value="${personalCustomer.id_customer}">
            <input type="hidden" name="operazione" value="voto">
            <input type="submit" value="conferma voto">
        </form>
        <br>
        <form action="reviewServlet" method="get">
            <textarea name="commento" placeholder="Aggiungi un commento ..." cols="20" rows="20"></textarea>

            <input type="hidden" name="book" value="${book.isbn}">
            <input type="hidden" name="utente" value="${personalCustomer.id_customer}">
            <input type="submit" value="conferma recensione">
        </form>
    </c:if>
</body>
</html>
