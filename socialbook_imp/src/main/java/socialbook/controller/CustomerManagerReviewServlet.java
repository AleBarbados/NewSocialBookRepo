package socialbook.controller;

import socialbook.model.ReviewDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerManagerReviewServlet")
public class CustomerManagerReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession().getAttribute("customerManager") == null)
            throw new socialbook.controller.ServletException("Solo il catalogue manager può effettuare questa operazione!!");

        String idReview = request.getParameter("r_id");

        if(idReview != null) {      //click sul bottone per la rimozione di una recensione
            reviewDAO.doDeleteById(Integer.parseInt(idReview));
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}