<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 23/01/2021
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifica Crea Booklist</title>
</head>
<body>
<form action="EditCreaBooklistServlet" method="post" enctype="multipart/form-data">
    <label>Immagine
        <input type="file" name="foto">
    </label>
    <c:if test="${operazione == 'edit'}">
        <input type="hidden" value="edit" name="edit">
        <input type="hidden" value="${booklist.id}" name="id">
        <label>Nome=
            <input type="text" name="nome" value="${booklist.name}"/>
        </label>
    </c:if>
    <c:if test="${operazione == 'Create'}">
        <input type="hidden" value="Create" name="Create">
        <label>Nome=
            <input type="text" name="nome"/>
        </label>
    </c:if>

    <input type="submit" value="Va al catalogo">
</form>
</body>
</html>
