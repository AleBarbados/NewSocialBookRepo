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
        <c:set var = "cus" value = "${customer}"/>
        <h1>${customer.c_usr}</h1>
    </c:when>
    <c:when test="${personalCustomer!=null&&view!=null}">
        <c:set var = "cus" value = "${personalCustomer}"/>
        <h1>${personalCustomer.c_usr}</h1>
    </c:when>
    <c:otherwise>
        <h1>UTENTE NON TROVATO</h1>
    </c:otherwise>
</c:choose>
<c:choose>
    <c:when test="${cus!=personalCustomer}">
        <form action="followEdit?id=${customer.id_customer}"><input type="submit" name="follow" value="Follow"></form>
    </c:when>
    <c:otherwise>
        <form action="followEdit"><input type="submit" name="edit" value="Edit Profile"></form>
    </c:otherwise>
</c:choose>
</body>
</html>
