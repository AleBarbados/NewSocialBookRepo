<%@ page import="socialbook.model.GestioneDatabase.CustomerDAO" %>
<%@ page import="socialbook.model.GestioneDatabase.Customer" %>
<%@ page import="socialbook.model.GestioneDatabase.Follow" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luket
  Date: 19/01/2021
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>followers</title>
</head>

<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
<%
    CustomerDAO customerDAO = new CustomerDAO();
    ArrayList<Follow> follows = (ArrayList<Follow>) request.getAttribute("follows");
%>
<table>
    <%if(request.getAttribute("Following")!=null){
        if(follows.isEmpty()){%>
            <h1>Non segue nessuno</h1>
        <%}
        for (Follow follow:
            follows ) {
            Customer user = customerDAO.doRetriveById(follow.getCostumer());
        %>
        <tr><td><%=user.getC_usr()%></td><td><%=user.getDescription()%>></td></tr>
<%}%>
    <%}else if(request.getAttribute("Followers")!=null){
        if(follows.isEmpty()){%>
    <h1>Non ha seguaci</h1>
    <%}
    for (Follow follow:
            follows ) {
        Customer user = customerDAO.doRetriveById(follow.getFollower());
    %>
    <tr><td><%=user.getC_usr()%></td><td><%=user.getDescription()%>></td></tr>
    <%}%>
    <%}%>
</table>
</body>
</html>
