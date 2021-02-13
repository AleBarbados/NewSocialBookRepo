package socialbook.controller.GestioneAcquisto;

import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.Order;
import socialbook.model.GestioneDatabase.OrderDAO;
import socialbook.model.GestioneDatabase.OrderDetailDAO;
import socialbook.model.GestioneDatabase.OrderDetail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/all-order-servlet")
public class AllOrdersViewServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = (Customer) req.getSession().getAttribute("personalCustomer");
        if(customer == null){
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");
        }else{
            List<Order> orders = orderDAO.doRetrieveByCustomer(customer.getId_customer());
            req.setAttribute("orders", orders);

            List<OrderDetail> orderDetail = orderDetailDAO.doRetrieveByCustomer(customer.getId_customer());
            req.setAttribute("products", orderDetail);

            RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/allOrdersView.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}