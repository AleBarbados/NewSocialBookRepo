package socialbook.controller;

import socialbook.model.BookDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/catalogueManagerServlet1")
public class CatalogueManagerInserimentoRimozioneServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        String dest = request.getHeader("referer");

        if(request.getParameter("operazione") == null) {      //inserimento/rimozione dal catalogo
            bookDAO.doUpdateCatalogue(isbn);

            if (dest == null || dest.contains("/cm1") || dest.trim().isEmpty()) {
                dest = ".";     //la destinazione sarà la pagina corrente
            }
            response.sendRedirect(dest);

        } else {
            if(request.getParameter("operazione").equals("modifica"))
                request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));
            else
                request.setAttribute("operazione", "creazione");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crea_modifica_libro.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
