package socialbook.controller;

import com.mysql.cj.Session;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{

        System.out.println("entro in servlet all ticket");
        TicketDAO ticketDAO = new TicketDAO();
        ArrayList<Ticket> tickets = new ArrayList<>();
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        if(admin != null) {

            ArrayList<Ticket> ticketsR = ticketDAO.doRetrieveByRole(admin.getA_role());
            request.setAttribute("ticketsR", ticketsR);
            System.out.println("tickets trovati: "+ ticketsR.size());
            tickets = ticketDAO.doRetrieveByAdmin(admin.getA_usr());

        }else if(customer != null){
            System.out.println("entro in all tickets servlet sezione customer id customer =" + customer.getId_customer());
            tickets = ticketDAO.doRetrieveByCustomer(customer.getId_customer());
            if(tickets != null){System.out.println("ci sono "+ tickets.size() + "tickets");}
        }
        System.out.println("post secondo if");
        request.setAttribute("tickets", tickets);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        requestDispatcher.forward(request, response);
    }
}
