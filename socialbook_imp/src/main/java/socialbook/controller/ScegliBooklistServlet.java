package socialbook.controller;

import socialbook.model.BookList;
import socialbook.model.BookListDAO;
import socialbook.model.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ScegliBooklistServlet")
public class ScegliBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer"); //prendo il customer dalla sessione

        request.setAttribute("isbn", request.getParameter("isbn"));         //setto come attributo il parametro isbn che servirà nella servlet successiva

        ArrayList<BookList> b = bookListDAO.doRetriveFromCustomer(customer.getId_customer()); //prendo le sue booklist e le setto come Attributo
        request.setAttribute("booklists", b);

        RequestDispatcher dispatcher;                                                         //faccio il forward alla pagina delle booklist
        dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ScegliBooklist.jsp");
        dispatcher.forward(request, response);
    }
}
