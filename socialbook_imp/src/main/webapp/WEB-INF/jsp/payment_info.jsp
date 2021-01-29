<%--
  Created by IntelliJ IDEA.
  User: alessia
  Date: 27/01/21
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info Pagamento</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"> <jsp:param name="header.jsp" value="Home"/></jsp:include>
</header>
        <div id="checkout">

            <div class="dati_ordine">
                <h2>Procedi all'ordine</h2>
                <h4>Inserisci i dati richiesti:</h4>

                <form action="payment-servlet" name="ordine" method="post">

                        <h5>Dettagli pagamento:</h5>
                    <div class="row">
                        <div class="col-md-3">
                            <h6>
                                <i class="fa fa-address-card-o"></i> Indirizzo fatturazione:
                            </h6>
                        </div>
                        <div class="col-md-3">
                            <div class="row">
                                <input type="text" class="adr" name="addresss"
                                       placeholder="Via Garibaldi, 123" class="input-sm">
                            </div>
                            <div class="row">
                                <small class="controllo_indirizzo"></small>
                            </div>
                        </div>
                    </div>
                        <div class="row">
                            <div class="col-md-3">
                                <h6>
                                    <i class="fa fa-user-circle"></i> Intestatario carta
                                </h6>
                            </div>
                            <div class="col-md-3">
                                    <input type="text" id="cname" name="cardname" placeholder="Mario" required>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <input type="text" id="csurname" name="cardsurname" placeholder="Rossi" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <h6>
                                    <i class="fa fa-credit-card-alt"></i> Numero carta:
                                </h6>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <input type="text" id="ccnum" name="cardnumber"
                                           placeholder="1111222233334444"
                                           onkeyup="validazioneNumCarta()" class="input-sm">
                                </div>
                                <div class="row">
                                    <small id="Messaggio_controllo2"></small>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <h6>
                                    <i class="fa fa-calendar"></i> Mese di scadenza:
                                </h6>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <input type="text" id="expmonth" name="expmonth"
                                           placeholder="Es: 04" onkeyup="validazionExpmonth()"
                                           class="input-sm">
                                </div>
                                <div class="row">
                                    <small id="Messaggio_controllo3"></small>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <h6>
                                    <i class="fa fa-calendar-plus-o"></i> Anno di scadenza:
                                </h6>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <input type="text" id="expyear" name="expyear" placeholder="Es: 2020" class="input-sm">
                                </div>
                                <div class="row">
                                    <small id="Messaggio_controllo4"></small>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <h6>
                                    <i class="fa fa-unlock-alt"></i> CVV:
                                </h6>
                            </div>
                            <div class="col-md-3">
                                <div class="row">
                                    <input type="text" id="cvv" name="cvv" placeholder="es: 352" class="input-sm">
                                </div>
                                <div class="row">
                                    <small id="Messaggio_controllo5"></small>
                                </div>
                            </div>
                        </div>


                    <input type="submit" id="pay"  name="pay" value="Procedi al pagamento">
                </form>

        <script>

            //validzione checkout
            function validazionExpmonth() {
                var state = $('input[name="expmonth"]').val();
                var exp = /^[0-9]{1,2}$/;

                if (exp.test(state)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('Messaggio_controllo3').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('Messaggio_controllo3').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }
            function validazionExpCVV() {
                var state = $('input[name="cvv"]').val();
                var exp = /^[0-9]{3}$/;

                if (exp.test(state)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('Messaggio_controllo5').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('Messaggio_controllo5').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }
            function validazionExpyear() {
                var state = $('input[name="expyear"]').val();
                var exp = /^[0-9]{4}$/;

                if (exp.test(state)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('Messaggio_controllo4').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('Messaggio_controllo4').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }


            function validazioneIndirizzo() {
                var address = $('input[class = "adr"]').val();
                var exp = /^[a-z0-9_-]{3,15}$/;

                if (exp.test(address)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('controllo_indirizzo').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('controllo_indirizzo').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }

            function validazioneNumCarta() {

                var numCarta = $('input[name="cardnumber"]').val();
                var exp = /^[0-9]{16}$/;

                if (exp.test(numCarta)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('Messaggio_controllo2').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('Messaggio_controllo2').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }

            function validazioneIntCarta() {

                var numCarta = $('input[name="cardname"]').val();
                var exp = /^[a-z0-9_-]{3,15}$/;

                if (exp.test(numCarta)) {
                    cartaOK = true;
                    document.getElementById('paga').disabled = false;
                    document.getElementById('Messaggio_controllo').innerHTML = '';
                } else {
                    document.getElementById('paga').disabled = true;
                    document.getElementById('Messaggio_controllo').innerHTML = 'Assicurati di compilare tutti i campi correttamente.';
                }
            }
        </script>
    </div>
</div>