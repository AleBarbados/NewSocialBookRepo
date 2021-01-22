package socialbook.controller;

import socialbook.model.BookDAO;
import socialbook.model.Review;
import socialbook.model.ReviewDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/customerManagerReviewServlet")
public class CustomerManagerReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String isbn = request.getParameter("libro");
        String r_id = request.getParameter("r_id");

        if(isbn != null) {      //click sul bottone 'mostra libro' dalla pagina index
            request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));
            ArrayList<Review> reviews = reviewDAO.doRetrieveByISBN(isbn);
            request.setAttribute("recensioni", reviews);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp");
            dispatcher.forward(request, response);
        } else if(r_id != null) {      //click sul bottone per la rimozione di una recensione
            reviewDAO.doDeleteById(Integer.parseInt(r_id));

            response.sendRedirect(request.getHeader("referer"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}