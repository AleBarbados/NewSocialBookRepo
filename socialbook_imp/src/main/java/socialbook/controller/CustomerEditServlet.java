package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerEdit")
public class CustomerEditServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        customer.setC_pwd(Utility.encryptionSHA1(request.getParameter("password")));
        customer.setDescription(request.getParameter("descrizione"));
        String fileName = Utility.aggiuntaFoto(request);
        customer.setImage(fileName);

        customerDAO.doUpdate(customer);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
