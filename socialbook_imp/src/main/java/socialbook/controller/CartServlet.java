package socialbook.controller;

import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cartServlet")
public class CartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        Book b;

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Customer customer = (Customer) session.getAttribute("personalCustomer");

        if (cart == null) {
            cart = new Cart();
        }

        if (isbn != null) {         //inserimento o rimozione
            b = bookDAO.doRetrieveByIsbn(isbn);

            if (cart.contains(b))    //si tratta di una rimozione
                cart.remove(b);
            else                     //si tratta di un inserimento
                cart.insert(b);
        }

        if(customer != null) {
            int id = customer.getId_customer();

            if(cart.getId_customer() == id) {   //l'utente ha già il proprio carrello salvato nel database
                //cartDAO.doUpdate(cart);
            } else {                            //si salva il carrello presente in sessione nel database
                cart.setId_customer(id);
                cartDAO.doSave(cart, id);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
