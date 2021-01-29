<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 20/01/2021
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Booklist</title>
</head>
<body>
    <section class="static about-sec">
        <div class="container">
            <c:choose>
                <c:when test="${booklist!=null}">
                    <img width="60px" height="60px" alt="Immagine booklist" src="${pageContext.request.contextPath}/images/${booklist.image}">
                    <h2>${booklist.name}</h2>
                    <form action="FollowEditBooklist" method="get">
                        <input type="hidden" class="btn" name="id" value="${booklist.id}"/>
                        <c:choose>
                            <c:when test="${idCustomer == personalCustomer.id_customer}">
                                <input type="submit" class="btn" name="edit" value="Modifica">
                                <input type="submit" class="btn black" name="delete" value="Elimina">
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${follow!=null}">
                                        <input type="submit" class="btn black" name="unFollow" value="unFollow">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" class="btn" name="follow" value="Segui Booklist">
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </form>
                    <div class="recomended-sec">
                        <div class="row">
                            <c:choose>
                                <c:when test="${books!=null}">
                                    <c:forEach items="${books}" var="book">
                                        <div class="col-lg-3 col-md-6">
                                            <div class="item">
                                                <img alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}">
                                                <h3>${book.title}</h3>
                                                <h6><span class="price">${book.price_cent/100} euro</span> / <a href="#">Buy Now</a></h6>
                                                <div class="hover">
                                                    <a href="product-single.html">
                                                        <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h1>Booklist non trovata</h1>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</body>
</html>