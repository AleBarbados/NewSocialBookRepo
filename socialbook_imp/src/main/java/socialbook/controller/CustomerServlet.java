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
        if(request.getParameter("costumer") != null){
            Customer c = customerDAO.doRetriveById(Integer.parseInt(request.getParameter("costumer")));
            request.setAttribute("costumer", c);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/costumerView.jsp");
            requestDispatcher.forward(request, response);
        }
        else if(request.getParameter("costumerView") != null){
            Customer utente = (Customer) request.getSession().getAttribute("utente");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/results/costumerView.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
