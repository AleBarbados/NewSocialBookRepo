<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 20/01/2021
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booklist</title>
</head>
<body>
<c:choose>
    <c:when test="${booklist!=null}">
        <h1>${booklist.name}</h1>
    </c:when>
    <c:otherwise>
        <h1>Booklist non trovata</h1>
    </c:otherwise>
</c:choose>
<form action="FollowEditBooklist" method="get">
    <input type="hidden" name="id" value="${booklist.id}"/>
    <c:choose>
        <c:when test="${view!=null}">
            <input type="submit" name="edit" value="Modifica">
        </c:when>
        <c:otherwise>
            <input type="submit" name="follow" value="Segui Booklist"/>
        </c:otherwise>
    </c:choose>
</form>

<c:choose>
    <c:when test="${books!=null}">
        <table>
        <c:forEach items="${books}" var="book">
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
            </tr
        </c:forEach>
        </table>
    </c:when>
</c:choose>
</body>
</html>
