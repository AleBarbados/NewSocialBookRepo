<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 26/01/2021
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Scegli Booklist</title>
</head>
<body>

<c:choose>
    <c:when test="${booklists!=null}">
        <table>
            <c:forEach items="${booklists}" var="booklist">
                <tr>
                    <td><a href="AddBookBooklistServlet?id=${booklist.id}&isbn=${isbn}">${booklist.name}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        Non ci sono Booklist
    </c:otherwise>
</c:choose>
</body>
</html>
