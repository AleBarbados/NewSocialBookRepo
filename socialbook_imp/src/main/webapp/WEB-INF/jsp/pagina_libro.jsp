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
        <table>
            <c:if test="${review.id_customer == customer.id_customer && review.id_review>0}">
            <form action="reviewDeleteServlet" method="get">
                <tr>
                    <td>
                        <input type="hidden" name="id_review" value="${review.id_review}">
                        <input type="submit" value="Elimina">
                    </td>
            </form>
            </c:if>
            <form action="newReviewServlet" method="get">
                <tr>
                    <td>
                        <input type="hidden" name="ISBN" value="${book.isbn}">
                        <input type="hidden" name="id_customer" value="${review.id_customer}">
                        <input type="hidden" name="date" value="${review.review_date}">
                    </td>
                    <td>
                        <textarea name="body" placeholder="Aggiungi una recensione ..." cols="30" rows="20"></textarea>
                    </td>
                <tr>
                    <td>
                        <input type="number" name="vote" min="1" max="5" required>
                    </td>
                </tr>
                </tr>
                <input type="submit" value="Inserisci">
            </form>
        </table>
    </table>
</body>
</html>
