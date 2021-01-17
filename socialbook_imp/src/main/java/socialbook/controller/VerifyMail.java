package socialbook.controller;

import socialbook.model.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/VerifyMail")
public class VerifyMail extends HttpServlet {

    private final CustomerDAO customerDAO = new CustomerDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        response.setContentType("text/xml");
        if (email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")
                && customerDAO.doRetriveByEmail(email) == null) {
            System.out.println("entra nel doget yes");
            response.getWriter().append("<ok/>");

        } else {
            System.out.println("entra nel doget no");

            response.getWriter().append("<no/>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
