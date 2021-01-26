package socialbook.controller;

import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerManagerServlet")
public class CustomerManagerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("customerManager") == null)
            throw new socialbook.controller.ServletException("Solo il customer manager può effettuare questa operazione!!");

        String id = request.getParameter("id");

        if(id == null) {      //click sul bottone 'mostra tutti gli utenti'
            request.setAttribute("customers", customerDAO.doRetrieveAll());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/utenti.jsp");
            dispatcher.forward(request, response);
        } else {
            customerDAO.doDeleteById(Integer.parseInt(id));

            response.sendRedirect(request.getHeader("referer"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
