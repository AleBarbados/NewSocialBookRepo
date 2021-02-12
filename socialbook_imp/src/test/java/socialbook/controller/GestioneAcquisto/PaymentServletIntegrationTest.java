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

    @Test
    public void doPostCustomerNotNull(){

        Customer customer = new CustomerDAO().doRetriveById(1);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
    }

    @Test
    public void doPostCustomerNull(){

        when(request.getSession().getAttribute("personalCustomer")).thenReturn(null);
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            paymentServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Bisogna prima effettuare l'accesso!!"));
    }
}
