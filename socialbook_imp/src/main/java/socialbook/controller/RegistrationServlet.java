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
@WebServlet("/registration-servlet")
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String description = req.getParameter("description");
        String addr = "/index.jsp";

        Customer customer = new Customer(name, surname, email, password, username, description);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.doSave(customer);

        RequestDispatcher dispatcher =
                req.getRequestDispatcher(addr);
        dispatcher.forward(req, resp);
    }

}
