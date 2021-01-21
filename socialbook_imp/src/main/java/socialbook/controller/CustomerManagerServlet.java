package socialbook.controller;

import socialbook.Utility.Utility;
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
        String id = request.getParameter("id");

        if(id == null) {      //click sul bottone 'mostra tutti gli utenti'
            request.setAttribute("customers", customerDAO.doRetrieveAll());

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/utenti.jsp");
            dispatcher.forward(request, response);
        } else {
            customerDAO.doDeleteById(Integer.parseInt(id));

            String dest = request.getHeader("referer");
            String conf = "/customerManagerServlet";
            Utility.redirect(response, dest, conf);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
