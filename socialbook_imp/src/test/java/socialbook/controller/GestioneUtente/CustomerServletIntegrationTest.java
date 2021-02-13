package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.*;
import socialbook.setup.InitTestDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServletIntegrationTest {
    //creo mock per Request, Response, Session e RequestDispatcher (oggetti fittizi)
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @InjectMocks
    CustomerServlet customerServlet = new CustomerServlet();

    CustomerDAO customerDAO;
    BookListDAO bookListDAO;
    FollowDAO followDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        customerDAO = new CustomerDAO();
        bookListDAO = new BookListDAO();
        followDAO = new FollowDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il caso in cui non c'è nessun utente loggato
     * @throws Exception
     */
    @Test
    void testPersonalCustomerNull() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(null);

        assertAll(
                () -> Assertions.assertThrows(NullPointerException.class, () -> {
                    customerServlet.doGet(request, response);
                }),
                () -> assertNull(bookListDAO.doRetriveFavorite(50))
        );
    }

    /**
     * testa il lancio della NumberFormatException
     * @throws Exception
     */
    @Test
    void testCustomerNumberException() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customerDAO.doRetriveById(1));
        when(request.getParameter("customerView")).thenReturn("test");
        when(request.getParameter("idCustomer")).thenReturn("test");

        Assertions.assertThrows(NumberFormatException.class, () -> {
            customerServlet.doGet(request, response);
        });
    }

    /**
     * testa la visualizzazione di un customer che non esiste
     * @throws Exception
     */
    @Test
    void testCustomerNull() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customerDAO.doRetriveById(1));
        when(request.getParameter("customerView")).thenReturn("test");
        when(request.getParameter("idCustomer")).thenReturn("50");

        assertAll(
                () -> Assertions.assertThrows(NullPointerException.class, () -> {
                    customerServlet.doGet(request, response);
                }),
                () -> assertNull(bookListDAO.doRetriveFavorite(50))
        );
    }

    /**
     * testa l'esistenza del follow tra due utenti
     * @throws Exception
     */
    @Test
    void testFollowTrue() throws Exception {
        when(request.getSession()).thenReturn(session);
        Customer personalCustomer = customerDAO.doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(personalCustomer);
        when(request.getParameter("customerView")).thenReturn("test");
        when(request.getParameter("idCustomer")).thenReturn("2");
        when(request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp")).thenReturn(rd);
        customerServlet.doGet(request, response);

        assertAll(
                () -> assertTrue(followDAO.checkFollower(personalCustomer.getId_customer(), 2)),
                () -> assertNotNull(bookListDAO.doRetriveFavorite(2))
        );
    }

    /**
     * testa la non esistenza del follow tra due utenti
     * @throws Exception
     */
    @Test
    void testFollowFalse() throws Exception {
        when(request.getSession()).thenReturn(session);
        Customer personalCustomer = customerDAO.doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(personalCustomer);
        when(request.getParameter("customerView")).thenReturn("test");
        when(request.getParameter("idCustomer")).thenReturn("3");
        when(request.getRequestDispatcher("WEB-INF/jsp/customerView.jsp")).thenReturn(rd);
        customerServlet.doGet(request, response);

        assertAll(
                () -> assertFalse(followDAO.checkFollower(personalCustomer.getId_customer(), 3)),
                () -> assertNotNull(bookListDAO.doRetriveFavorite(3))
        );
    }
}