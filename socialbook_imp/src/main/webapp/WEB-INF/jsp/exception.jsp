<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>

<html>
<head>
    <title>All tickets</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<section style="padding: 80px">
    <h1><%= exception.getMessage() %></h1>
</section>
</body>
<%@include file="footer.html"%>
</html>