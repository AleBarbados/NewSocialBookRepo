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
    <h1>booklist.booklist_name</h1>
    </c:when>
    <c:otherwise>
        <h1>Booklist non trovata</h1>
    </c:otherwise>
</c:choose>
<form action="followBooklist" method="get">
    <input type="hidden" name="id" value="${booklist.id_booklist}"/>
    <input type="submit" value="Segui Booklist"/>
</form>
</body>
</html>
