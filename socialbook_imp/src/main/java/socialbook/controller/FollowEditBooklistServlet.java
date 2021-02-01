package socialbook.controller;

import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String destination;
        RequestDispatcher dispatcher;
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

        } else if(request.getParameter("delete") != null){
            bookListDAO.doDelete(Integer.parseInt(request.getParameter("id")));

            destination =request.getHeader("referer");
            response.sendRedirect(destination);

        } else if(request.getParameter("addPreferiti") != null){
            bookListDAO.addFavorite(customer.getId_customer(), request.getParameter("isbn"));

            destination = request.getHeader("referer");
            response.sendRedirect(destination);

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
