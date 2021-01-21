<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 21/01/2021
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AllBooklists</title>
</head>
<body>
<form action="FollowEditBooklist" method="get">
    <input type="submit" name="Create" value="Nuova Booklist">
    <c:choose>
        <c:when test="${booklists}!=null">
        <table>
            <c:forEach items="${booklists}" var="booklist">
                <tr>
                    <td><a href="BooklistViewServlet?id=${booklist.id}">${booklist.name}</a></td>
                    <td>
                        <input type="submit" name="follow" value="Segui Booklist">
                        <input type="submit" name="delete" value="Elimina">
                        <input type="submit" name="edit" value="Modifica">
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:when>
        <c:otherwise>
            Non ci sono Booklist
        </c:otherwise>
    </c:choose>
<input type="submit" name="Create" value="Nuova Booklist">
<table>
    <c:forEach items="${booklists}" var="booklist">
        <tr>
            <td><a href="BooklistViewServlet?id=${booklist.id}">${booklist.name}</a></td>
            <td>
                    <input type="submit" name="follow" value="Segui Booklist">
                    <input type="submit" name="delete" value="Elimina">
                    <input type="submit" name="edit" value="Modifica">
            </td>
        </tr>
    </c:forEach>
</table>
</form>
</body>
</html>
