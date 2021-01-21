package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.BookListDAO;
import socialbook.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/FollowEditBooklist")
public class FollowEditBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dest;
        RequestDispatcher dispatcher;

        if (request.getParameter("follow") != null) {
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            bookListDAO.doFollow(customer.getId_customer(), Integer.parseInt(request.getParameter("id")) );

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else if (request.getParameter("unFollow") != null) {
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            bookListDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else {
            if (request.getParameter("editProfile").equals("edit")) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerEdit.jsp");
            } else {


                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerView.jsp");
            }
            dispatcher.forward(request, response);
        }
    }
}
