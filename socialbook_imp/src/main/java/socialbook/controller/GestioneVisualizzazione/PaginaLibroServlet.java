package socialbook.controller.GestioneVisualizzazione;

import socialbook.model.GestioneDatabase.*;
import socialbook.utility.Utility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/paginaLibroServlet")
public class PaginaLibroServlet extends HttpServlet {
    private BookDAO bookDAO = new BookDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("libro");

        ArrayList<Review> reviews = reviewDAO.doRetrieveByISBN(isbn);
        request.setAttribute("recensioni", reviews);
        request.setAttribute("book", bookDAO.doRetrieveByIsbn(isbn));

        ArrayList<Customer> customers = customerDAO.doRetrieveByReviews(isbn);
        request.setAttribute("customers", customers);

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer != null) {      //c'è un utente loggato
            Utility.checkReview(request, isbn, customer.getId_customer());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
