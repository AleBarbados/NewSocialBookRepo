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
class RegistrationServletIntegrationTest {
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

    /**
     * testa la registrazione che va a buon fine
     * @throws Exception
     */
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

    /**
     * testa la registrazione con il nome vuoto
     * @throws Exception
     */
    @Test
    void testEmptyName() throws Exception {
        when(request.getParameter("name")).thenReturn("");
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

    /**
     * testa la registrazione con il cognome vuoto
     * @throws Exception
     */
    @Test
    void testEmptySurname() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("");
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

    /**
     * testa la registrazione con l'username vuoto
     * @throws Exception
     */
    @Test
    void testEmptyUsername() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("");
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

    /**
     * testa la registrazione con l'email vuota
     * @throws Exception
     */
    @Test
    void testEmptyEmail() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("");
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

    /**
     * testa la registrazione con la password vuota
     * @throws Exception
     */
    @Test
    void testEmptyPassword() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Password non conforme")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    /**
     * testa la registrazione con la password troppo corta (dovrebbe essere almeno di 8 caratteri)
     * @throws Exception
     */
    @Test
    void testWrongPasswordLength() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getParameter("description")).thenReturn("ALessia Barbato");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            registrationServlet.doPost(request, response);
        });
        assertAll(
                () -> assertTrue(thrown.getMessage().contains("Password non conforme")),
                () -> assertNull(customerDAO.doRetrieveByUsername("Alessia"))
        );
    }

    /**
     * testa la registrazione con il nome troppo lungo (dovrebbe essere di massimo di 15 caratteri)
     * @throws Exception
     */
    @Test
    void testWrongNameLength() throws Exception {
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

    /**
     * testa la registrazione con cognome troppo lungo
     * @throws Exception
     */
    @Test
    void testWrongSurnameLength() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
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

    /**
     * testa la registrazione con username troppo lungo
     * @throws Exception
     */
    @Test
    void testWrongUsernameLength() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
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

    /**
     * testa la registrazione con il formato non corretto del nome (non sono ammessi numeri)
     * @throws Exception
     */
    @Test
    void testWrongNameFormat() throws Exception {
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

    /**
     * testa la registrazione con il formato non corretto del cognome (non sono ammessi numeri)
     * @throws Exception
     */
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

    /**
     * testa la registrazione con un'email già presente
     * @throws Exception
     */
    @Test
    void testEmailPresente() throws Exception {
        String email = customerDAO.doRetriveById(1).getE_mail();

        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn("Alessia99");
        when(request.getParameter("description")).thenReturn("Alessia barbato");

        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationServlet.doPost(request, response);
        });
    }

    /**
     * testa la registrazione con il formato non corretto dell'username (non sono ammessi caratteri speciali=
     * @throws Exception
     */
    @Test
    void testWrongUsernameFormat() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
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

    /**
     * testa la registrazione con formato email non corretto
     * @throws Exception
     */
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

    /**
     * testa la registrazione con il formato della password scorretto (deve esserci almeno un numero)
     * @throws Exception
     */
    @Test
    void testPostWrongPasswordNumber() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
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

    /**
     * testa la registrazione con il formato della password non corretto (deve esserci almeno un carattere maiuscolo)
     * @throws Exception
     */
    @Test
    void testPostWrongPasswordUpperCase() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
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

    /**
     * testa la registrazione con il formato della password non corretto (deve esserci almeno un carattere minuscolo)
     * @throws Exception
     */
    @Test
    void testPostWrongPasswordLowerCase() throws Exception {
        when(request.getParameter("name")).thenReturn("Alessia");
        when(request.getParameter("surname")).thenReturn("Barbato");
        when(request.getParameter("username")).thenReturn("Alessia");
        when(request.getParameter("email")).thenReturn("alessia.bar@drg.iy");
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