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
import java.util.Optional;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();
    private AdminDAO adminDAO = new AdminDAO();
    private CartDAO cartDAO = new CartDAO();

    public LoginServlet(){}

    public LoginServlet(CustomerDAO customerDAO, AdminDAO adminDAO, CartDAO cartDAO){
        this.customerDAO = customerDAO;
        this.adminDAO = adminDAO;
        this.cartDAO = cartDAO;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("pwd");
        String pwd = Utility.encryptionSHA1(password);

        Customer customer;
        Admin admin = adminDAO.doRetrieveByUsrEPwd(username, pwd);

        if (customerDAO.validate(username, pwd)) {          //validazione utente
            customer = customerDAO.doRetrieveByUsername(username);
            session.setAttribute("personalCustomer", customer);

            Optional<Cart> cart = cartDAO.doRetrieveByCustomer(customer.getId_customer());
            if(!cart.isPresent() || cart == null){
                Cart newCart = new Cart(customer.getId_customer(), 0);
                cartDAO.doSave(newCart, customer.getId_customer());
                session.setAttribute("cart", newCart);

            }else{
                session.setAttribute("cart", cart.get());

            }
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            rd.forward(req, resp);

        } else if (admin != null) {
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

            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
            rd.forward(req, resp);
        } else
            throw new socialbook.utility.ServletException("Le credenziali inserite non sono valide!!");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}