<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 16/01/2021
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Your Profile</title>
</head>
<body>
    <form action="editServlet" method="post">
        <label>Cambia Password:
            <input type="text" name="password" value="password">
        </label>
        <label>Cambia Descrizione:
            <input type="text" name="descrizione" value="descrizione">
        </label>
        <input type="submit" value="EditProfile">
    </form>
</body>
</html>
