package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.*;

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
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String review = request.getParameter("rimuovi_rec");
        String isbn = request.getParameter("isbn");
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        int id_customer = customer.getId_customer();

        if(review != null) {        //utente elimina la propria recensione
            reviewDAO.doDeleteById(Integer.parseInt(review));
        } else {        //utente aggiunge recensione
            GregorianCalendar gc = new GregorianCalendar();
            Review r = new Review();

            String str = "" + gc.get(Calendar.YEAR) + gc.get(Calendar.MONTH) + gc.get(Calendar.DAY_OF_MONTH);
            Calendar cal = Calendar.getInstance();      //si ottiene la data attuale
            java.sql.Date date = new Date (cal.getTimeInMillis());

            r.setId_customer(id_customer);
            r.setIsbn(isbn);
            r.setDate(date);
            r.setVote(request.getParameter("voto"));
            r.setBody(request.getParameter("commento"));
            reviewDAO.doSave(r);
        }

        Utility.checkReview(request, isbn, id_customer);
        request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));
        request.setAttribute("recensioni", reviewDAO.doRetrieveByISBN(isbn));

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
