package socialbook.controller;

import socialbook.Utility.AdminRole;
import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/all-ticket-servlet")
public class AllTicketsServlet extends HttpServlet {
    private final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        Admin customerManager = (Admin) request.getSession().getAttribute("customerManager");
        Admin systemManager = (Admin) request.getSession().getAttribute("systemManager");

        if(customer == null && customerManager == null && systemManager == null)
            throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");

        ArrayList<Ticket> tickets;
        ArrayList<Ticket> ticketsByAdminRole;

        if(customerManager != null) {
            ticketsByAdminRole = ticketDAO.doRetrieveByRole(AdminRole.CUSTOMER_MANAGER);
            request.setAttribute("ticketsR", ticketsByAdminRole);

            tickets = ticketDAO.doRetrieveByAdmin(customerManager.getA_usr());
        } else if(systemManager != null) {
            ticketsByAdminRole = ticketDAO.doRetrieveByRole(AdminRole.SYSTEM_MANAGER);
            request.setAttribute("ticketsR", ticketsByAdminRole);

            tickets = ticketDAO.doRetrieveByAdmin(systemManager.getA_usr());
        }else
            tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());

        request.setAttribute("tickets", tickets);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        requestDispatcher.forward(request, response);
    }
}
