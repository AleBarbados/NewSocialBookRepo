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
class ReviewServletIntegrationTest {
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
    ReviewServlet reviewServlet = new ReviewServlet();

    CustomerDAO customerDAO;
    ReviewDAO reviewDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        customerDAO = new CustomerDAO();
        reviewDAO = new ReviewDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa l'inserimento di una nuova recensione (voto + commento) che va a buon fine
     * @throws Exception
     */
    @Test
    void testNewReview() throws Exception {
        Customer customer = customerDAO.doRetriveById(3);
        String isbn = "9788869186127";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("voto")).thenReturn("5");
        when(request.getParameter("commento")).thenReturn("Test commento2");
        when(request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp")).thenReturn(rd);
        reviewServlet.doGet(request, response);

        assertNotNull(reviewDAO.doRetrieveByISBNCustomer("9788869186127", 3));
    }

    /**
     * testa il tentativo di recensione da parte di un utente non loggato (non va a buon fine)
     * @throws Exception
     */
    @Test
    void testCustomerNull() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(null);

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            reviewServlet.doGet(request, response);
        });
        assertTrue(thrown.getMessage().contains("Devi prima effettuare l'accesso!!"));
    }

    /**
     * testa l'inserimento di una recensione (commento) che va a buon fine
     * @throws Exception
     */
    @Test
    void testNewBody() throws Exception {
        Customer customer = customerDAO.doRetriveById(1);
        String isbn = "9788869183157";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("commento")).thenReturn("Test commento");
        when(request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp")).thenReturn(rd);
        reviewServlet.doGet(request, response);

        assertEquals("Test commento", reviewDAO.doRetrieveByISBNCustomer(isbn, customer.getId_customer()).getBody());
    }

    /**
     * testa l'inserimento di una recensione (voto) che va a buon fine
     * @throws Exception
     */
    @Test
    void testNewVote() throws Exception {
        Customer customer = customerDAO.doRetriveById(2);
        String isbn = "9788893817035";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("voto")).thenReturn("2");
        when(request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp")).thenReturn(rd);
        reviewServlet.doGet(request, response);

        assertEquals("2", reviewDAO.doRetrieveByISBNCustomer(isbn, customer.getId_customer()).getVote());
    }

    /**
     * testa l'inserimento di una registrazione con un voto troppo alto
     * @throws Exception
     */
    @Test
    void testWrongVote() throws Exception {
        Customer customer = customerDAO.doRetriveById(1);
        String isbn = "1234";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("voto")).thenReturn("6");

        Assertions.assertThrows(ServletException.class, () -> {
            reviewServlet.doPost(request, response);
        });
    }

    /**
     * testa l'inserimento di una recensione con il formato del commento non corretto (non sono ammessi simboli %$&£)
     * @throws Exception
     */
    @Test
    void testWrongComment() throws Exception {
        Customer customer = customerDAO.doRetriveById(3);
        String isbn = "00000";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("voto")).thenReturn("5");
        when(request.getParameter("commento")).thenReturn("Test commento%$&£");

        Assertions.assertThrows(ServletException.class, () -> {
            reviewServlet.doPost(request, response);
        });
    }
}