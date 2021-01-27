package socialbook.controller;

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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if("Procedi al pagamento".equals(req.getParameter("pay"))){
            Customer c = (Customer) req.getSession().getAttribute("personalCustomer");
            InfoPayment infoPayment = new InfoPayment();
            infoPayment.setId_customer(c.getId_customer());
            infoPayment.setPayment_name(req.getParameter("cardname"));
            infoPayment.setPayment_surname(req.getParameter("cardsurname"));
            infoPayment.setCard_number(req.getParameter("cardnumber"));
            infoPayment.setExp_month(req.getParameter("expmonth"));
            infoPayment.setExp_year(req.getParameter("expyear"));
            infoPayment.setCvv(Integer.parseInt(req.getParameter("cvv")));
            new InfoPaymentDAO().doSave(infoPayment);
            Order o = new OrderDAO().doRetrieveByCart(c.getId_customer());
            o.setInvoice_addr(req.getParameter("address"));
            new OrderDAO().doUpdate(o);

            Cart cart = new Cart(c.getId_customer(), 0);
            CartDAO cartDAO = new CartDAO();
            cartDAO.doSave(cart, c.getId_customer());


            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/jsp/index.jsp");
            rd.forward(req, resp);
        }else{
            System.out.println("req id"+ req.getParameter("pay"));
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            if("elimina".equals(req.getParameter("id"))){
                Customer c = (Customer) req.getSession().getAttribute("personalCustomer");
                new InfoPaymentDAO().doDeleteById(c.getId_customer());
                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/jsp/customerView.jsp");
                rd.forward(req, resp);
            }else {

                RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/jsp/payment_info.jsp");
                rd.forward(req, resp);
            }
    }
}
