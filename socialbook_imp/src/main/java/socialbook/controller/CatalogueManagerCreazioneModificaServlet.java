package socialbook.controller;

import socialbook.model.Book;
import socialbook.model.BookDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/catalogueManagerServlet2")
public class CatalogueManagerCreazioneModificaServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int price_cent = Integer.parseInt(request.getParameter("price_cent"));
        String isbnMod = request.getParameter("isbn_modifica");

        if(!isbnMod.equals("")) {        //modifica libro esistente (prezzo)
            bookDAO.doUpdatePrice(isbnMod, price_cent);
        } else {        //creazione nuovo libro
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            int publication_year = Integer.parseInt(request.getParameter("publication_year"));
            String publishing_house = request.getParameter("publishing_house");
            String plot = request.getParameter("plot");

            bookDAO.doSave(new Book(isbn, title, genre, price_cent, publication_year, publishing_house, plot, true));

        }

        request.setAttribute("books", bookDAO.doRetrieveAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/libri.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
