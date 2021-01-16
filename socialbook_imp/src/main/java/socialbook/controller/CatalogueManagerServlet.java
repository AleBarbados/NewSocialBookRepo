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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/prova.jsp");

        String dest = request.getHeader("referer");     //prendiamo dall'header della richiesta l'url corrente
        if(dest == null || dest.contains("/catalogueManagerServlet") || dest.trim().isEmpty()){
            dest = ".";     //la destinazione sarà la pagina corrente
        }

        if(request.getParameter("inserimento") != null) {   //click sul bottone "inserisci"
            if(isbn != null) {       //click su uno dei libri già presenti nel database, ma attualmente non presenti nel catalogo
                bookDAO.doUpdateCatalogue(isbn, true);
                response.sendRedirect(dest);
            } else {
                dispatcher.forward(request, response);
            }
        } else if(request.getParameter("modifica") != null) {   //click sul bottone "modifica" relativo a un libro
            dispatcher.forward(request, response);
            //bookDAO.doUpdatePrice(isbn, p);
        } else {    //click sul bottone "rimuovi" relativo a un libro attualmente nel catalogo
            bookDAO.doUpdateCatalogue(isbn, false);
            response.sendRedirect(dest);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
