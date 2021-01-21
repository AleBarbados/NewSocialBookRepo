  Created by IntelliJ IDEA.
  User: luket
  Date: 14/01/2021
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Customer</title>
</head>
<body>
<h1>we bucchin</h1>
<c:choose>
    <c:when test="${customer!=null}">
        <h1>${customer.c_usr}</h1>
        <img width="60px" height="60px" alt="immagine customer" src="${pageContext.request.contextPath}/images/${customer.image}">
        <c:choose>
            <c:when test="${follow!=true&&view!=true}">
                <form action="followEdit">
                    <input type="submit" name="follow" value="Follow">
                    <input type="hidden" name="id" value="${customer.id_customer}">
                </form>
            </c:when>
            <c:when test="${follow==true&&view!=true}">
                <form action="followEdit">
                    <input type="submit" name="unFollow" value="UnFollow">
                    <input type="hidden" name="id" value="${customer.id_customer}">
                </form>
            </c:when>
            <form action="FollowersServlet" method="get">
                <input type="hidden" value="${customer.id_customer}" name="id">
                <input type="submit" name="Following" value="Following">
                <input type="submit" name="Followers" value="Followers">
            </form>
        </c:choose>
        <form action="allBooklistServlet" method="get">
            <input type="hidden" name="id" value="${customer.id_customer}">
            <input type="submit" value="Mostra tutte le Booklist">
        </form>
    </c:when>
    <c:when test="${personalCustomer!=null&&view==true}">
        <h1>${personalCustomer.c_usr}</h1>
        <img width="60px" height="60px" alt="immagine customer" src="${pageContext.request.contextPath}/images/${personalCustomer.image}">
        <form action="followEdit" method="get">
            <input type="hidden" name="editProfile" value="edit">
            <input type="submit" value="Edit Profile">
        </form>
        <form action="FollowersServlet" method="get">
            <input type="hidden" value="${personalCustomer.id_customer}" name="id">
            <input type="submit" name="Following" value="Following">
            <input type="submit" name="Followers" value="Followers">
        </form>
        <form action="/booklistView" method="get">
            <input type="hidden" name="id" value="${personalCustomer.id_customer}">
            <input type="submit" value="Mostra Booklist">
        </form>
    </c:when>
    <c:otherwise>
        <h1>UTENTE NON TROVATO</h1>
    </c:otherwise>
</c:choose>

</body>
</html>
