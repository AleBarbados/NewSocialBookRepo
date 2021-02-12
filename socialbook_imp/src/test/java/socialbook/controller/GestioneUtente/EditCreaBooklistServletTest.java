package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.CustomerDAO;
;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

    @Test
    void testGetCrea() throws Exception{
        //do un input al test
        BookListDAO bookListDAO = new BookListDAO();

        Customer customer = new CustomerDAO().doRetriveById(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("nome")).thenReturn("Test");
        when(request.getParameter("id")).thenReturn("null");
        when(request.getParameter("edit")).thenReturn("null");
        when(request.getParameter("foto")).thenReturn("Test");
        editCreaBooklistServlet.doGet(request, response);

        assertEquals("Test", bookListDAO.doRetriveBooklist(6).getName());
    }

    @Test
    void testGetModifica() throws Exception{
        //do un input al test
        BookListDAO bookListDAO = new BookListDAO();

        Customer customer = new Customer();
        customer.setId_customer(1);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("edit")).thenReturn("ciao");
        when(request.getParameter("foto")).thenReturn("Test");
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
        when(request.getParameter("foto")).thenReturn("Test");
        when(request.getParameter("edit")).thenReturn(null);
        editCreaBooklistServlet.doGet(request, response);

        Exception thrown = Assertions.assertThrows(ServletException.class, () -> {
            editCreaBooklistServlet.doPost(request, response);
        });
        assertTrue(thrown.getMessage().contains("Nome non conforme"));
    }
}
