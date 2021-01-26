package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;
import socialbook.model.FollowDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/followEdit")
public class FollowEditServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final FollowDAO followDAO = new FollowDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer == null)
            throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");

        String dest;

        if (request.getParameter("follow") != null) {
            followDAO.doFollow(Integer.parseInt(request.getParameter("id")), customer.getId_customer());

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else if (request.getParameter("unFollow") != null) {
            followDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);
        }
    }
}
