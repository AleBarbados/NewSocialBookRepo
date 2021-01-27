package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.*;

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

        req.getParameterMap().entrySet().stream().forEach(e->System.out.println(e.getKey()+ " "+e.getValue().toString()));


        String addr = "WEB-INF/jsp/index.jsp";
        Customer customer = new Customer(name, surname, email,  Utility.encryptionSHA1(password), username, description);
        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.doSave(customer);

        Cart cart = new Cart(customer.getId_customer(), 0);
        CartDAO cartDAO = new CartDAO();
        cartDAO.doSave(cart, customer.getId_customer());

        BookList bookList = new BookList("preferiti", true, "");
        BookListDAO bookListDAO = new BookListDAO();
        bookListDAO.doSave(bookList, customer.getId_customer());

        RequestDispatcher dispatcher =
                req.getRequestDispatcher(addr);
        dispatcher.forward(req, resp);
    }
}
