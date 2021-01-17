<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<form action="customerServlet" method="get">
    <input type="submit" name="personalView" value="Account">
</form>
<table>
    <tr>
        <th>menu</th>
    </tr>
    <tr>
        <th><a href="WEB-INF/jsp/registration.jsp">Registrazione</a></th>

    </tr>
</table>
</body>
</html>