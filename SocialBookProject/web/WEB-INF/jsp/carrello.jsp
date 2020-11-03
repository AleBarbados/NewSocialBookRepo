<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Carrello"/>
</jsp:include>
	<section>
		<h1>Carrello</h1>
		<grid>

		<c:forEach var="pq" items="${carrello.getLibri()}">
			<div col="1/3">
				<a href="#"><img src="img/prodotti/${pq.libri[pq.key].id}.jpg"></a>
			</div>
			<div col="2/3">
				<h3>${carrello.libri[pq.key].nome}</h3>
				<p>${carrello.libri[pq.key].descrizione}</p>
				<h5>Quantità: ${carrello.libro_quantita[pq.key]}, Prezzo unit.: ${carrello.libri[pq.key].prezzo} &euro;</h5>
				<form action="Carrello" method="post">
					<input type="hidden" name="prodId" value="${pq.libro.id}">
					<input type="hidden" name="setNum" value="0">
					<input type="submit" value="Rimuovi">
				</form>
			</div>
		</c:forEach>
		<c:if test="${empty carrello.prodotti}">
			<div col="1">Nessun libro nel carrello</div>
		</c:if>
		</grid>
	</section>
	<c:if test="${not empty carrello.prodotti}">
	<section>
		<grid>
		<div col="1/3">
			<h2>Totale: ${carrello.sumPrezzo} &euro;</h2>
		</div>
	
		<div col="1/3">
			<form action="todo">
				<input type="submit" value="Completa acquisto">
			</form>
		</div>
		</grid>
	</section>
	</c:if>
<%@include file="footer.html"%>
