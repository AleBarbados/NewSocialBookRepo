package socialbook.controller;

import socialbook.model.BookList;
import socialbook.model.BookListDAO;
import socialbook.model.Customer;
import socialbook.model.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer.getId_customer() == Integer.parseInt(request.getParameter("id"))){

            ArrayList<BookList> booklists = bookListDAO.doRetriveFromCustomer(customer.getId_customer()); //prendo le booklist create dall'utente
            ArrayList<BookList> followed = bookListDAO.doRetriveFollowed(customer.getId_customer());//prendo le booklist seguite dall'utente

            request.setAttribute("idCustomer", customer.getId_customer());   //setto come parametro l'id dell'utente
            request.setAttribute("booklists", booklists);
            request.setAttribute("followed", followed);  //setto le liste di booklist

        }else{
            Customer customer1 = customerDAO.doRetriveById(Integer.parseInt(request.getParameter("id")));

            ArrayList<BookList> booklists = bookListDAO.doRetriveFromCustomer(customer1.getId_customer()); //prendo le booklist create dall'utente
            ArrayList<BookList> followed = bookListDAO.doRetriveFollowed(customer1.getId_customer());//prendo le booklist seguite dall'utente

            request.setAttribute("idCustomer", customer1.getId_customer());   //setto come parametro l'id dell'utente
            request.setAttribute("booklists", booklists);
            request.setAttribute("followed", followed);  //setto le liste di booklist
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/allBooklistsView.jsp");
        requestDispatcher.forward(request, response); //mando tutto alla jsp
    }
}
