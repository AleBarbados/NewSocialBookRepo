package socialbook.controller;

import socialbook.model.Admin;
import socialbook.model.AdminDAO;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession sessione = req.getSession();
        String ADDRESS = "home.jsp";
        String usr = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        Customer customer;
        CustomerDAO customerDAO = new CustomerDAO();
        AdminDAO adminDAO = new AdminDAO();

        if (customerDAO.validate(usr, pwd) == true) {          //validazione utente
            System.out.println("entra in if");
            customer = customerDAO.doRetrieveByUsername(usr);
            sessione.setAttribute("personalCustomer", customer);
            System.out.println("entra in controllo utente");
        }
       else{
            // controllo amministratore
            Admin admin = adminDAO.doRetrieveByUsrEPwd(usr, pwd);

            if ( admin != null) {
                sessione.setAttribute("admin", admin);

            }else{
                ADDRESS = "errorLogin.jsp";
            }

        }
        RequestDispatcher rd = req.getRequestDispatcher(ADDRESS);
        rd.forward(req, resp);
    }
}