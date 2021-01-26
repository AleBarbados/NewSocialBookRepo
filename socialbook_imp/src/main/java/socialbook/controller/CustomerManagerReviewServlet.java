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
        if(request.getSession().getAttribute("customerManager") == null)
            throw new socialbook.controller.ServletException("Solo il catalogue manager può effettuare questa operazione!!");

        String r_id = request.getParameter("r_id");

        if(r_id != null) {      //click sul bottone per la rimozione di una recensione
            reviewDAO.doDeleteById(Integer.parseInt(r_id));
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}