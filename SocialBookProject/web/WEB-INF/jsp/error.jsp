<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<jsp:include page="header.jsp">
	<jsp:param name="pageTitle" value="Errore ${requestScope['javax.servlet.error.status_code']}"/>
</jsp:include>
	<section>
		<h1>Errore ${requestScope['javax.servlet.error.status_code']}</h1>
		<!-- Scegliere una delle due opzioni per stampare l'eccezione:
		la prima per stampa un breve messaggio di errore, la seconda lo stacktrace completo. -->
		<pre>${requestScope['javax.servlet.error.exception']}</pre>
		<pre><%
				if (exception != null) {
					out.flush();
					exception.printStackTrace(response.getWriter());
				}
			%></pre>
	</section>
<%@include file="footer.html"%>
