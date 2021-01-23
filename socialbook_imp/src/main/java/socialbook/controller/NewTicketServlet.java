package socialbook.controller;

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

@WebServlet("/new-servlet")
public class NewTicketServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer != null) {
            TicketDAO ticketDAO = new TicketDAO();
            Ticket ticket = new Ticket();
            ticket.setStatus(StatusEnumeration.OPEN);
            ticket.setIssue(request.getParameter("issue"));
            ticket.setId_customer(customer.getId_customer());
            ticketDAO.doSave(ticket);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/allTicketsView.jsp");
            requestDispatcher.forward(request, response);
        }else{
            System.out.println("devo fare una pagina di prova ma ho sonno");
        }
    }

}
