package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
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
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith( MockitoExtension.class)
public class EditDeleteCreaBooklistServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;
    @Mock
    HttpSession session;

    @InjectMocks
    EditDeleteCreaBooklistServlet editDeleteCreaBooklistServlet = new EditDeleteCreaBooklistServlet();

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    void doGetDeleteTest() throws ServletException, IOException {
        BookListDAO bookListDAO = new BookListDAO();

        Customer customer = new CustomerDAO().doRetriveById(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("delete")).thenReturn("lol");
        when(request.getHeader("referer")).thenReturn("referer");
        editDeleteCreaBooklistServlet.doGet(request, response);

        assertNull(bookListDAO.doRetriveBooklist(1));
    }

    @Test
    void doGetAddPreferitiTest() throws ServletException, IOException {
        BookListDAO bookListDAO = new BookListDAO();
        BookDAO bookDAO = new BookDAO();

        Customer customer = new CustomerDAO().doRetriveById(1);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("addPreferiti")).thenReturn("true");
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("delete")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn("9788869183157");
        when(request.getHeader("referer")).thenReturn("referer");

        editDeleteCreaBooklistServlet.doGet(request, response);

        assertTrue(bookListDAO.doRetriveBooks(4).contains(bookDAO.doRetrieveByIsbn("9788869183157")));
    }
}
