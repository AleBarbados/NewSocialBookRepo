<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Page</title>
    <meta charset="UTF-8">
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
<h1 style="margin-left: 40%"> Parola cercata: ${parolaCercata}</h1>
<br>
<br>
<div class="recomended-sec">
    <div class="row">
    <c:forEach items="${books}" var="book">
        <div class="col-lg-3 col-md-6">
            <div class="item">
                <img style="max-width: 225px; max-height: 367px" alt="immagine prodotto" src="${pageContext.request.contextPath}/images/${book.image}">
                <h3>${book.title}</h3>
                <h6><span class="price">${book.price_cent/100} euro</span></h6>
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
    <c:if test="${empty books}">
        <h3>Nessun prodotto conforme alla ricerca.</h3>
    </c:if>
</body>
</html>