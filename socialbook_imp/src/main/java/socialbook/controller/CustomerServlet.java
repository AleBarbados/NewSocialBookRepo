package socialbook.controller;

import socialbook.model.*;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(request.getParameter("costumerView") != null){
            Customer c = customerDAO.doRetriveById(Integer.parseInt(request.getParameter("customer")));
            request.setAttribute("customer", c);                                                   //setto il customer come attributo
            if(followDAO.checkFollower( c.getId_customer(), customer.getId_customer())){              //controllo se l'utente loggato segue il costumer
                request.setAttribute("follow", true);
            }
            BookList b = booklistDAO.doRetriveFavorite(customer.getId_customer());
            request.setAttribute("preferiti", booklistDAO.doRetriveBooks(b.getId()));  //prendo preferiti del customer per renderle poi visibili sulla sua pagona
        }
        if(request.getParameter("personalView")!=null){ //in caso l'utente voglia visitare la su area personale carichiamo i suoi dati
            request.setAttribute("view", true);
            BookList b = booklistDAO.doRetriveFavorite(customer.getId_customer());
            request.setAttribute("preferiti", booklistDAO.doRetriveBooks(b.getId()));
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp");
        requestDispatcher.forward(request, response);
    }
}
