package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.*;
import socialbook.utility.Utility;

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
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AdminDAO adminDAO = new AdminDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("pwd");
        String pwd = Utility.encryptionSHA1(password);

        Customer customer;

        if (customerDAO.validate(username, pwd)) {          //validazione utente
            customer = customerDAO.doRetrieveByUsername(username);
            session.setAttribute("personalCustomer", customer);

            Cart cart = cartDAO.doRetrieveByCustomer(customer.getId_customer());
            System.out.println("cart id: " + cart.getId_cart());
            session.setAttribute("cart", cart);

            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            rd.forward(req, resp);
        } else {
            // controllo amministratore
            Admin admin = adminDAO.doRetrieveByUsrEPwd(username, pwd);

            if (admin != null) {
                switch (admin.getA_role()) {
                    case CUSTOMER_MANAGER:
                        session.setAttribute("customerManager", admin);
                        break;
                    case CATALOGUE_MANAGER:
                        session.setAttribute("catalogueManager", admin);
                        break;
                    case SYSTEM_MANAGER:
                        session.setAttribute("systemManager", admin);
                        break;
                }

                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/customerManagerView.jsp");
                rd.forward(req, resp);
            }
        }

        throw new socialbook.utility.ServletException("Le credenziali inserite non sono valide!!");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}