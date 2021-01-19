package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.Author;
import socialbook.model.AuthorDAO;
import socialbook.model.Book;
import socialbook.model.BookDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@MultipartConfig
@WebServlet("/catalogueManagerServlet2")
public class CatalogueManagerCreazioneModificaServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();
    private final AuthorDAO authorDAO = new AuthorDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int price_cent = Integer.parseInt(request.getParameter("price_cent"));
        String isbnMod = request.getParameter("isbn_modifica");

        if(!isbnMod.equals("")) {        //modifica libro esistente (prezzo)
            bookDAO.doUpdatePrice(isbnMod, price_cent);
        } else {        //creazione nuovo libro
            String fileName = Utility.aggiuntaFoto(request);

            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            int publication_year = Integer.parseInt(request.getParameter("publication_year"));
            String publishing_house = request.getParameter("publishing_house");
            String plot = request.getParameter("plot");

            bookDAO.doSave(new Book(isbn, title, genre, price_cent, publication_year, publishing_house, plot, true, fileName));

            ArrayList<Author> authors = new ArrayList<Author>();
            Author a;
            String name, surname;

            for(int i = 1; i<6; i++) {
                name = request.getParameter("author_name"+i);
                surname = request.getParameter("author_surname"+i);

                if(name != null && surname != null) {
                    a = new Author(name, surname);
                    authors.add(a);
                }
            }

            authorDAO.doSave(authors, isbn);        //al libro con questo isbn vengono associati gli autori inseriti
        }

        response.sendRedirect("/socialbook_war/mostraLibriServlet?");        //FUNZIONA INSERIMENTO/RIMOZIONE DA CATALOGO MA NON IMMAGINE
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
