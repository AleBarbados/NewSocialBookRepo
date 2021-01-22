package socialbook.controller;

import socialbook.Utility.Utility;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

@WebServlet("/reviewServlet")
public class ReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String review = request.getParameter("rimuovi_rec");

        if(review != null) {        //utente elimina la propria recensione
            reviewDAO.doDeleteById(Integer.parseInt(review));

            response.sendRedirect(request.getParameter("referer"));
        } else {
            GregorianCalendar gc = new GregorianCalendar();
            Review r = new Review();

            String book = request.getParameter("book");
            String utente = request.getParameter("utente");

            String str = "" + gc.get(Calendar.YEAR) + gc.get(Calendar.MONTH) + gc.get(Calendar.DAY_OF_MONTH);
            Calendar cal = Calendar.getInstance();      //si ottiene la data attuale
            java.sql.Date date = new Date (cal.getTimeInMillis());

            r.setId_customer(Integer.parseInt(utente));
            r.setIsbn(book);
            r.setDate(date);

            if(request.getParameter("operazione") != null) {        //si tratta di un voto
                r.setVote(request.getParameter("voto"));
            }

            r.setBody(request.getParameter("commento"));
            reviewDAO.doSave(r);

            response.sendRedirect("/socialbook_war/customerManagerServlet?");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
