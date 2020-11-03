<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Errore"/>
</jsp:include>
	<section>
		<h1><%= exception.getMessage() %></h1>
	</section>
<%@include file="footer.html"%>
