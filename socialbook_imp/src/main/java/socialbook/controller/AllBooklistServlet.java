package socialbook.controller;

import socialbook.model.BookList;
import socialbook.model.BookListDAO;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/allBooklistServlet")
public class AllBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        ArrayList<BookList> personalBooklists;
        ArrayList<BookList> followedBooklists;

        int idCustomer = Integer.parseInt(request.getParameter("id"));

        if(customer.getId_customer() == idCustomer){    //l'utente loggato vuole vedere le proprie booklist
            personalBooklists = bookListDAO.doRetriveFromCustomer(customer.getId_customer()); //prendo le booklist create dall'utente
            followedBooklists = bookListDAO.doRetriveFollowed(customer.getId_customer());     //prendo le booklist seguite dall'utente
        }else{      //l'utente loggato vuole vedere le booklist di un secondo utente
            Customer customerLoggato = customerDAO.doRetriveById(idCustomer);

            personalBooklists = bookListDAO.doRetriveFromCustomer(customerLoggato.getId_customer()); //prendo le booklist create dall'utente
            followedBooklists = bookListDAO.doRetriveFollowed(customerLoggato.getId_customer());//prendo le booklist seguite dall'utente
        }

        request.setAttribute("idCustomer", customer.getId_customer());   //setto come parametro l'id dell'utente
        request.setAttribute("booklists", personalBooklists);
        request.setAttribute("followed", followedBooklists);  //setto le liste di booklist

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/allBooklistsView.jsp");
        requestDispatcher.forward(request, response); //mando tutto alla jsp
    }
}
