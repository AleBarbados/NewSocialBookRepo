package socialbook.controller;

import socialbook.model.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
@WebServlet("/customerManagerServlet")
public class CustomerManager extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int c_id = Integer.parseInt(request.getParameter("id"));
        customerDAO.doDeleteById(c_id);

        String dest = request.getHeader("referer");
        if (dest == null || dest.contains("/customerManagerServlet") || dest.trim().isEmpty()) {
            dest = ".";     //la destinazione sarà la pagina corrente
        }
        response.sendRedirect(dest);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
*/