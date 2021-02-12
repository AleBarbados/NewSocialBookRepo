package socialbook.controller.GestioneAcquisto;

import socialbook.utility.CartManagement;
import socialbook.model.GestioneDatabase.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/payment-servlet")
public class PaymentServlet extends HttpServlet {
    private final InfoPaymentDAO infoPaymentDAO = new InfoPaymentDAO();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String card_name = request.getParameter("cardname");
        String card_surname = request.getParameter("cardsurname");
        String card_number = request.getParameter("cardnumber");
        String exp_month = request.getParameter("expmonth");
        String exp_year = request.getParameter("expyear");
        String cvv = request.getParameter("cvv");

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null)
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");

        if("Procedi al pagamento".equals(request.getParameter("pay"))){
            InfoPayment infoPayment = new InfoPayment();

            if(!card_name.matches("([a-zA-Z]{1,15})"))
                throw new socialbook.utility.ServletException("Formato nome non valido");

            if(!card_surname.matches("([a-zA-Z]{1,15})"))
                throw new socialbook.utility.ServletException("Formato cognome non valido");

            if(!card_number.matches("([0-9]{16})"))
                throw new socialbook.utility.ServletException("Formato carta sbagliato");

            if(!exp_month.matches("([0-9]{2})"))
                throw new socialbook.utility.ServletException("Formato exp-month sbagliato");

            if(!exp_year.matches("([0-9]{4})"))
                throw new socialbook.utility.ServletException("Formato exp-year sbagliato");

            if(!cvv.matches("([0-9]{3})"))
                throw new socialbook.utility.ServletException("Formato cvv sbagliato");



            infoPayment.setId_customer(customer.getId_customer());
            infoPayment.setPayment_name(card_name);
            infoPayment.setPayment_surname(card_surname);
            infoPayment.setCard_number(card_number);
            infoPayment.setExp_month(exp_month);
            infoPayment.setExp_year(exp_year);
            infoPayment.setCvv(Integer.parseInt(cvv));
            String addr = request.getParameter("address");

            infoPaymentDAO.doSave(infoPayment);

            new CartManagement().doManageCart(customer.getId_customer(), addr);
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null)
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");


        if("elimina".equals(request.getParameter("id"))){
            request.getSession().removeAttribute("info");
            infoPaymentDAO.doDeleteById(customer.getId_customer());

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
            dispatcher.forward(request, response);
        }else {
            if("procedi".equals(request.getParameter("id"))){
                String addr = request.getParameter("address");

                new CartManagement().doManageCart(customer.getId_customer(), addr);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
                rd.forward(request, response);
                return;
            }

            if("pagamento".equals(request.getParameter("id"))){
                Optional<InfoPayment> info = infoPaymentDAO.doRetrieveByCustomer(customer.getId_customer());
                if(info != null && info.isPresent()){
                    request.setAttribute("info", info.get());
                }
                RequestDispatcher  dispatcher = request.getRequestDispatcher("WEB-INF/jsp/payment_info.jsp");
                dispatcher.forward(request, response);
            }

        }
    }
}
