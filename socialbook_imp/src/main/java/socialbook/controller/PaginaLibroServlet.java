package socialbook.controller;

import socialbook.model.BookDAO;
import socialbook.model.CustomerDAO;
import socialbook.model.Review;
import socialbook.model.ReviewDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/paginaLibroServlet")
public class PaginaLibroServlet extends HttpServlet {

    private BookDAO bookDAO = new BookDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isbn=req.getParameter("ISBN");
        int id_review = Integer.parseInt(req.getParameter("id_review"));
        req.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));
        req.setAttribute("review", reviewDAO.doRetrieveById(id_review));

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
