<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SocialBook</title>
</head>
<body>
<div class="main-menu">
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light">
            <a class="navbar-brand" href="index.jsp"><img src="/images/logo.jpeg" alt="logo"></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto">
                    <li class="navbar-item active">
                        <a href="index.jsp" class="nav-link">Home</a>
                    </li>
                    <li class="navbar-item">
                        <a href="mostraLibriServlet" class="nav-link">Shop</a>
                    </li>
                    <li class="navbar-item">
                        <a href="customerServlet" class="nav-link">Account</a>
                    </li>
                    <li class="navbar-item">
                        <form action="login-servlet" id = "form-container" method="post" class="form-container">
                            <h5>Login</h5>
                            <label for = "username" ><b>Username</b></label>
                            <br>
                            <input type="text" placeholder="Enter Username" width="150px" id ="username" name = "username" required>

                            <label for="pwd"><b>Password</b></label><br>
                            <input type="password" placeholder="Enter Password" width="150px" id ="pwd" name = "pwd" required>

                            <button type="submit" class="btn" onclick="accesso()">Login</button>
                        </form>
                        <a href="logout-servlet" class="nav-link" onclick="return confirm('sicuro di voler uscire?');">Logout</a>
                    </li>
                    <li class="navbar-item">
                        <a href="newCustomerServlet" class="nav-link">Registrati</a>
                    </li>
                </ul>
                <div class="cart my-2 my-lg-0">
                            <span>
                                <i class="fa fa-shopping-cart" aria-hidden="true"></i></span>
                    <span class="quntity">3</span>
                </div>
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search here..." aria-label="Search">
                    <span class="fa fa-search"></span>
                </form>
            </div>
        </nav>
    </div>
</div>
</body>
</html>
