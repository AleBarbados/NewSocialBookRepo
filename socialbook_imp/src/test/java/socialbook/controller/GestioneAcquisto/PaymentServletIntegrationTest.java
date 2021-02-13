package socialbook.controller.GestioneAcquisto;

import com.mysql.cj.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.*;
import socialbook.setup.InitTestDb;
import socialbook.utility.CartManagement;

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
public class PaymentServletIntegrationTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @Mock
    RequestDispatcher rd;
    @Mock
    CartManagement cartManagement;

    @InjectMocks
    PaymentServlet paymentServlet = new PaymentServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa l'inserimento del metodo di pagamento del customer loggato
     * @throws Exception
     */
    @Test
    public void doPostCustomerNotNull()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123");
        when(request.getParameter("address")).thenReturn("blablabla");
        when(request.getRequestDispatcher("WEB-INF/jsp/index.jsp")).thenReturn(rd);
        paymentServlet.doPost(request, response);
        Optional<InfoPayment> infoPayment = new InfoPaymentDAO().doRetrieveByCustomer(1);
        assertTrue(infoPayment.isPresent());
    }

    /**
     * testa l'assenza del customer loggato
     */
    @Test
    public void doPostCustomerNull(){

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(null);
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Bisogna prima effettuare l'accesso!!"));
    }

    /**
     * testa il formato non corretto del nome
     * @throws Exception
     */
    @Test
    public void doPostWrongName()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede55");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato nome non valido"));

    }

    /**
     * testa il formato non corretto del cognome
     * @throws Exception
     */
    @Test
    public void doPostWrongSurname()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("Fede");
        when(request.getParameter("cardsurname")).thenReturn("cit7a");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato cognome non valido"));

    }

    /**
     * testa il formato non corretto del numero di carta
     * @throws Exception
     */
    @Test
    public void doPostWrongNumber()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("11112292b33334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato carta sbagliato"));

    }

    /**
     * testa il formato non corretto del mese di scadenza
     * @throws Exception
     */
    @Test
    public void doPostWrongMonth()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08b");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato exp-month sbagliato"));

    }

    /**
     * testa il formato non corretto dell'anno di scadenza
     * @throws Exception
     */
    @Test
    public void doPostWrongYear()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020b");
        when(request.getParameter("cvv")).thenReturn("123");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato exp-year sbagliato"));

    }

    /**
     * testa il formato non corretto del cvv
     * @throws Exception
     */
    @Test
    public void doPostWrongCvv()throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("pay")).thenReturn("Procedi al pagamento");
        Customer customer = new CustomerDAO().doRetriveById(1);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("cardname")).thenReturn("fede");
        when(request.getParameter("cardsurname")).thenReturn("cita");
        when(request.getParameter("cardnumber")).thenReturn("1111229233334444");
        when(request.getParameter("expmonth")).thenReturn("08");
        when(request.getParameter("expyear")).thenReturn("2020");
        when(request.getParameter("cvv")).thenReturn("123b");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato cvv sbagliato"));
    }
}
