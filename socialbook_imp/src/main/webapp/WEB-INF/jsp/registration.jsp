<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 16/01/21
  Time: 23:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registrazione</title>
</head>
<body class="body-registrati" onload='document.form.email.focus()'>

<div class = "main">
    <ul>
        <li><h3>Registrati:</h3></li>

        <li>
            <div class="mail">
                <form id = "sub_form" name = "form" action = "registration-servlet" method="post" onsubmit=" return validate()">
                    <ul>
                        <li><label for = 'name'>Nome:</label><br>
                            <input type = 'text' id = 'name' required/></li>

                        <li><label for = 'surname'>Cognome:</label><br>
                            <input type = 'text' id = 'surname' required/></li>

                        <li><label for = 'username'>Username:</label><br>
                            <input type = 'text' id = 'username' required/></li>

                        <li><label for = 'email'>Email:</label><br>
                            <input type = 'text' id = 'email' pattern ="^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$"  required/></li>

                        <li><label for = 'password'>Password :(almeno 8 caratteri, deve contenere: una lettera maiuscola, una minuscola, un numero)</label><br>
                            <input type = 'password' id = 'password'   required/></li>

                        <li><label for = 'passwordConfirm'>Conferma password </label><br>
                            <input type = 'password' id = 'passwordConfirm'  required/></li>

                        <li><label for = 'description'>Descrizione:</label><br>
                            <textarea id='description' name='description' rows='4' cols='50'></textarea>
                        </li>

                        <li class='submit'><input type = 'submit' name = 'submit' value = 'Conferma'/></li>

                    </ul>
                </form>
            </div>
        </li>
    </ul>

</div>

<script >

    function validate() {
        return (validateEmail() && validatePassword());
    }

    function validateEmail() {
        var email = document.form.email.value;
        var request = new XMLHttpRequest();
        request.open('GET',	"VerifyMail?email="+ encodeURIComponent(email),false);
        // `false` per le richieste synchronous
        request.send(null);
        if (request.status === 200) {
            if (request.responseText === '<ok/>')
                return true;
            else
                alert('email gia presente in archivio');
        } else
            alert("problemi di connessione");
        return false;
    }

    function validatePassword() {
        var password = document.form.password.value;
        if (password.length >= 8
            && password.toUpperCase() != password
            && password.toLowerCase() != password
            && /[0-9]/.test(password)) {
            if (password == document.form.passwordConfirm.value) {
                return (true);
            } else {
                alert("Password non confermata");
                return false;
            }
        } else {
            alert("Password non valida");
            return (false);
        }

    }
</script>

</body>
</html>
