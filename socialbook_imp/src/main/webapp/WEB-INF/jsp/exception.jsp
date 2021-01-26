<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true"%>

<jsp:include page="/WEB-INF/results/header.jsp">
    <jsp:param name="pageTitle" value="Errore"/>
</jsp:include>

<section style="padding: 80px">
    <h1><%= exception.getMessage() %></h1>
</section>

<%@include file="footer.html"%>
