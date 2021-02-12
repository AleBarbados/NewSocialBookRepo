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

class RegistrationServletTest {
    //creo mock per request e response (oggetti fittizi)
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;
    @InjectMocks
    RegistrationServlet registrationServlet = new RegistrationServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    void testPost() throws Exception{
        //do un input al test
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");
        when(request.getRequestDispatcher("WEB-INF/jsp/index.jsp")).thenReturn(rd);
        registrationServlet.doPost(request, response);

        //faccio il doRetrieveById e poi vedo se è salvato correttamente (assertEquals)
    }

    @Test
    void testPostWrongPasswordNumber() throws Exception{
        //do un input al test
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        //password sbagliata
        when(request.getParameter("password")).thenReturn("Alessia");
        when(request.getParameter("description")).thenReturn("Alessia barbato");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Password non conforme"));


    }

    @Test
    void testPostWrongPasswordUpperCase() throws Exception{
        //do un input al test
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        //password sbagliata
        when(request.getParameter("password")).thenReturn("alessia");
        when(request.getParameter("description")).thenReturn("Alessia barbato");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Password non conforme"));
    }

    @Test
    void testPostWrongEmailFormat() throws Exception{
        //do un input al test
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        //email sbagliata
        when(request.getParameter("email")).thenReturn("alessia.bar@drg");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");
        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato email non corretto"));
    }
}