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
                    <ul id = "first-part">
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
                        <li><button class = "next" onclick="nextForm()">Next </button></li>
                    </ul>
                    <ul id = "second-part" hidden>
                        <li>
                            <label for="age">Quanti anni hai?</label>
                            <select name="age" id="age">
                                <option value="age-14-18">14-18</option>
                                <option value="age-19-25">19-25</option>
                                <option value="age-26-30">26-30</option>
                                <option value="age-31-40">31-40</option>
                                <option value="age-41-50">41-50</option>
                                <option value="age-50-plus">50+</option>
                            </select>  </li>
                        <li>
                            <label for="underage_sons"> Hai figli minorenni?</label><br>
                            <select name="underage_sons" id="underage_sons">
                                <option value="yes">si</option>
                                <option value="no">no</option>
                            </select>
                        </li>
                        <li>
                            <label for="work">Che ruolo rivesti? </label><br>
                            <select name="work" id="work">
                                <option value="student">studente</option>
                                <option value="unemployed">disoccupato</option>
                                <option value="employee">impiegato</option>
                                <option value="worker">operaio</option>
                                <option value="freelance">libero professionista</option>
                            </select>  </li>
                        <li><label>Nel tempo libero ti piace:</label><br>
                            <label for="read">leggere </label>
                            <input type="checkbox" id="read" name="read"><br>
                            <label for="play-instrument">suonare</label>
                            <input type="checkbox" id="play-instrument" name="play-instruments"><br>
                            <label for="sing">cantare</label>
                            <input type="checkbox" id="sing" name="sing"><br>
                            <label for="dance">ballare</label>
                            <input type="checkbox" id="dance" name="dance"><br>
                            <label for="videogame">giocare ai videogiochi</label>
                            <input type="checkbox" id="videogame" name="videogame"><br>
                            <label for="travels">viaggiare</label>
                            <input type="checkbox" id="travels" name="travels"><br>
                            <label for="cooking"> cucinare</label>
                            <input type="checkbox" id="cooking" name="cooking"><br>
                            <label for="sport"> fare sport</label>
                            <input type="checkbox" id="sport" name="sport"><br>
                            <label for="volunteering ">fare volontariato</label>
                            <input type="checkbox" id="volunteering " name="volunteering "><br>
                            <label for="art"> dedicarti all'arte, in tutte le sue forme</label>
                            <input type="checkbox" id="art" name="art"><br>
                            <label for="beauty">dedicarti alla cura del corpo ed al beauty</label>
                            <input type="checkbox" id="beauty" name="beauty"><br>
                            <label for="cartoon">guardare cartoni animati/anime </label>
                            <input type="checkbox" id="cartoon" name="cartoon"><br>
                            <label for="walk">fare delle passeggiate</label>
                            <input type="checkbox" id="walk" name="walk"><br>
                            <label for="tv-series">guardare serie tv</label>
                            <input type="checkbox" id="tv-series" name="tv-series">
                        </li>
                        <li><label>Generi che ti piacciono:</label><br>
                            <label for="crime_novel">Giallo</label>
                            <input type="checkbox" id="crime_novel" name="crime_novel"><br>
                            <label for="soap_drama">Drammatico</label>
                            <input type="checkbox" id="soap_drama" name="soap_drama"><br>
                            <label for="romance_novel">Romantico</label>
                            <input type="checkbox" id="romance_novel" name="romance_novel"><br>
                            <label for="historical_novel">Storico</label>
                            <input type="checkbox" id="historical_novel" name="historical_novel"><br>
                            <label for="comics">Fumetti</label>
                            <input type="checkbox" id="comics" name="comics"><br>
                            <label for="sci-fi">Fantascienza</label>
                            <input type="checkbox" id="sci-fi" name="sci-fi"><br>
                            <label for="fantasy">Fantasy</label>
                            <input type="checkbox" id="fantasy" name="fantasy"><br>
                            <label for="adventure"> Avventura</label>
                            <input type="checkbox" id="adventure" name="adventure"><br>
                            <label for="buildunsgroman">Romanzo di formazione</label>
                            <input type="checkbox" id="buildunsgroman" name="buildunsgroman"><br>
                            <label for="horror">Horror</label>
                            <input type="checkbox" id="horror" name="horror"><br>
                            <label for="poem">Poesia</label>
                            <input type="checkbox" id="poem" name="poem"><br>
                            <label for="nonfiction">Saggi </label>
                            <input type="checkbox" id="nonfiction" name="nonfiction"><br>
                            <label for="psychological">Psicologico</label>
                            <input type="checkbox" id="psychological" name="psychological"><br>
                            <label for="motivational">Motivazionale</label>
                            <input type="checkbox" id="motivational" name="motivational"><br>
                            <label for="timely_novel">Attualità</label>
                            <input type="checkbox" id="timely_novel" name="timely_novel"><br>
                            <label for="satirist">Satira</label>
                            <input type="checkbox" id="satirist" name="satirist"><br>
                            <label for="science">Scienza</label>
                            <input type="checkbox" id="science" name="science"><br>
                            <label for="classic">Classici</label>
                            <input type="checkbox" id="classic" name="classic">
                        </li>
                        <li>
                            <label for="num_pages">Quante pagine preferisci leggere?</label><br>
                            <select name="num_pages" id="num_pages">
                                <option value="pages_400_plus">più di 400 pagine</option>
                                <option value="pages_400_less">meno di 400 pagine</option>
                            </select>
                        </li>
                        <li><label>Scegli un libro in base a quali parametri?</label><br>
                            <label for="choice_author">autore</label>
                            <input type="checkbox" id="choice_author" name="choice_author"><br>
                            <label for="choice_publishing_house">Casa editrice</label>
                            <input type="checkbox" id="choice_publishing_house" name="choice_publishing_house"><br>
                            <label for="romance_novel">Premi</label>
                            <input type="checkbox" id="choice_prize" name="choice_prize"><br>
                            <label for="choice_genre">Genere</label>
                            <input type="checkbox" id="choice_genre" name="choice_genre"><br>
                            <label for="choice_review">Recensioni</label>
                            <input type="checkbox" id="choice_review" name="choice_review"><br>
                            <label for="choice_school">Suggerimento scolastico</label>
                            <input type="checkbox" id="choice_school" name="choice_school"><br>
                            <label for="choice_plot">Trama</label>
                            <input type="checkbox" id="choice_plot" name="choice_plot"><br>
                            <label for="choice_cover">Copertina</label>
                            <input type="checkbox" id="choice_cover" name="choice_cover"><br>
                            <label for="choice_title">Titolo</label>
                            <input type="checkbox" id="choice_title" name="choice_title"><br>
                            <label for="choice_first_impact">Sfogliandolo</label>
                            <input type="checkbox" id="choice_first_impact" name="choice_first_impact"><br>
                            <label for="choice_advice">Consiglio di terzi</label>
                            <input type="checkbox" id="choice_advice" name="choice_advice"><br>

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

        function nextForm() {
        document.getElementById("first-part").style.display = "none";
        document.getElementById("second-part").style.display = "block";
    }



</script>

</body>
</html>

