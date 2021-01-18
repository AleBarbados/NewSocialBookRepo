package socialbook.controller;

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

/*@WebServlet(urlPatterns = "", name = "HomeServlet", loadOnStartup = 1)
public class ProvaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() throws ServletException {
        BookDAO bookDAO = new BookDAO();
        String ciao = "ciao";

        getServletContext().setAttribute("notCatalogue", ciao);

        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookDAO bookDAO = new BookDAO();

        ArrayList<Book> books = bookDAO.doRetrieveAll();
        request.setAttribute("books", books);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prova.jsp");
        dispatcher.forward(request, response);
    }
}*/