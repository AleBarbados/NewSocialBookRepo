package socialbook.controller;

import socialbook.Utility.AdminRole;
import socialbook.Utility.StatusEnumeration;
import socialbook.model.Customer;
import socialbook.model.Ticket;
import socialbook.model.TicketDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/new-ticket-servlet")
public class NewTicketServlet extends HttpServlet {
    private final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        Ticket ticket = new Ticket();
        RequestDispatcher dispatcher;

        if(request.getParameter("customerManager") == null && request.getParameter("systemManager") == null){
            ticket.setStatus(StatusEnumeration.OPEN);
            ticket.setIssue(request.getParameter("issue"));

            if("users".equals(request.getParameter("destination"))){
                ticket.setDestination(AdminRole.CUSTOMER_MANAGER);
            }else {
                ticket.setDestination(AdminRole.CATALOGUE_MANAGER);
            }

            if(customer != null){
                ticket.setId_customer(customer.getId_customer());

                List<Ticket> tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());
                request.setAttribute("tickets", tickets);
                ticketDAO.doSave(ticket);

                dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
                dispatcher.forward(request, response);
            } else {
                ticket.setId_customer(4);       //4 è l'id di un customer fittizio
                ticketDAO.doSave(ticket);

                dispatcher = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
                dispatcher.forward(request, response);
            }
        }else{
            //errore, admin non può creare ticket
        }
    }
}


