package socialbook.controller;

import socialbook.Utility.StatusEnumeration;
import socialbook.model.Message;
import socialbook.model.MessageDAO;
import socialbook.model.Ticket;
import socialbook.model.TicketDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Message m = new Message();
        m.setMessage_body(request.getParameter("message"));
        m.setId_ticket(t.getId_ticket());

        if(request.getSession().getAttribute("admin") != null){
            sender = true;
            status = CLOSED;

        }else if(request.getSession().getAttribute("personalCustomer") != null ){
            sender = false;
            status = OPEN;
        }

        m.setSender(sender);
        messageDAO.doSave(m);
        ticketDAO.doUpdate(t);
        request.removeAttribute("ticket");
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView");
        rd.forward(request, response);
    }

}
