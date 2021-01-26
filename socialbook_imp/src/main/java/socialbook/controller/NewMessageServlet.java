package socialbook.controller;

import socialbook.Utility.AdminRole;
import socialbook.Utility.StatusEnumeration;
import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static socialbook.Utility.StatusEnumeration.CLOSED;
import static socialbook.Utility.StatusEnumeration.OPEN;

@WebServlet("/new-message-servlet")
public class NewMessageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        MessageDAO messageDAO = new MessageDAO();
        TicketDAO ticketDAO = new TicketDAO();

        boolean sender = true;
        StatusEnumeration status = OPEN;

        Ticket t = (Ticket) session.getAttribute("ticket");
        List<Ticket> tickets = Collections.emptyList();

        Message m = new Message();
        m.setMessage_body(request.getParameter("message"));
        m.setId_ticket(t.getId_ticket());

        Admin customerM = (Admin) session.getAttribute("customerManager");
        Admin systemM = (Admin) session.getAttribute("systemManager");
        Customer customer = (Customer) session.getAttribute("personalCustomer");

        if(customerM != null || systemM != null) {
            status = CLOSED;
            Date close_date = new Date(new java.util.Date().getTime());
            t.setClose_date(close_date);

            if(customerM != null) {
                tickets = ticketDAO.doRetrieveByAdmin(customerM.getA_usr());
                request.setAttribute("ticketsR", ticketDAO.doRetrieveByRole(AdminRole.CUSTOMER_MANAGER));
            } else {
                tickets = ticketDAO.doRetrieveByAdmin(systemM.getA_usr());
                request.setAttribute("ticketsR", ticketDAO.doRetrieveByRole(AdminRole.SYSTEM_MANAGER));
            }

        }else if(customer != null){
            sender = false;
            status = OPEN;
            tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());
        }

        m.setSender(sender);
        t.setStatus(status);
        messageDAO.doSave(m);
        ticketDAO.doUpdate(t);

        session.removeAttribute("ticket");
        request.setAttribute("tickets", tickets);

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        rd.forward(request, response);
    }

}
