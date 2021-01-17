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
<form action="customerServlet" method="get">
    <input type="submit" name="costumerView" value="Utente">
    <input type="hidden" name="customer"  value="2">
</form>
</body>
</html>