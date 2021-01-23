package socialbook.controller;

import socialbook.Utility.StatusEnumeration;
import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        MessageDAO messageDAO = new MessageDAO();
        TicketDAO ticketDAO = new TicketDAO();
        boolean sender = true;
        StatusEnumeration status = OPEN;
        Ticket t = (Ticket) request.getSession().getAttribute("ticket");
        List<Ticket> tickets = Collections.emptyList();
        Message m = new Message();
        m.setMessage_body(request.getParameter("message"));
        m.setId_ticket(t.getId_ticket());

        if(request.getSession().getAttribute("admin") != null){
            Admin admin = (Admin) request.getSession().getAttribute("admin");
            sender = true;
            status = CLOSED;
            Date close_date = new Date(new java.util.Date().getTime());
            t.setClose_date(close_date);
            tickets = ticketDAO.doRetrieveByAdmin(admin.getA_usr());
            request.setAttribute("ticketsR", ticketDAO.doRetrieveByRole(admin.getA_role()));

        }else if(request.getSession().getAttribute("personalCustomer") != null ){
            sender = false;
            status = OPEN;
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());
        }

        m.setSender(sender);
        t.setStatus(status);
        messageDAO.doSave(m);
        ticketDAO.doUpdate(t);
        request.getSession().removeAttribute("ticket");
        request.setAttribute("tickets", tickets);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        rd.forward(request, response);
    }

}
