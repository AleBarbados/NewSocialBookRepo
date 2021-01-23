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
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet("/reviewServlet")
public class ReviewServlet extends HttpServlet {
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final BookDAO bookDAO = new BookDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String review = request.getParameter("rimuovi_rec");
        String isbn = request.getParameter("isbn");

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        int id_customer = customer.getId_customer();

        String vote, body;

        if(review != null) {        //utente elimina la propria recensione
            reviewDAO.doDeleteById(Integer.parseInt(review));
        } else {        //utente aggiunge recensione
            Review r = reviewDAO.doRetrieveByISBNCustomer(isbn, id_customer);

            Calendar cal = Calendar.getInstance();      //si ottiene la data attuale
            java.sql.Date date = new Date (cal.getTimeInMillis());

            if(r == null) {                 //utente non ha mai recensito il libro in questione
                Review r_new = new Review();

                r_new.setId_customer(id_customer);
                r_new.setIsbn(isbn);
                r_new.setDate(date);

                vote = request.getParameter("voto");
                if(vote == null)
                    r_new.setVote("-");
                else
                    r_new.setVote(vote);

                body = request.getParameter("commento");
                if(body.equals(""))
                    r_new.setBody("-");
                else
                    r_new.setBody(body);

                reviewDAO.doSave(r_new);

            } else {                  //utente ha già recensito il libro in questione
                if(r.getBody().equals("-")) {
                    body = request.getParameter("commento");

                    if(body.equals(""))
                        r.setBody("-");
                    else
                        r.setBody(body);
                }

                if(r.getVote().equals("-")) {
                    vote = request.getParameter("voto");

                    if(vote == null)
                        r.setVote("-");
                    else
                        r.setVote(vote);
                }

                r.setDate(date);
                reviewDAO.doUpdateById(r);
            }
        }

        request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));

        ArrayList<Review> recensioni = reviewDAO.doRetrieveByISBN(isbn);
        request.setAttribute("recensioni", recensioni);

        ArrayList<Customer> customers = customerDAO.doRetrieveByReviews(recensioni);
        request.setAttribute("customers", customers);

        Utility.checkReview(request, isbn, customer.getId_customer());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
