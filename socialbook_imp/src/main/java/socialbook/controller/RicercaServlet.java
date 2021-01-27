package socialbook.controller;

import socialbook.model.AuthorDAO;
import socialbook.model.Book;
import socialbook.model.BookDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ricercaServlet")
public class RicercaServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parolaCercata = request.getParameter("query");

        if(parolaCercata.equals(""))
            throw new socialbook.controller.ServletException("Non hai inserito nessuna parola!!");

        ArrayList<Book> books = bookDAO.doRetrieveByTitleOrGenre(parolaCercata, 0, 10);
        ArrayList<Integer> authors = authorDAO.doRetrieveIdAuthorLike(parolaCercata, 0, 10);
        ArrayList<Book> book_autore = new ArrayList<>();

        for(Integer id : authors) {
            book_autore = bookDAO.doRetrieveByIdAuthor(id);
        }

        for(Book b : book_autore) {
            if(!books.contains(b))
                books.add(b);
        }

        request.setAttribute("parolaCercata", parolaCercata);
        request.setAttribute("books", books);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ricerca.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}