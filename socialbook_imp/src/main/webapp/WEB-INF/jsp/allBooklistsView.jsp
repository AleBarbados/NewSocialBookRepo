<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 21/01/2021
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>AllBooklists</title>
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
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
    <section class="static about-sec">
        <div class="container">
            <form action="EditCreaBooklist" method="get">
                <c:if test="${idCustomer != null && idCustomer == personalCustomer.id_customer}">
                    <input class="btn" type="submit" name="Create" value="Nuova Booklist">
                </c:if>
            </form>
            <c:choose>
                <c:when test="${booklists != null}">
                    <h2>Booklist create</h2><br>
                    <div class="recomended-sec">
                        <div class="row">
                            <c:forEach items="${booklists}" var="booklist">
                                <c:choose>
                                    <c:when test="${idCustomer != null && idCustomer == personalCustomer.id_customer}">
                                        <div class="col-lg-3 col-md-6">
                                            <div class="item">
                                                <img alt="immagine booklist" src="images/${booklist.image}">
                                                <h3>${booklist.name}</h3>
                                                <div class="hover">
                                                    <a href="BooklistViewServlet?id=${booklist.id}&idCustomer=${personalCustomer.id_customer}">
                                                        <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                                                    </a>
                                                </div>
                                            </div>
                                            <a href="EditCreaBooklist?id=${booklist.id}&delete=true" class="btn black">Elimina</a>
                                            <a href="EditCreaBooklist?id=${booklist.id}&edit=true" class="btn">Modifica</a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-lg-3 col-md-6">
                                            <div class="item">
                                                <img alt="immagine booklist" src="${pageContext.request.contextPath}/images/${booklist.image}">
                                                <h3>${booklist.name}</h3>
                                                <div class="hover">
                                                    <a href="BooklistViewServlet?id=${booklist.id}&idCustomer=${idCustomer}">
                                                        <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                Nessuna booklist Creata<br>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${followed!=null}">
                    <h2>Booklist seguite</h2>
                    <div class="recomended-sec">
                        <div class="row">
                            <c:forEach items="${followed}" var="booklist">
                                <c:choose>
                                    <c:when test="${idCustomer == personalCustomer.id_customer}">
                                        <div class="col-lg-3 col-md-6">
                                           <div class="item">
                                                <img alt="immagine booklist" src="${pageContext.request.contextPath}/images/${booklist.image}">
                                                <h3>${booklist.name}</h3>
                                                <div class="hover">
                                                    <a href="BooklistViewServlet?id=${booklist.id}&idCustomer=${idCustomer}">
                                                        <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                                                    </a>
                                                </div>
                                            </div>
                                            <a href="FollowBooklistServlet?id=${booklist.id}&unFollow=true">UnFollow</a>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-lg-3 col-md-6">
                                            <div class="item">
                                                <img alt="immagine booklist" src="${pageContext.request.contextPath}/images/${booklist.image}">
                                                <h3>${booklist.name}</h3>
                                                <div class="hover">
                                                    <a href="BooklistViewServlet?id=${booklist.id}&idCustomer=${idCustomer}">
                                                        <span><i class="fa fa-long-arrow-right" aria-hidden="true"></i></span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                Nessuna Booklist seguita
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</body>
</html>
