<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SocialBook HomePage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#03a6f3">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:200,300,400,500,600,700,800,900" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
<section class="slider">
    <div class="container">
        <div id="owl-demo" class="owl-carousel owl-theme">
            <c:forEach items="${utentiHome}" var="user">
                <div class="item">
                    <div class="slide">
                        <img src="${pageContext.request.contextPath}/images/${user.image}" alt="slide1">
                        <div class="content">
                            <div class="title">
                                <h3>${user.c_usr}</h3>
                                <h5>${user.description}</h5>
                                <a href="customerServlet?idCustomer=${user.id_customer}&customerView=true" class="btn">View</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<section class="recomended-sec">
    <div class="container">
        <div class="title">
            <h2>highly recommendes books</h2>
            <hr>
        </div>
        <div class="row">
            <c:forEach items="${libriHome}" var="book">
                <div class="col-lg-3 col-md-6">
                    <div class="item">
                        <img src="${pageContext.request.contextPath}/images/${book.image}" alt="book">
                        <h3>${book.title}</h3>
                        <h6><span class="price">${book.price_cent/100}</span>euro</h6>
                        <div class="hover">
                            <a href="paginaLibroServlet?libro=${book.isbn}">
                                <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<section class="about-sec">
    <div class="about-img">
        <figure style="background:url(/images/about-img.jpg)no-repeat;"></figure>
    </div>
    <div class="about-content">
        <h2>About bookstore,</h2>
        <p>SocialBook si propone come un social innovativo che permette la nascita di una vasta community unita dalla passione comune per i libri e, allo stesso tempo, ne mette a</p>
        <p>disposizione anche l’acquisto (in formato digitale). Puoi guardare il nostro catalogo, creare tue booklist e seguire altri utenti, REGISTRATI SUBITO!</p>
        <div class="btn-sec">
            <a href="mostraLibriServlet" class="btn yellow">Guarda il nostro catalogo</a>
            <a href="newCustomerServlet" class="btn black">Registrati!</a>
        </div>
    </div>
</section>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/owl.carousel.min.js"></script>
<script src="js/custom.js">
</script>
</body>
</html>