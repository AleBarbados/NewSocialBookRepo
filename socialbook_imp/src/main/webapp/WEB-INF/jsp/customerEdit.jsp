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
    <form action="customerEdit" method="post" enctype="multipart/form-data">
        <label>Cambia Password:
            <input type="text" name="password">
        </label>
        <label>Cambia Descrizione:
            <input type="text" name="descrizione">
        </label>
        <label>Immagine
            <input type="file" name="foto">
        </label>

        <input type="hidden" name="editProfile" value="editProfile">
        <input type="submit" value="EditProfile">
    </form>
</body>
</html>
