package socialbook.controller;

import socialbook.Utility.BookAlreadyInsertException;
import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/show-cart-servlet")
public class ShowCartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        RequestDispatcher dispatcher;

        String isbn = req.getParameter("isbn");

        if(req.getSession().getAttribute("personalCustomer") != null) {
            if (req.getParameter("addCart") != null) {
                try {
                    cartDAO.doSaveBookCart(cart.getId_cart(), isbn);
                } catch (BookAlreadyInsertException e) {
                    e.printStackTrace();
                    req.getSession().setAttribute("cart", cart);

                    dispatcher = req.getRequestDispatcher("WEB-INF/jsp/cartView.jsp");
                    dispatcher.forward(req, resp);
                    return;
                }
                cart.insert(new BookDAO().doRetrieveByIsbn(isbn));
                cartDAO.doUpdateCustomerCart(cart);
                req.getSession().setAttribute("cart", cart);
            }else if(req.getParameter("delete") != null){
                cart.remove(bookDAO.doRetrieveByIsbn(isbn));

                cartDAO.doDeleteBookFromCart(cart.getId_cart(), isbn);
                cartDAO.doUpdateCustomerCart(cart);
                req.getSession().setAttribute("cart", cart);
            }

            dispatcher = req.getRequestDispatcher("WEB-INF/jsp/cartView.jsp");
            dispatcher.forward(req, resp);
        }
        else {
            throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");
        }
    }
}
