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
<table>
    <c:forEach items="${booklists}" var="booklist">
        <tr>
            <a href="/"></a>
            <td>
                <input type="text" value="${author.name}" disabled>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
