package socialbook.controller;

import socialbook.Utility.Utility;
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


@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession sessione = req.getSession();
        String ADDRESS = "/WEB-INF/jsp/index.jsp";
        String usr = req.getParameter("username");
        String password = req.getParameter("pwd");
        Customer customer;
        CustomerDAO customerDAO = new CustomerDAO();
        AdminDAO adminDAO = new AdminDAO();
        String pwd = Utility.encryptionSHA1(password);
System.out.println(" u :"+usr+" p:"+pwd);

        if (customerDAO.validate(usr, pwd)) {          //validazione utente
            System.out.println("entra in if");
            customer = customerDAO.doRetrieveByUsername(usr);
            System.out.println("customer " + customer.getC_name());
            sessione.setAttribute("personalCustomer", customer);
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            rd.forward(req, resp);
        }
       else{
            // controllo amministratore
            Admin admin = adminDAO.doRetrieveByUsrEPwd(usr, pwd);

            if ( admin != null) {
                sessione.setAttribute("admin", admin);
                System.out.println("salvato admin in sessione");
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/customerManagerView.jsp");
                rd.forward(req, resp);

            }else{
                ADDRESS = "errorLogin.jsp";
            }

        }
        RequestDispatcher rd = req.getRequestDispatcher(ADDRESS);
        rd.forward(req, resp);
    }
}