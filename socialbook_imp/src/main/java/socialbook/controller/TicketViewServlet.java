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
import java.util.ArrayList;

@WebServlet("/ticket-view-servlet")
public class TicketViewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    String ADDRESS = "";
    TicketDAO ticketDAO = new TicketDAO();

    MessageDAO messageDAO = new MessageDAO();

    if(request.getParameter("name") == null){
        request.getSession().setAttribute("ticket", ticketDAO.doRetrieveById(Integer.parseInt(request.getParameter("id"))));
        ArrayList<Message> messages = messageDAO.doRetrieveByTicket(Integer.parseInt(request.getParameter("id")));
        if(messages.size() == 0){
            System.out.println("non prende i messaggi");
        }
        request.setAttribute("messages", messages);
        ADDRESS = "WEB-INF/jsp/TicketViewProva.jsp";
    }else{

    if(request.getParameter("name").equals("accept")){
        System.out.println("in servlet ticket view id ticket:" + request.getParameter("id") );
        Ticket ticket = ticketDAO.doRetrieveById(Integer.parseInt(request.getParameter("id")));
        System.out.println("/n");
        System.out.println("ticket issue: " + ticket.getIssue());
        System.out.println("ticket admin:"+ ticket.getAdmn_usr());
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        ticket.setAdmn_usr(admin.getA_usr());
        ticket.setStatus(StatusEnumeration.WORK_IN_PROGRESS);
            ticketDAO.doUpdate(ticket);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        requestDispatcher.forward(request, response);

        } else if(request.getParameter("name").equals("delete")){
        ticketDAO.doDeleteById(Integer.parseInt(request.getParameter("id")));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/AllTicketsView.jsp");
        requestDispatcher.forward(request, response);

        }else if(request.getParameter("id").equals("null") && request.getParameter("name").equals("newTicket")){
        ADDRESS = "WEB-INF/jsp/newTicket.jsp";

        }}
        RequestDispatcher rd = request.getRequestDispatcher(ADDRESS);
        rd.forward(request, response);
    }

}
