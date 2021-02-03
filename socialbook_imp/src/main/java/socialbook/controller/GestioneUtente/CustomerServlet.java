package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerServlet")
public class CustomerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final FollowDAO followDAO = new FollowDAO();
    private final BookListDAO booklistDAO = new BookListDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customerLoggato = (Customer) request.getSession().getAttribute("personalCustomer");

        if(request.getParameter("customerView") != null){
            Customer customer = customerDAO.doRetriveById(Integer.parseInt(request.getParameter("idCustomer")));
            request.setAttribute("customer", customer);   //setto il customer come attributo

            if(customerLoggato != null) {
                if (followDAO.checkFollower(customer.getId_customer(), customerLoggato.getId_customer())) {      //controllo se l'utente loggato segue il costumer
                    request.setAttribute("follow", true);
                }
                request.setAttribute("idCustomer", customerLoggato.getId_customer());
            }

            BookList favourites = booklistDAO.doRetriveFavorite(customer.getId_customer());
            request.setAttribute("preferiti", booklistDAO.doRetriveBooks(favourites.getId()));  //prendo preferiti del customer per renderle poi visibili sulla sua pagoina

        } else if(request.getParameter("personalView") != null){    //in caso l'utente voglia visitare la sua area personale carichiamo i suoi dati
            if(customerLoggato == null)
                throw new socialbook.utility.ServletException("HEYY, devi fare l'accesso prima!!");

            request.setAttribute("idCustomer", customerLoggato.getId_customer());

            BookList favourites = booklistDAO.doRetriveFavorite(customerLoggato.getId_customer());
            request.setAttribute("preferiti", booklistDAO.doRetriveBooks(favourites.getId()));
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp");
        requestDispatcher.forward(request, response);
    }
}
