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
    <c:if test="${view!=null}">
    <input type="submit" name="Create" value="Nuova Booklist">
    </c:if>
    <c:choose>
        <c:when test="${booklists!=null}">
            <h1>Booklist create</h1><br>
            <table>
                <c:forEach items="${booklists}" var="booklist">
                    <tr>
                        <c:choose>
                            <c:when test="${view!=null}">
                                <td><a href="BooklistViewServlet?id=${booklist.id}&view=true">${booklist.name}</a></td>
                                <td>
                                    <input type="hidden" name="id" value="${booklist.id}">
                                    <input type="submit" name="delete" value="Elimina">
                                    <input type="submit" name="edit" value="Modifica">
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="BooklistViewServlet?id=${booklist.id}">${booklist.name}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            Nessuna booklist Creata<br>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${followed!=null}">
            <h1>Booklist seguite</h1>
            <table>
                <c:forEach items="${followed}" var="booklist">
                    <tr>
                        <c:choose>
                            <c:when test="${view!=null}">
                                <td><a href="BooklistViewServlet?id=${booklist.id}&view=true">${booklist.name}</a></td>
                                <td>
                                    <input type="hidden" name="id" value="${booklist.id}"/>
                                    <input type="submit" name="unFollow" value="unFollow">
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="BooklistViewServlet?id=${booklist.id}">${booklist.name}</a></td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            Nessuna Booklist seguita
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
