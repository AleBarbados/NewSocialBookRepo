<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 23/01/21
  Time: 01:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New ticket</title>
</head>
<body>
    <c:choose>
        <c:when test="${personalCustomer} != null">
    <form id = "new-ticket" name="new-ticket" method="post" action="new-ticket-servlet">
        <label for = "issue">Problema:</label><br>
        <textarea id = "issue"></textarea>
        <label for = "destination"> Problema di:</label>
        <select name="destination" id="destination">
            <option value="users"> utenza</option>
            <option value="system"> sistema </option>
        </select>
        <button type="submit" value="procedi"> Procedi </button>
    </form>
        </c:when>
        <c:otherwise>
            Tu non potevi essere qui, come ti permetti!
        </c:otherwise>
    </c:choose>
</body>
</html>
