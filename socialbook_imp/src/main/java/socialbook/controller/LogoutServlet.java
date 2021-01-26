package socialbook.controller;

import socialbook.model.Admin;
import socialbook.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout-servlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Admin customerM = (Admin) session.getAttribute("customerManager");
        Admin catalogueM = (Admin) session.getAttribute("catalogueManager");
        Admin systemM = (Admin) session.getAttribute("systemManager");
        Customer customer = (Customer) session.getAttribute("personalCustomer");

        if(customerM == null && catalogueM == null && systemM == null && customer == null)
            throw new socialbook.controller.ServletException("Bisgona aver effettuato l'accesso!!");

        if(customerM != null)
            session.removeAttribute("customerManager");

        if(catalogueM != null)
            session.removeAttribute("catalogueManager");

        if(systemM != null)
            session.removeAttribute("systemManager");

        if(customer != null)
            session.removeAttribute("personalCustomer");

        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        rd.forward(req,resp);
    }
}