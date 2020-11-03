<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Home"/>
</jsp:include>
	<section>
		<h1>Descrizione dello store, offerte, ecc.</h1>
		<p>Paragraph one lorem ipsum dolor sit amet, consectetur
			adipisicing elit, sed do eiusmod tempor incididunt ut labore et
			dolore magna aliqua. Ut enim ad minim veniam, quis nostrud.</p>
	</section>

	<section>
		<h2>Prodotti in evidenza</h2>

		<grid>
		<c:forEach items="${prodotti}" var="libro">
			<div col="1/3">
				<h3>
					<a href="Prodotto?id=<c:out value="${libro.id}"/>"><c:out value="${libro.nome}" /></a>
				</h3>
				<a href="Prodotto?id=<c:out value="${libro.id}"/>"><img src="img/prodotti/<c:out value="${libro.id}"/>.jpg"></a>
				<h4>Prezzo: <c:out value="${libro.prezzoEuro}" /> &euro;</h4>
			</div>
		</c:forEach>
		</grid>
	</section>
<%@include file="footer.html"%>
