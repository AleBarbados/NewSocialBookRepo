package socialbook.controller.GestioneAcquisto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.Cart;
import socialbook.model.GestioneDatabase.CartDAO;
import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.CustomerDAO;
import socialbook.setup.InitTestDb;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)
public class ShowCartServletIntegrationTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;
    @Mock
    HttpSession session;

    @InjectMocks
    ShowCartServlet showCartServlet = new ShowCartServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    public void doGetCustomerNull(){

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(null);
        when(session.getAttribute("cart")).thenReturn(null);

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            showCartServlet.doGet(request, response);
        });
        assertTrue(thrown.getMessage().contains("Bisogna prima effettuare l'accesso!!"));
    }

    @Test
    public void doGetAddSuccessCart(){

        Customer customer = new CustomerDAO().doRetriveById(1);
        Optional<Cart> op_cart = new CartDAO().doRetrieveByCustomer(1);
        Cart cart = op_cart.get();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("addCart")).thenReturn("");
        when(session.getAttribute("cart")).thenReturn(cart);
        when(request.getParameter("isbn")).thenReturn("9788869183157");

        when(request.getRequestDispatcher("/WEB-INF/jsp/cartView.jsp")).thenReturn(rd);
        assertDoesNotThrow(() ->showCartServlet.doGet(request, response));


    }

    @Test
    public void doGetAddSuccessCartNull(){

        Customer customer = new CustomerDAO().doRetriveById(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("addCart")).thenReturn("");
        when(session.getAttribute("cart")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn("9788869183157");

        when(request.getRequestDispatcher("/WEB-INF/jsp/cartView.jsp")).thenReturn(rd);
        assertDoesNotThrow(() ->showCartServlet.doGet(request, response));

    }



    @Test
    public void doGetAddFail(){

        Optional<Cart> op_cart = new CartDAO().doRetrieveByCustomer(1);
        Cart cart = op_cart.get();
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(session.getAttribute("cart")).thenReturn(cart);
        when(request.getParameter("isbn")).thenReturn("9788869183157");
        when(request.getParameter("addCart")).thenReturn(null);
        when(request.getParameter("delete")).thenReturn("");

        when(request.getRequestDispatcher("/WEB-INF/jsp/cartView.jsp")).thenReturn(rd);
        assertDoesNotThrow(() ->showCartServlet.doGet(request, response));


    }
}
