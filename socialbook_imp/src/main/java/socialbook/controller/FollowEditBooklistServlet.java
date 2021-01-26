package socialbook.controller;

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
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if (request.getParameter("follow") != null) {
            bookListDAO.doFollow(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else if (request.getParameter("unFollow") != null) {
            bookListDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else if(request.getParameter("delete") != null){
            bookListDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else if(request.getParameter("addPreferiti") != null){
            bookListDAO.addFavorite(customer.getId_customer(), request.getParameter("isbn"));

            dest=request.getHeader("referer");
            response.sendRedirect(dest);

        } else{
            if (request.getParameter("edit") != null) {
                request.setAttribute("operazione", "edit");
                request.setAttribute("books", bookListDAO.doRetriveBooks(Integer.parseInt(request.getParameter("id"))));
                request.setAttribute("booklist", bookListDAO.doRetriveBooklist(Integer.parseInt(request.getParameter("id"))));

            }else if( request.getParameter("Create") != null) {
                request.setAttribute("operazione", "Create");
            }
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/booklistEditCreate.jsp");
            dispatcher.forward(request, response);
        }
    }
}
