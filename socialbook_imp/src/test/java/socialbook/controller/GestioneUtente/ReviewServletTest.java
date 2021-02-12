package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import socialbook.model.GestioneDatabase.*;
import socialbook.setup.InitTestDb;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ReviewServletTest{
    //creo mock per Request, Response e RequestDispatcher (oggetti fittizi)
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
    BookDAO bookDAO;
    ReviewDAO reviewDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        customerDAO = new CustomerDAO();
        bookDAO = new BookDAO();
        reviewDAO = new ReviewDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    @Test
    void testGet() throws Exception{
        //do un input al test
        Customer customer = customerDAO.doRetriveById(3);
        String isbn = "9788869186127";

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("rimuovi_rec")).thenReturn(null);
        when(request.getParameter("isbn")).thenReturn(isbn);
        when(request.getParameter("voto")).thenReturn("5");
        when(request.getParameter("commento")).thenReturn("Ottimo libro.");
        when(request.getRequestDispatcher("/WEB-INF/jsp/pagina_libro.jsp")).thenReturn(rd);
        reviewServlet.doGet(request, response);

        assertNotNull(reviewDAO.doRetrieveByISBNCustomer("9788869186127", 3));
    }
}