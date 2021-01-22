<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Utenti</title>
</head>

<body>
    <table>
        <tr>
            <th>Immagine</th>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Descrizione</th>
            <th>Operazione</th>
        </tr>
        <c:forEach items="${customers}" var="c">
            <tr>
                <td><img width="100px" height="100px" alt="immagine customer" src="${pageContext.request.contextPath}/images/${c.image}"></td>
                <td>${c.c_name}</td>
                <td>${c.c_surname}</td>
                <td>${c.description}</td>
                <td>
                    <form action="customerManagerServlet" method="get">
                        <input type="hidden" name="id" value="${c.id_customer}">
                        <input type="submit" value="Rimuovi">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
