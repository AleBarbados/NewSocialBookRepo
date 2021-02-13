<%@page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>

<html>
<head>
    <title>All tickets</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<section>
    <h1>Errore ${requestScope['javax.servlet.error.status_code']}</h1>
    <!-- Scegliere una delle due opzioni per stampare l'eccezione:
    la prima per stampa un breve messaggio di errore, la seconda lo stacktrace completo. -->
</section>
</body>
</html>