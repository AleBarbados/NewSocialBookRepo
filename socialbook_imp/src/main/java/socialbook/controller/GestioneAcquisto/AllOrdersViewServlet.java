package socialbook.controller.GestioneAcquisto;

import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.Order;
import socialbook.model.GestioneDatabase.OrderDAO;
import socialbook.model.GestioneDatabase.OrderDetailDAO;
import socialbook.model.OrderDetail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/all-order-servlet")
public class AllOrdersViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer c = (Customer) req.getSession().getAttribute("personalCustomer");
        if(c == null){
            //throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");
        }else{
            List<Order> orders = new OrderDAO().doRetrieveByCustomer(c.getId_customer());
            req.setAttribute("orders", orders);
            List<OrderDetail> orderDetail = new OrderDetailDAO().doRetrieveByCustomer(c.getId_customer());
            req.setAttribute("products", orderDetail);
            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/allOrdersView.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
