<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="theme-color" content="#03a6f3">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:200,300,400,500,600,700,800,900" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/styles.css">
    <title>Account Customer</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<section class="product-sec">
    <div class="container">
        <c:choose>
            <c:when test="${customer!=null}">
                <h1>${customer.c_usr}</h1>
                <div class="row">
                    <div class="col-md-6 slider-sec">
                        <!-- main slider carousel -->
                        <div class="myCarousel" class="carousel slide">
                        <!-- main slider carousel items -->
                            <div class="carousel-inner">
                                <div class="active item carousel-item" data-slide-number="0">
                                    <img alt="immagine customer" src="${pageContext.request.contextPath}/images/${customer.image}">
                                </div>
                            </div>
                        </div>
                    </div>
                <div class="col-md-6 slider-content">
                    <p>${customer.description}</p>
                    <ul>
                        <c:choose>
                            <c:when test="${follow!=true&&idCustomer == personalCustomer.id_customer}">
                                <li>
                                    <form action="followEdit" method="post">
                                        <input type="hidden" name="id" value="${customer.id_customer}">
                                       <input class="btn" type="submit" name="follow" value="Follow">
                                    </form>
                                </li>
                            </c:when>
                            <c:when test="${follow==true&&idCustomer == personalCustomer.id_customer}">
                                <li>
                                    <form action="followEdit" method="post">
                                        <input type="hidden" name="id" value="${customer.id_customer}">
                                        <input class="btn black" type="submit" name="unFollow" value="UnFollow">
                                    </form>
                                </li>
                            </c:when>
                        </c:choose>
                        <li>
                            <form action="allBooklistServlet" method="get">
                                <input type="hidden" name="id" value="${customer.id_customer}">
                                <input class="btn black" type="submit" value="Mostra tutte le Booklist">
                            </form>
                        </li>
                    </ul>
                    <div class="btn-sec">
                        <form action="FollowersServlet" method="get">
                            <input type="hidden" value="${customer.id_customer}" name="id">
                            <input class="btn" type="submit" name="Following" value="Following">
                            <input class="btn black" type="submit" name="Followers" value="Followers">
                        </form>
                    </div>
                </div>
            </c:when>
            <c:when test="${personalCustomer!=null&&idCustomer == personalCustomer.id_customer}">
                <h1>${personalCustomer.c_usr}</h1>
                <div class="row">
                    <div class="col-md-6 slider-sec">
                        <!-- main slider carousel -->
                        <div class="myCarousel" class="carousel slide">
                            <!-- main slider carousel items -->
                            <div class="carousel-inner">
                                <div class="active item carousel-item" data-slide-number="0">
                                    <img alt="immagine customer" src="${pageContext.request.contextPath}/images/${personalCustomer.image}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 slider-content">
                        <p>${personalCustomer.description}</p>
                        <ul>
                            <li>
                                <form action="followEdit" method="get">
                                    <input type="hidden" name="editProfile" value="edit">
                                    <input class="btn" type="submit" value="Edit Profile">
                                </form>
                            </li>
                            <li>
                                <form action="allBooklistServlet" method="get">
                                    <input type="hidden" name="id" value="${personalCustomer.id_customer}">
                                    <input class="btn black" type="submit" value="Mostra Booklist">
                                </form>
                            </li>
                            <li>
                                <form action="FollowEditBooklist" method="get">
                                    <input class="btn" type="submit" name="Create" value="Nuova Booklist">
                                </form>
                            </li>
                            <li> <a href = "all-ticket-servlet">tickets</a></li>
                            <li> <a href = "all-order-servlet">ordini</a></li>

                        </ul>
                        <div class="btn-sec">
                            <form action="FollowersServlet" method="get">
                                <input type="hidden" value="${personalCustomer.id_customer}" name="id">
                                <input class="btn" type="submit" name="Following" value="Following">
                                <input class="btn black" type="submit" name="Followers" value="Followers">
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <h1>UTENTE NON TROVATO</h1>
            </c:otherwise>
        </c:choose>
    </div>
</section>
<section class="related-books">
    <div class="container">
        <h2>Preferiti</h2>
        <div class="recomended-sec">
            <div class="row">
                <c:forEach items="${preferiti}" var="book">
                <div class="col-lg-3 col-md-6">
                    <div class="item">
                        <a href="paginaLibroServlet?libro=${book.isbn}"><img alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}"></a>
                        <h3>${book.title}</h3>
                        <h6><span class="price">${book.price_cent/100}euro</span></h6>
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
    </div>
</section>
</body>
</html>
