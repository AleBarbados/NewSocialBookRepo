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

@WebServlet("/catalogueManagerServlet")
public class CatalogueManagerServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");

        if(request.getParameter("inserimento") != null) {   //click sul bottone "inserisci"
            if(isbn != null)    //click su uno dei libri già presenti nel database, ma attualmente non presenti nel catalogo
                bookDAO.doUpdateCatalogue(isbn, true);
            else {  //creazione di un nuovo libro, aggiunto al database e catalogo
                Book l = new Book();

                l.setIsbn(request.getParameter("newIsbn"));
                l.setTitle(request.getParameter("title"));
                l.setGenre(request.getParameter("genre"));
                l.setPrice_cent(Integer.parseInt(request.getParameter("price")));
                l.setPublication_year(Integer.parseInt(request.getParameter("publication_year")));
                l.setPublishing_house(request.getParameter("publishing_house"));
                l.setPlot(request.getParameter("plot"));
                l.setCatalogue(true);

                bookDAO.doSave(l);
            }
        } else if(request.getParameter("modifica") != null) {   //click sul bottone "modifica" relativo a un libro
            int p = Integer.parseInt(request.getParameter("price"));
            bookDAO.doUpdatePrice(isbn, p);
        } else {    //click sul bottone "rimuovi" relativo a un libro
            bookDAO.doUpdateCatalogue(isbn, false);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/prova.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
