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
import javax.servlet.http.HttpSession;
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
    @Mock
    HttpSession session;
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

    /**
     * testa il login con credenziali errate (l'username non è presente nel database)
     * @throws Exception
     */
    @Test
    public void doPostFalseUsernameTest() throws Exception{
        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("pwd")).thenReturn("7c76bc4ec270168613b1228471f29caa6dbc56f2");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            loginServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Le credenziali inserite non sono valide!!"));
    }

    /**
     * testa il login con credenziali errate (la password è errata)
     * @throws Exception
     */
    @Test
    public void doPostFalsePasswordTest() throws Exception{
        when(request.getParameter("username")).thenReturn("AleBarbados");
        when(request.getParameter("pwd")).thenReturn("pass");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            loginServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Le credenziali inserite non sono valide!!"));
    }
    /**
     * testa il login del customer che ha successo
     * @throws Exception
     */
    @Test
    public void doPostTrueCustomerTest() throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("AleBarbados");
        when(request.getParameter("pwd")).thenReturn("barbados99");
        when(request.getRequestDispatcher("/WEB-INF/jsp/index.jsp")).thenReturn(rd);
        assertDoesNotThrow(() ->loginServlet.doPost(request, response));
    }

    /**
     * testa il login dell'admin che ha successo
     * @throws Exception
     */
    @Test
    public void doPostTrueAdminTest() throws Exception{

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("username")).thenReturn("prova");
        when(request.getParameter("pwd")).thenReturn("password");
        when(request.getRequestDispatcher("/WEB-INF/jsp/index.jsp")).thenReturn(rd);

        assertDoesNotThrow( () -> loginServlet.doPost(request, response));
    }
}
