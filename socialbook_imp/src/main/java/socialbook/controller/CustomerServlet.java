package socialbook.controller;

import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerServlet")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("costumerView") != null || request.getParameter("redirect") != null){
            Customer c = customerDAO.doRetriveById(Integer.parseInt(request.getParameter("customer")));
            request.setAttribute("customer", c);
        }
        if(request.getParameter("personalView")!=null){
            request.setAttribute("view", true);
        }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp");
            requestDispatcher.forward(request, response);
    }
}
