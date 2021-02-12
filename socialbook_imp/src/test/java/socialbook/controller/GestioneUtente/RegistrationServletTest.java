package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.CustomerDAO;
import socialbook.setup.InitTestDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrationServletTest {
    //creo mock per Request, Response e RequestDispatcher (oggetti fittizi)
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;
    @InjectMocks
    RegistrationServlet registrationServlet = new RegistrationServlet();

    CustomerDAO customerDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        customerDAO = new CustomerDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    void testPost() throws Exception {
        //do un input al test
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia99");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.it");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");
        when(request.getRequestDispatcher("WEB-INF/jsp/index.jsp")).thenReturn(rd);
        registrationServlet.doPost(request, response);

        assertNotNull(customerDAO.doRetrieveByUsername("Alessia99"));
    }

    @Test
    void testWrongNameLength() throws Exception {
        //lunghezza del nome sbagliata
        when(request.getParameter("name")).thenReturn("Alessiaaaaaaaaaa");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato nome non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testWrongNameFormat() throws Exception {
        //formato del nome sbagliato
        when(request.getParameter("name")).thenReturn("Alessia1");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato nome non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testWrongSurnameLength() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        //lunghezza del cognome sbagliata
        when(request.getParameter("surname")).thenReturn("Barbatoooooooooo");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato cognome non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testWrongUsernameLength() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        //lunghezza username sbagliata
        when(request.getParameter("username")).thenReturn("Alessiaaaaaaaaaa");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato username non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testWrongUsernameFormat() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        //formato username non corretto
        when(request.getParameter("username")).thenReturn("Alessia.");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato username non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testWrongSurnameFormat() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        //formato del cognome sbagliato
        when(request.getParameter("surname")).thenReturn("Barbato2");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato cognome non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testPostWrongEmailFormat() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        //formato dell'email sbagliato
        when(request.getParameter("email")).thenReturn("alessia.bar@drg");
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Formato email non corretto")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testPostWrongPasswordNumber() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        //formato password sbagliato (manca il numero)
        when(request.getParameter("password")).thenReturn("Alessia");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });

        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Password non conforme")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testPostWrongPasswordUpperCase() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        //formato password sbagliato (manca una lettera maiuscola)
        when(request.getParameter("password")).thenReturn("alessia");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });

        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Password non conforme")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    @Test
    void testPostWrongPasswordLowerCase() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        //formato password sbagliato (manca una lettera minuscola)
        when(request.getParameter("password")).thenReturn("alessia");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });

        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Password non conforme")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }
}