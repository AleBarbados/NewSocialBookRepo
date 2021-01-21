package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.ReviewDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reviewDeleteServlet")
public class ReviewDeleteServlet extends HttpServlet {

    private final ReviewDAO ReviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id_review"));
        String dest = req.getHeader("referer");
        String conf = "/ReviewServlet";
        ReviewDAO.doDeleteById(id);


        Utility.redirect(resp, dest, conf);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
