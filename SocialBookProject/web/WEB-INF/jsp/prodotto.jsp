<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="${libro.nome}"/>
</jsp:include>
	<section>
		<grid>
	
			<div col="1/3">
				<img src="img/prodotti/${libro.id}.jpg">
			</div>
		
			<div col="1/3">
				<h3>${libro.nome}</h3>
				${libro.descrizione}
			</div>
		
			<div col="1/3">
				<c:if test="${utente.admin}">
					<form action="AdminProdotto" method="post">
						<input type="hidden" name="id" value="${libro.id}">
						<input type="submit" value="Modifica">
						<input type="submit" name="rimuovi" value="Rimuovi">
					</form>
				</c:if>
				<p>Categorie:
					<c:forEach items="${libro.categorie}" var="categoria" varStatus="status">
						<a href="Categoria?id=<c:out value="${categoria.id}"/>"><c:out value="${categoria.nome}" /></a><c:if test="${not status.last}">, </c:if>
					</c:forEach>
				</p>
				<h4>Prezzo: ${libro.prezzoEuro} &euro;</h4>
				<form action="Carrello" method="post">
					<label>Quantità:</label>
					<select name="addNum">
						<c:forEach begin="1" end="30" varStatus="loop">
							<option value="${loop.index}">${loop.index}</option>
						</c:forEach>
					</select>
					<input type="hidden" name="prodId" value="${libro.id}">
					<input type="submit" value="Aggiungi al carrello">
				</form>
			</div>
	
		</grid>
	</section>
<%@include file="footer.html"%>
