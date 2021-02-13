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
import java.util.ArrayList;

@WebServlet("/ticket-view-servlet")
public class TicketViewServlet extends HttpServlet {
    private final TicketDAO ticketDAO = new TicketDAO();
    private final MessageDAO messageDAO = new MessageDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Customer customer = (Customer) session.getAttribute("personalCustomer");
        Admin customerManager = (Admin) session.getAttribute("customerManager");
        Admin systemManager = (Admin) session.getAttribute("systemManager");

        String ADDRESS = "";
        RequestDispatcher dispatcher;

        if(request.getParameter("name") == null) {
            if (customer == null && customerManager == null && systemManager == null)
                throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");

            request.getSession().setAttribute("ticket", ticketDAO.doRetrieveById(Integer.parseInt(request.getParameter("id"))));
            ArrayList<Message> messages = messageDAO.doRetrieveByTicket(Integer.parseInt(request.getParameter("id")));

            request.setAttribute("messages", messages);
            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/MessageTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(request.getParameter("name").equals("accept")) {
            if(customerManager == null && systemManager == null)
                throw new socialbook.utility.ServletException("Solo il customer manager e il system managere possono effettuare quest'operazione!!");

            String username = "";
            AdminRole adminRole = null;

            Ticket ticket = ticketDAO.doRetrieveById(Integer.parseInt(request.getParameter("id")));
            ticket.setStatus(StatusEnumeration.WORK_IN_PROGRESS);


            if(customerManager != null){
                username = customerManager.getA_usr();
                adminRole = AdminRole.CUSTOMER_MANAGER;
                ticket.setAdmn_usr(customerManager.getA_usr());
            } else{
                username = systemManager.getA_usr();
                adminRole = AdminRole.SYSTEM_MANAGER;
                ticket.setAdmn_usr(systemManager.getA_usr());
            }
            request.setAttribute("ticket", new TicketDAO().doRetrieveByAdmin(username));
            request.setAttribute("ticketR", new TicketDAO().doRetrieveByRole(adminRole));

            ticketDAO.doUpdate(ticket);

            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
            dispatcher.forward(request, response);

        } else if(request.getParameter("name").equals("delete")) {
            if (customer == null && customerManager == null && systemManager == null)
                throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");

            ticketDAO.doDeleteById(Integer.parseInt(request.getParameter("id")));

            dispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
            dispatcher.forward(request, response);
        }

        if(request.getParameter("id").equals("null") && request.getParameter("name").equals("newTicket")) {
            ADDRESS = "WEB-INF/jsp/newTicket.jsp";
        }

        dispatcher = request.getRequestDispatcher(ADDRESS);
        dispatcher.forward(request, response);
    }

}
