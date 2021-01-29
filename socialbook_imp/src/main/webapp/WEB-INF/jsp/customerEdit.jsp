<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Your Profile</title>
</head>
<body>
    <form name = "editForm" action="customerEdit" method="post">
        <div>
            <img width="80px" height="80px" src="images/${personalCustomer.image}" alt="0">
            <input type="radio" name="foto" value="b4.jpg" checked>
        </div>
        <div>
            <img width="80px" height="80px" src="images/b1.jpg" alt="1">
            <input type="radio" name="foto" value="b1.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b2.jpg" alt="2">
            <input type="radio" name="foto" value="b2.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b3.jpg" alt="3">
            <input type="radio" name="foto" value="b3.jpg">
        </div>
        <div>
            <img width="80px" height="80px" src="images/b4.jpg" alt="4">
            <input type="radio" name="foto" value="b4.jpg">
        </div>
        <br>
        <label>Cambia Password:
            <input type="password" name="password" value="${personalCustomer.c_pwd}">
        </label>
        <label>Cambia Descrizione:</label>
            <input type="text" name="descrizione" value="${personalCustomer.description}">


        <input type="hidden" name="editProfile" value="editProfile">
        <input type="submit" onclick="Check()" value="Edit Profile">
    </form>

</body>
</html>
