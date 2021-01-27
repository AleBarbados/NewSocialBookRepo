package socialbook.controller;

import socialbook.model.Book;
import socialbook.model.BookDAO;
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

@WebServlet("/AddBookBooklistServlet")
public class AddBookBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    private final BookDAO bookDAO = new BookDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        bookListDAO.doSaveBook(Integer.parseInt(request.getParameter("id")), request.getParameter("isbn")); //salviamo il libro nella booklist

        ArrayList<Book> books = bookDAO.doRetrieveAll();
        request.setAttribute("books", books);                   //Setto tutti i libri come attributo

        RequestDispatcher dispatcher;
        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/libri.jsp"); //ritorniamo alla pagina del catalogo
        dispatcher.forward(request, response);
    }
}