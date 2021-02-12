package socialbook.controller.GestioneUtente;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class EditDeleteCreaBooklistServletTest {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    RequestDispatcher rd;

    @InjectMocks
    EditDeleteCreaBooklistServlet editDeleteCreaBooklistServlet = new EditDeleteCreaBooklistServlet();

    @Test
    void doGetDeleteTest() throws ServletException, IOException {
        BookListDAO bookListDAO = new BookListDAO();

       Customer customer = new Customer();
        customer.setId_customer(1);

        when(request.getSession().getAttribute("personalCustomer")).thenReturn(customer);
        when(request.getParameter("delete")).thenReturn("lol");
        when(request.getHeader("referer")).thenReturn("aosdk");
        when(request.getParameter("addPreferiti")).thenReturn("lol");
        when(request.getParameter("edit")).thenReturn("lol");
        when(request.getAttribute("operazione")).thenReturn("lol");
        when(request.getParameter("Create")).thenReturn("lol");
        when(request.getAttribute("books")).thenReturn("lol");
        when(request.getAttribute("booklist")).thenReturn("lol");
        when(request.getRequestDispatcher("/WEB-INF/jsp/booklistEditCreate.jsp")).thenReturn(rd);
        editDeleteCreaBooklistServlet.doGet(request, response);

        assertNull(bookListDAO.doRetriveBooklist(6).getName());
    }


}
