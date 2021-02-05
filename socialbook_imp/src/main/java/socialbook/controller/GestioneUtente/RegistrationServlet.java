package socialbook.controller.GestioneUtente;
import socialbook.model.GestioneDatabase.*;
import socialbook.utility.Utility;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration-servlet")
public class RegistrationServlet extends HttpServlet {

    private  CustomerDAO customerDAO = new CustomerDAO();
    private  CartDAO cartDAO = new CartDAO();
    private  BookListDAO bookListDAO = new BookListDAO();

    //4 test purpose only ->

    public RegistrationServlet(CustomerDAO customerDAO,CartDAO cartDAO ,BookListDAO bookListDAO ) {
        this.customerDAO = customerDAO;
        this.cartDAO = cartDAO;
        this.bookListDAO =bookListDAO;
    }// <-

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String description = request.getParameter("description");

        if(email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$")){

            if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")){

                Customer customer = new Customer(name, surname, email, Utility.encryptionSHA1(password), username, description);
                customerDAO.doSave(customer);

                Cart cart = new Cart(customer.getId_customer(), 0);
                cartDAO.doSave(cart, customer.getId_customer());

                BookList bookList = new BookList("preferiti", true, "");
                bookListDAO.doSave(bookList, customer.getId_customer());

                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
                dispatcher.forward(request, response);

            }else{
                throw new ServletException("Password non conforme");
            }

        }else{
            throw new ServletException("Formato email non corretto");
        }


    }
}
