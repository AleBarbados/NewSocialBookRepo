<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocialBook</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#03a6f3">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:200,300,400,500,600,700,800,900" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<header>
    <div class="main-menu">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light">
                <a class="navbar-brand" href="index.html"><img src="images/logo.jpeg" alt="logo"></a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <c:choose>
                        <c:when test="${personalCustomer != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item active">
                                    <a href="allBooklistServlet?id=${personalCustomer.id_customer}" class="nav-link">Booklist</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="customerServlet?personalView=true" class="nav-link">Area Utente</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="all-order-servlet">Ordini</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="ticket-view-servlet?id=null&name=newTicket" class="nav-link">Contattaci</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" onclick="return confirm('sicuro di voler uscire?');" class="nav-link">Logout</a>
                                </li>
                            </ul>

                            <div class="cart my-2 my-lg-0">
                                <span>
                                    <a href="show-cart-servlet"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a></span>
                                <span class="quntity">0</span>
                            </div>
                        </c:when>
                        <c:when test="${customerManager != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="customerManagerServlet" class="nav-link">Utenti</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="all-ticket-servlet" class="nav-link">Ticket</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" onclick="return confirm('sicuro di voler uscire?');" class="nav-link">Logout</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:when test="${systemManager != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="all-ticket-servlet" class="nav-link">Ticket</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" onclick="return confirm('sicuro di voler uscire?');" class="nav-link">Logout</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:when test="${catalogueManager != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="mostraLibriServlet" class="nav-link">Libri</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="all-ticket-servlet" class="nav-link">Ticket</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" onclick="return confirm('sicuro di voler uscire?');" class="nav-link">Logout</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="ticket-view-servlet?id=null&name=newTicket" class="nav-link">Contattaci</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="#" class="nav-link">Login<span style="font-size:8px">&nabla;</span></a>
                                    <ul class="hidden">
                                        <li>
                                            <form action="login-servlet" id = "form-container" method="post" class="form-container">
                                                <b>Login</b>
                                                <label for = "username" ><b>Username</b></label>
                                                <br>
                                                <input type="text" placeholder="Enter Username" width="150px" id ="username" name = "username" required>

                                                <label for="pwd"><b>Password</b></label><br>
                                                <input type="password" placeholder="Enter Password" width="150px" id ="pwd" name = "pwd" required>

                                                <button type="submit" class="btn" onclick="accesso()">Login</button>
                                            </form>
                                        </li>
                                        <li><a href="newCustomerServlet">Registrazione</a></li>
                                    </ul>
                                </li>
                            </ul>
                            <div class="cart my-2 my-lg-0">
                                <span>
                                    <a href="show-cart-servlet"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a></span>
                                <span class="quntity">3</span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <form class="form-inline my-2 my-lg-0" action="ricercaServlet" method="get">
                        <input class="form-control mr-sm-2" type="search" placeholder="cerca" aria-label="Search" name="query" value="<c:out value='${param.query}'/>">
                        <span class="fa fa-search"><button type="submit">search</button></span>
                    </form>
                </div>
            </nav>
        </div>
    </div>
</header>
<script>
    function accesso(){

        document.getElementById("form-container").style.display = "none";
        document.getElementById("logout-button").style.display = "block";
    }
</script>
</body>
</html>
