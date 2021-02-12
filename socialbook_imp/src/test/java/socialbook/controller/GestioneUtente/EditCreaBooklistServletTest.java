package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.CustomerDAO;
import socialbook.setup.InitTestDb;
;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)
public class EditCreaBooklistServletTest {
    //creo mock per request e response (oggetti fittizi)
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;

    @InjectMocks
    EditCreaBooklistServlet editCreaBooklistServlet = new EditCreaBooklistServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    void testGetCrea() throws Exception{
        //do un input al test
        BookListDAO bookListDAO = new BookListDAO();

        Customer customer = new CustomerDAO().doRetriveById(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("nome")).thenReturn("Test");
        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("edit")).thenReturn(null);
        when(request.getParameter("foto")).thenReturn("Test");
        editCreaBooklistServlet.doGet(request, response);

        assertEquals("Test", bookListDAO.doRetriveBooklist(6).getName());
    }

    @Test
    void testGetModifica() throws Exception{
        //do un input al test
        BookListDAO bookListDAO = new BookListDAO();

        Customer customer = new CustomerDAO().doRetriveById(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("nome")).thenReturn("Test");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("edit")).thenReturn("ciao");
        editCreaBooklistServlet.doGet(request, response);

        assertEquals("Test", bookListDAO.doRetriveBooklist(1).getName());
    }

    @Test
    void testGetWrongNameFormat() throws Exception{
        Customer customer = new Customer();
        customer.setId_customer(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("nome")).thenReturn("Testino0!!");
        when(request.getParameter("id")).thenReturn("1");

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            editCreaBooklistServlet.doGet(request, response);
        });
        assertTrue(thrown.getMessage().contains("Formato nome non corretto"));
    }
}
