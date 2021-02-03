package socialbook.controller.GestioneInterazione;

import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FollowBooklistServlet")
public class FollowBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer == null)
            throw new socialbook.utility.ServletException("HEYY, devi fare l'accesso prima!!");

        if (request.getParameter("follow") != null) {
            bookListDAO.doFollow(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            destination = request.getHeader("referer");
            response.sendRedirect(destination);

        } else if (request.getParameter("unFollow") != null) {
            bookListDAO.doUnFollow(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            destination = request.getHeader("referer");
            response.sendRedirect(destination);

        }
    }
}
