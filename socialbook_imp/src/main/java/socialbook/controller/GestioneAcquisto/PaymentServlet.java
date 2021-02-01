package socialbook.controller.GestioneAcquisto;

import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payment-servlet")
public class PaymentServlet extends HttpServlet {
    private final InfoPaymentDAO infoPaymentDAO = new InfoPaymentDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null)
            throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");

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

            Order order = orderDAO.doRetrieveByCart(customer.getId_customer());
            order.setInvoice_addr(request.getParameter("address"));
            orderDAO.doUpdate(order);

            Cart cart = new Cart(customer.getId_customer(), 0);
            cartDAO.doSave(cart, customer.getId_customer());

            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
            rd.forward(request, response);
        }else{
            System.out.println("req id"+ request.getParameter("pay"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;

        if("elimina".equals(request.getParameter("id"))){
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            infoPaymentDAO.doDeleteById(customer.getId_customer());

            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp");
            dispatcher.forward(request, response);
        }else {
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/payment_info.jsp");
            dispatcher.forward(request, response);
        }
    }
}
