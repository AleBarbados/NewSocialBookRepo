<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Book Page</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
    <h3>Parola cercata: ${parolaCercata}</h3>
    <c:forEach items="${books}" var="book">
        <a href="paginaLibroServlet?libro=<c:out value="${book.isbn}"/>">
            <h3>${book.title}</h3>
            <img src="images/${book.image}" alt="immagine prodotto" width="70px" height=70px">
        </a>
    </c:forEach>

    <c:if test="${empty books}">
        <h3>Nessun prodotto conforme alla ricerca.</h3>
    </c:if>
</body>
</html>