package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editServlet")
public class EditServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("EditProfile")!=null) {
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            customer.setC_pwd(request.getParameter("password"));
            customer.setDescription(request.getParameter("descrizione"));
            String fileName = Utility.aggiuntaFoto(request);
            customerDAO.doUpdate(customer);
        }
        String dest = request.getHeader("referer");     //prendiamo dall'header della richiesta l'url corrente
        if(dest == null || dest.contains("/CustomerServlet") || dest.trim().isEmpty()){
            dest = ".";     //la destinazione sarà la pagina corrente
        }
        response.sendRedirect(dest);
    }
}
