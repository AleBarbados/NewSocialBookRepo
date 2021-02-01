package socialbook.controller.GestioneVisualizzazione;

import socialbook.model.CartDAO;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="HomeServlet", urlPatterns = "/index.html", loadOnStartup=1)
public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* CustomerDAO customerDAO = new CustomerDAO();
        request.getSession().setAttribute("personalCustomer", customerDAO.doRetriveById(1));
        request.getSession().setAttribute("cart", new CartDAO().doRetrieveByCustomer(1));

        AdminDAO adminDAO = new AdminDAO();
        request.getSession().setAttribute("customerManager", adminDAO.doRetrieveByUsrEPwd("username", "password"));

        request.setAttribute("ISBN", "9788869183157"); */

        String address= "/WEB-INF/jsp/index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}