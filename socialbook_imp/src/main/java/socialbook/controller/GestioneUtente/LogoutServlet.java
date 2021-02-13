package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.Admin;
import socialbook.model.GestioneDatabase.Customer;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Admin customerManager = (Admin) session.getAttribute("customerManager");
        Admin catalogueManager = (Admin) session.getAttribute("catalogueManager");
        Admin systemManager = (Admin) session.getAttribute("systemManager");
        Customer customer = (Customer) session.getAttribute("personalCustomer");

        if(customerManager == null && catalogueManager == null && systemManager == null && customer == null)
            throw new socialbook.utility.ServletException("Bisgona aver effettuato l'accesso!!");

        if(customerManager != null)
            session.removeAttribute("customerManager");

        if(catalogueManager != null)
            session.removeAttribute("catalogueManager");

        if(systemManager != null)
            session.removeAttribute("systemManager");

        if(customer != null)
            session.removeAttribute("personalCustomer");

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}