<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifica Crea Booklist</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<form action="EditCreaBooklistServlet" method="post">
    <c:if test="${operazione == 'edit'}">
        <input type="hidden" name="edit" value="edit">
        <input type="hidden" value="${booklist.id}" name="id">
        <label>Nome=
            <input type="text" name="nome" value="${booklist.name}" required>
        </label>
    </c:if>
    <c:if test="${operazione == 'Create'}">
        <input type="hidden" value="Create" name="Create">
        <label>Nome=
            <input type="text" name="nome" required>
        </label>
        <div>
            <img width="80px" height="80px" src="images/b1.jpg" alt="1">
            <input type="radio" name="foto" value="b1.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b2.jpg" alt="2">
            <input type="radio" name="foto" value="b2.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b3.jpg" alt="3">
            <input type="radio" name="foto" value="b3.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b4.jpg" alt="4">
            <input type="radio" name="foto" value="b4.jpg">
        </div>
    </c:if>

    <input type="submit" value="Salva">
</form>
</body>
</html>
