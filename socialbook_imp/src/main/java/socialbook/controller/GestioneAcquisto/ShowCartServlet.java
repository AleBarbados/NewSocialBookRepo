package socialbook.controller.GestioneAcquisto;

import socialbook.model.GestioneDatabase.BookDAO;
import socialbook.model.GestioneDatabase.Cart;
import socialbook.model.GestioneDatabase.CartDAO;
import socialbook.model.GestioneDatabase.Customer;
import socialbook.utility.BookAlreadyInsertException;

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
        Customer customer = (Customer) req.getSession().getAttribute("personalCustomer");

        String isbn = req.getParameter("isbn");

        if( customer != null) {
            if (req.getParameter("addCart") != null) {
                if(cart == null){
                    System.out.println("entro in cart null");
                    cart = new Cart(customer.getId_customer(), 0);
                    cartDAO.doSave(cart, customer.getId_customer());
                    req.getSession().setAttribute("cart", cart);
                }
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
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");
        }
    }
}
