package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.Book;
import socialbook.model.GestioneDatabase.BookList;
import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/BooklistViewServlet")
public class BooklistViewServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<Book> books = bookListDAO.doRetriveBooks(id);
        BookList b = bookListDAO.doRetriveBooklist(id);

        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer != null && customer.getId_customer()==Integer.parseInt(request.getParameter("idCustomer")))
            request.setAttribute("idCustomer", customer.getId_customer());

        if(customer != null && bookListDAO.checkFollower(customer.getId_customer(), id))
            request.setAttribute("follow", true);

        request.setAttribute("booklist", b);
        request.setAttribute("books", books);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/booklistView.jsp");
        requestDispatcher.forward(request, response);
    }
}