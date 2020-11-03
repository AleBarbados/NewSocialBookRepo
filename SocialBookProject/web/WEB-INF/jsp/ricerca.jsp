<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="${param.q}"/>
</jsp:include>
	<section>
		<grid>
			<c:forEach items="${prodotti}" var="libro">
				<div col="1/3">
					<a href="#"><img src="img/prodotti/${libro.id}.jpg"></a>
				</div>
				<div col="2/3">
					<h3>
						<a href="Prodotto?id=${libro.id}">${libro.nome}</a>
					</h3>
					<p>${libro.descrizione}</p>
					<h4>Prezzo: ${libro.prezzoEuro} &euro;</h4>
				</div>
			</c:forEach>
			<c:if test="${empty prodotti}">
				<div col="1">Nessun libro trovato.</div>
			</c:if>
		</grid>
	</section>
<%@include file="footer.html"%>
