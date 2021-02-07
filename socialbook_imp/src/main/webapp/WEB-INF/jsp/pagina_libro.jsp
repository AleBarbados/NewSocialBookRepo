<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Page</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<section class="product-sec">
    <div class="container">
        <h1>${book.title}</h1>
        <div class="row">
            <div class="col-md-6 slider-sec">
                <!-- main slider carousel -->
                <div id="myCarousel" class="carousel slide">
                    <!-- main slider carousel items -->
                    <div class="carousel-inner">
                        <div class="active item carousel-item" data-slide-number="0">
                            <img class="img-fluid" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}">
                        </div>
                    </div>
                </div>
                <!--/main slider carousel-->
            </div>
            <div class="col-md-6 slider-content">
                <p>Trama: ${book.plot}</p> <br>
                <p>Anno di pubblicazione: ${book.publication_year}</p>
                <p>Casa di pubblicazione: ${book.publishing_house}</p>
                <p>Genere: ${book.genre}</p>
                <ul>
                    <li>
                        <span class="name">Prezzo</span><span class="clm">:</span>
                        <span class="price final">${book.price_cent/100}</span>
                    </li>
                </ul>
                <div class="btn-sec">
                    <c:if test="${personalCustomer != null}">
                        <tr>
                            <td>
                                <form action="EditCreaBooklist" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" class="btn" name="addPreferiti" value="Aggiungi ai Preferiti">
                                </form>
                                <form action="ScegliBooklistServlet" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" class="btn black" value="Aggiungi a Booklist">
                                </form>
                                <form action="show-cart-servlet" method="get">
                                    <input type="hidden" name="isbn" value="${book.isbn}">
                                    <input type="submit" class="btn" name="addCart" value="Aggiungi al carrello">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </div>
            </div>
            <div class="recenzione">
                <br><br>
                <c:if test="${personalCustomer != null}">
                    <c:if test="${recensione_no == null}">
                        <form action="reviewServlet" method="get">
                            <c:if test="${vote != null || recensione_si != null}">
                                Voto:
                                <div class="c4l-rating">
                                    <input name="voto" type="radio" id="c4l-rate1" value="1" />
                                    <label for="c4l-rate1"></label>

                                    <input name="voto" type="radio" id="c4l-rate2" value="2" />
                                    <label for="c4l-rate2"></label>

                                    <input name="voto" type="radio" id="c4l-rate3" value="3" />
                                    <label for="c4l-rate3"></label>

                                    <input name="voto" type="radio" id="c4l-rate4" value="4" />
                                    <label for="c4l-rate4"></label>

                                    <input name="voto" type="radio" id="c4l-rate5" value="5" checked />
                                    <label for="c4l-rate5"></label>
                                </div>
                            </c:if>
                            <c:if test="${body != null || recensione_si != null}">
                                <textarea name="commento" placeholder="Aggiungi un commento ..." cols="110" rows="0.2"></textarea>
                            </c:if>
                            <input type="hidden" name="isbn" value="${book.isbn}">
                            <input type="submit" class="btn" style="margin-bottom: 44pt; margin-left: 1pt" value="conferma recensione">
                        </form>
                    </c:if>
                </c:if>
            </div>
            <div class="recenzioni">
                <c:if test="${not empty recensioni}">
                    <c:forEach items="${recensioni}" var="rec">
                        <div class="breadcrumb">
                            <div class="container">
                                <c:forEach items="${customers}" var="cus">
                                   <c:if test="${rec.id_customer == cus.id_customer}">
                                        <a href="customerServlet?customerView=true&idCustomer=${cus.id_customer}">${cus.c_surname} ${cus.c_name}</a>
                                    </c:if>
                                </c:forEach>
                                <p>Data: ${rec.date}</p>
                                <p>Voto: ${rec.vote}</p>
                                <p>${rec.body}</p>
                                <c:choose>
                                    <c:when test="${customerManager != null}">
                                        <form action="customerManagerReviewServlet" method="get">
                                            <input type="hidden" name="r_id" value="${rec.id_review}">
                                            <input type="submit" class="btn black" value="rimuovi">
                                        </form>
                                    </c:when>
                                    <c:when test="${personalCustomer != null && personalCustomer.id_customer == rec.id_customer}">
                                        <form action="reviewServlet" method="post">
                                            <input type="hidden" name="isbn" value="${book.isbn}">
                                            <input type="hidden" name="rimuovi_rec" value="${rec.id_review}">
                                            <input type="submit" class="btn black" value="rimuovi">
                                        </form>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</section>
</body>
</html>
