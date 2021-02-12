package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.*;
import socialbook.utility.Utility;

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
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null)
            throw new socialbook.utility.ServletException("Devi prima effettuare l'accesso!!");

        String idReview = request.getParameter("rimuovi_rec");
        String isbn = request.getParameter("isbn");
        int idCustomer = customer.getId_customer();

        String vote, body;

        if(idReview != null) {        //utente elimina la propria recensione
            reviewDAO.doDeleteById(Integer.parseInt(idReview));
        } else {        //utente aggiunge recensione
            Review review = reviewDAO.doRetrieveByISBNCustomer(isbn, idCustomer);

            Calendar calendar = Calendar.getInstance();      //si ottiene la data attuale
            java.sql.Date date = new Date (calendar.getTimeInMillis());

            if(review == null) {                 //utente non ha mai recensito il libro in questione
                Review newReview = new Review();

                newReview.setId_customer(idCustomer);
                newReview.setIsbn(isbn);
                newReview.setDate(date);

                vote = request.getParameter("voto");
                if(vote == null)
                    newReview.setVote("-");
                else
                    newReview.setVote(vote);

                body = request.getParameter("commento");
                if(body.equals(""))
                    newReview.setBody("-");
                else {
                    if(!body.matches("[a-zA-Z0-9- !?:.,()]{0,300}"))
                        throw new socialbook.utility.ServletException("");

                    newReview.setBody(body);
                }

                reviewDAO.doSave(newReview);
            } else {      //utente ha già recensito il libro in questione
                if(review.getBody().equals("-")) {
                    body = request.getParameter("commento");

                    if(body.equals(""))
                        review.setBody("-");
                    else {
                        if(!body.matches("[a-zA-Z0-9- !?:.,()]{0,300}"))
                            throw new socialbook.utility.ServletException("");

                        review.setBody(body);
                    }
                }

                if(review.getVote().equals("-")) {
                    vote = request.getParameter("voto");

                    if(vote == null)
                        review.setVote("-");
                    else
                        review.setVote(vote);
                }

                review.setDate(date);
                reviewDAO.doUpdateById(review);
            }
        }

        request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));

        ArrayList<Review> reviews = reviewDAO.doRetrieveByISBN(isbn);
        request.setAttribute("recensioni", reviews);

        ArrayList<Customer> customers = customerDAO.doRetrieveByReviews(isbn);
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
