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
    <table>
        <tr>
            <td><img width="60px" height="60px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}"></td>
        </tr>
        <tr>
            <td><h2>${book.title}</h2></td>
        </tr>
        <tr>
            <td>${book.isbn}</td>
        </tr>
        <tr>
            <td>${book.genre}</td>
        </tr>
        <tr>
            <td>${book.price_cent}</td>
        </tr>
        <tr>
            <td>${book.publication_year}</td>
        </tr>
        <tr>
            <td>${book.publishing_house}</td>
        </tr>
        <tr>
            <td>${book.plot}</td>
        </tr>
    </table>
    <br><br>
    <c:if test="${personalCustomer != null}">
        <tr>
            <td>
                <form action="FollowEditBooklist" method="get">
                    <input type="hidden" name="isbn" value="${book.isbn}">
                    <input type="submit" name="addPreferiti" value="Aggiungi ai Preferiti">
                </form>
                <form action="ScegliBooklistServlet" method="get">
                    <input type="hidden" name="isbn" value="${book.isbn}">
                    <input type="submit" value="Aggiungi a Booklist">
                </form>
                </form>
                <form action="show-cart-servlet" method="get">
                    <input type="hidden" name="isbn" value="${book.isbn}">
                    <input type="submit" name = "addCart" value="Aggiungi al carrello">
                </form>
            </td>
        </tr>
    </c:if>
    <c:if test="${not empty recensioni}">
        <table id="boh">
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>vote</th>
                <th>body</th>
                <th>date</th>
            </tr>
                <c:forEach items="${recensioni}" var="rec">
                    <tr>
                        <c:forEach items="${customers}" var="cus">
                            <c:if test="${rec.id_customer == cus.id_customer}">
                                <td>${cus.c_surname}</td>
                                <td>${cus.c_name}</td>
                            </c:if>
                        </c:forEach>
                        <td>${rec.vote}</td>
                        <td>${rec.body}</td>
                        <td>${rec.date}</td>
                        <c:choose>
                            <c:when test="${customerManager != null}">
                                <td>
                                    <form action="customerManagerReviewServlet" method="get">
                                        <input type="hidden" name="r_id" value="${rec.id_review}">
                                        <input type="submit" value="rimuovi">
                                    </form>
                                </td>
                            </c:when>
                            <c:when test="${personalCustomer != null && personalCustomer.id_customer == rec.id_customer}">
                                <td>
                                    <form action="reviewServlet" method="post">
                                        <input type="hidden" name="isbn" value="${book.isbn}">
                                        <input type="hidden" name="rimuovi_rec" value="${rec.id_review}">
                                        <input type="submit" value="rimuovi">
                                    </form>
                                </td>
                            </c:when>
                        </c:choose>
                    </tr>
                </c:forEach>
        </table>
    </c:if>
    <br><br>
    <c:if test="${personalCustomer != null}">
       <c:if test="${recensione_no == null}">
           <form action="reviewServlet" method="get">
               <c:if test="${vote != null || recensione_si != null}">
                   <label>Voto:</label>
                   <select name="voto">
                       <option value="-">-</option>
                       <option value="1">1</option>
                       <option value="2">2</option>
                       <option value="3">3</option>
                       <option value="4">4</option>
                       <option value="5">5</option>
                   </select>
               </c:if>
               <c:if test="${body != null || recensione_si != null}">
                   <textarea name="commento" placeholder="Aggiungi un commento ..." cols="20" rows="20"></textarea>
               </c:if>
               <input type="hidden" name="isbn" value="${book.isbn}">
               <input type="submit" value="conferma recensione">
           </form>
       </c:if>
    </c:if>
</body>
</html>
