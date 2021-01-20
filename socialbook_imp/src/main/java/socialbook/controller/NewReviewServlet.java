package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.Book;
import socialbook.model.Review;
import socialbook.model.ReviewDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/newReviewServlet")
public class NewReviewServlet extends HttpServlet {

    private final ReviewDAO ReviewDAO = new ReviewDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dest = req.getHeader("referer");
        String conf = "/ReviewServlet";
        int id_review = Integer.parseInt(req.getParameter("id_review"));
        int id_customer = Integer.parseInt(req.getParameter("id_customer"));
        String ISBN = req.getParameter("ISBN");
        Date review_date = Date.valueOf(req.getParameter("publication_year"));
        String body = req.getParameter("body");
        int vote = Integer.parseInt(req.getParameter("vote"));

        ReviewDAO.doSave(new Review(id_review, id_customer, ISBN, review_date, body, vote));
        Utility.redirect(resp, dest, conf);
    }
}
