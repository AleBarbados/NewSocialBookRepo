package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.setup.InitTestDb;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)

public class LoginServletIntegrationTest {

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;
    @InjectMocks
    LoginServlet loginServlet = new LoginServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    public void doPostFalseTest() throws Exception{
        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("pwd")).thenReturn("7c76bc4ec270168613b1228471f29caa6dbc56f2");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            loginServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Le credenziali inserite non sono valide!!"));

    }

    public void doPostTrueCustomerTest() throws Exception{

        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("pwd")).thenReturn("7c76bc4ec270168613b1228471f29caa6dbc56f2");

    }

    public void doPostTrueAdminTest() throws Exception{

        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("pwd")).thenReturn("7c76bc4ec270168613b1228471f29caa6dbc56f2");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            loginServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Le credenziali inserite non sono valide!!"));

    }
}
