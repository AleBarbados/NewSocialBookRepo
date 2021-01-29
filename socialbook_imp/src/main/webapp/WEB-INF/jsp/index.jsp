<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<form action="customerServlet" method="get">
    <input type="hidden" name="personalView" value="view">
    <input type="submit" name="personalView" value="Account">
</form>
<form action="customerServlet" method="get">
    <input type="submit" name="costumerView" value="Utente">
    <input type="hidden" name="idCustomer" value="2">
</form>
<form action="newCustomerServlet" method="get">
    <input type="submit" name="toRegistrationServlet" value="Registrati">
</form>

<form action="login-servlet" id = "form-container" method="post" class="form-container">
    <h2>Login</h2>
    <label for = "username" ><b>Username</b></label>
    <br>
    <input type="text" placeholder="Enter Username" width="150px" id ="username" name = "username" required>

    <label for="pwd"><b>Password</b></label><br>
    <input type="password" placeholder="Enter Password" width="150px" id ="pwd" name = "pwd" required>

    <button type="submit" class="btn" onclick="accesso()">Login</button>
</form>

<br>
<form action="mostraLibriServlet" method="get">
    <input type="submit" value="Mostra tutti i libri">
</form>
<br>

<br>
<form action="customerManagerServlet" method="get">
    <input type="submit" value="Mostra tutti gli utenti">
</form>
<br>

<br>
<form action="paginaLibroServlet" method="get">
    <input type="hidden" name="libro" value="${ISBN}">
    <input type="submit" value="Mostra libro">
</form>
<br>

<br>
<form action="ricercaServlet" method="get">
    <input type="text" placeholder="Cerca..." name="query" value="<c:out value='${param.query}'/>">
    <button type="submit">search</button>
</form>
<br>
<a href="ticket-view-servlet?id=null&name=newTicket"> Contattaci</a>
<br>
<a href="logout-servlet" onclick="return confirm('sicuro di voler uscire?');"  >Logout</a>

<script>
    function accesso(){

        document.getElementById("form-container").style.display = "none";
        document.getElementById("logout-button").style.display = "block";
    }
</script>

</body>
</html>