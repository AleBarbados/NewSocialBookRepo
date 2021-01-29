package socialbook.controller;

import socialbook.model.Book;
import socialbook.model.BookList;
import socialbook.model.BookListDAO;
import socialbook.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/BooklistViewServlet")
public class BooklistViewServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<Book> books = bookListDAO.doRetriveBooks(id);
        BookList b = bookListDAO.doRetriveBooklist(id);

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer.getId_customer()==Integer.parseInt(request.getParameter("idCustomer")))
            request.setAttribute("idCustomer", customer.getId_customer());

        if(bookListDAO.checkFollower(customer.getId_customer(), id))
            request.setAttribute("follow", true);

        request.setAttribute("booklist", b);
        request.setAttribute("books", books);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/booklistView.jsp");
        requestDispatcher.forward(request, response);
    }
}