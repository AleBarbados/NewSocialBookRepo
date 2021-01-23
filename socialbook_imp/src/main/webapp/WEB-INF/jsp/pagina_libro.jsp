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

    <c:if test="${not empty recensioni}">
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
                        <td>${rec.vote}</td>
                        <td>${rec.body}</td>
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
                                    <form action="reviewServlet" method="post">
                                        <input type="hidden" name="isbn" value="${book.isbn}">
                                        <input type="hidden" name="rimuovi_rec" value="${rec.id_review}">
                                        <input type="submit" value="rimuovi">
                                    </form>
                                </td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
        </table>
    </c:if>
    <br><br>
    <c:if test="${personalCustomer != null && recensione != 'no'}">
        <form action="reviewServlet" method="get">
            <c:if test="${recensione == 'voto_si' || recensione == 'si'}">
                <label>Voto:</label>
                <input name="voto" list="valutazioni">
                <datalist id="valutazioni">
                    <option value="1">
                    <option value="2">
                    <option value="3">
                    <option value="4">
                    <option value="5">
                </datalist>
            </c:if>
            <c:if test="${recensione == 'commento_si' || recensione == 'si' }">
                <textarea name="commento" placeholder="Aggiungi un commento ..." cols="20" rows="20"></textarea>
            </c:if>
            <input type="hidden" name="isbn" value="${book.isbn}">
            <input type="submit" value="conferma recensione">
        </form>
    </c:if>
</body>
</html>
