package socialbook.controller;

import socialbook.model.MessageDAO;
import socialbook.model.TicketDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ticket-view")
public class TicketViewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String ADDRESS = "";
    TicketDAO ticketDAO = new TicketDAO();
    MessageDAO messageDAO = new MessageDAO();


        if(request.getParameter("delete") != null){
        ticketDAO.doDeleteById(Integer.parseInt(request.getParameter("id")));

    }else if(request.getParameter("newTicket") != null){
        ADDRESS = "WEB-INF/jsp/newTicket.jsp";

    }else{
        request.setAttribute("ticket", ticketDAO.doRetrieveById(Integer.parseInt(request.getParameter("id"))));
        request.setAttribute("messages", messageDAO.doRetrieveByTicket(Integer.parseInt(request.getParameter("id"))));
        ADDRESS = "WEB-INF/jsp/TicketViewProva";
    }
        RequestDispatcher rd = request.getRequestDispatcher(ADDRESS);
        rd.forward(request, response);
    }

}
