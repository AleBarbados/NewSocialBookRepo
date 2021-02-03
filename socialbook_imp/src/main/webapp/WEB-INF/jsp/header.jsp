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
    <div class="header-top">
        <div class="container">
            <div class="row">
                <div class="col-md-3"><a href="#" class="web-url">www.bookstore.com</a></div>
                <div class="col-md-6">
                    <h5>Free Shipping Over $99 + 3 Free Samples With Every Order</h5></div>
                <div class="col-md-3">
                    <span class="ph-number">Call : 800 1234 5678</span>
                </div>
            </div>
        </div>
    </div>
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
                                    <a href="" class="nav-link">Contattaci</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" class="nav-link">Log-out</a>
                                </li>
                            </ul>

                            <div class="cart my-2 my-lg-0">
                                <span>
                                    <a href="show-cart-servlet"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a></span>
                                <span class="quntity">3</span>
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
                                    <a href="logout-servlet" class="nav-link">Log-out</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:when test="${systemManager != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="all-ticket-servlet" class="nav-link">Ticket</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" class="nav-link">Log-out</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:when test="${catalogueManager != null}">
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="catalogueManagerServlet1" class="nav-link">Prodotti</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="all-ticket-servlet" class="nav-link">Ticket</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="logout-servlet" class="nav-link">Log-out</a>
                                </li>
                            </ul>
                        </c:when>
                        <c:otherwise>
                            <ul class="navbar-nav ml-auto">
                                <li class="navbar-item">
                                    <a href="" class="nav-link">Contattaci</a>
                                </li>
                                <li class="navbar-item">
                                    <a href="login-servlet" class="nav-link">Log-in</a>
                                </li>
                            </ul>

                            <div class="cart my-2 my-lg-0">
                                <span>
                                    <a href="show-cart-servlet"><i class="fa fa-shopping-cart" aria-hidden="true"></i></a></span>
                                <span class="quntity">3</span>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <form class="form-inline my-2 my-lg-0">
                        <input class="form-control mr-sm-2" type="search" placeholder="Search here..." aria-label="Search">
                        <span class="fa fa-search"></span>
                    </form>
                </div>
            </nav>
        </div>
    </div>
</header>
</body>
</html>
