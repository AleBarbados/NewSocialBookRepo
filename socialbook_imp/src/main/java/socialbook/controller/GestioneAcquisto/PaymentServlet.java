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
    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null)
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");

        if("Procedi al pagamento".equals(request.getParameter("pay"))){
            InfoPayment infoPayment = new InfoPayment();

            infoPayment.setId_customer(customer.getId_customer());
            infoPayment.setPayment_name(request.getParameter("cardname"));
            infoPayment.setPayment_surname(request.getParameter("cardsurname"));
            infoPayment.setCard_number(request.getParameter("cardnumber"));
            infoPayment.setExp_month(request.getParameter("expmonth"));
            infoPayment.setExp_year(request.getParameter("expyear"));
            infoPayment.setCvv(Integer.parseInt(request.getParameter("cvv")));

            infoPaymentDAO.doSave(infoPayment);

            new CartManagement().doManageCart(customer.getId_customer(), request);

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
            rd.forward(request, response);
        }else{
            System.out.println("req id"+ request.getParameter("pay"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");


        if("elimina".equals(request.getParameter("id"))){
            request.getSession().removeAttribute("info");
            infoPaymentDAO.doDeleteById(customer.getId_customer());

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
            dispatcher.forward(request, response);
        }else {
            if("procedi".equals(request.getParameter("id"))){
            new CartManagement().doManageCart(customer.getId_customer(), request);
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
