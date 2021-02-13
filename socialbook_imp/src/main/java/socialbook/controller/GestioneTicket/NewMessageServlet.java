package socialbook.controller.GestioneTicket;

import socialbook.model.GestioneDatabase.*;
import socialbook.utility.AdminRole;
import socialbook.utility.StatusEnumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

import static socialbook.utility.StatusEnumeration.CLOSED;
import static socialbook.utility.StatusEnumeration.OPEN;

@WebServlet("/new-message-servlet")
public class NewMessageServlet extends HttpServlet {
    private final MessageDAO messageDAO = new MessageDAO();
    private final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        boolean sender = true;
        StatusEnumeration status = OPEN;

        Ticket ticket = (Ticket) session.getAttribute("ticket");
        List<Ticket> tickets = Collections.emptyList();

        Message message = new Message();
        message.setMessage_body(request.getParameter("message"));
        message.setId_ticket(ticket.getId_ticket());

        Admin customerManager = (Admin) session.getAttribute("customerManager");
        Admin systemManager = (Admin) session.getAttribute("systemManager");
        Customer customer = (Customer) session.getAttribute("personalCustomer");

        if(customerManager != null || systemManager != null) {
            status = CLOSED;
            Date close_date = new Date(new java.util.Date().getTime());
            ticket.setClose_date(close_date);

            if(customerManager != null) {
                tickets = ticketDAO.doRetrieveByAdmin(customerManager.getA_usr());
                request.setAttribute("ticketsR", ticketDAO.doRetrieveByRole(AdminRole.CUSTOMER_MANAGER));
            } else {
                tickets = ticketDAO.doRetrieveByAdmin(systemManager.getA_usr());
                request.setAttribute("ticketsR", ticketDAO.doRetrieveByRole(AdminRole.SYSTEM_MANAGER));
            }

        }else if(customer != null){
            sender = false;
            status = OPEN;
            tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());
        }

        message.setSender(sender);
        ticket.setStatus(status);
        messageDAO.doSave(message);
        ticketDAO.doUpdate(ticket);

        session.removeAttribute("ticket");
        request.setAttribute("tickets", tickets);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
