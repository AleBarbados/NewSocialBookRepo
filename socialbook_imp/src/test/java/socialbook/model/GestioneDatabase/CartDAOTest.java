package socialbook.model.GestioneDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartDAOTest {
    CartDAO cartDAO;
    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        cartDAO = new CartDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il doRetrieveByCustomer
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByCustomerTest() throws SQLException {
        cartDAO = new CartDAO();
        assertNotNull(cartDAO.doRetrieveByCustomer(2));
    }

    /**
     * testa il doSave
     *
     * @throws SQLException
     */
    @Test
    public void doSaveTest() throws SQLException {
        cartDAO = new CartDAO();
        Cart cart = new Cart(1, 0);
        cartDAO.doSave(cart, 1);
        assertEquals(cart, cartDAO.doRetrieveByCustomer(1).get());
    }

    /**
     * testa il doDeleteBookFromCart
     *
     * @throws SQLException
     */
    @Test
    public void doDeleteBookFromCartTest() throws SQLException {
        cartDAO = new CartDAO();
        cartDAO.doDeleteBookFromCart(1, "9788893817035");
        List<Book> books = new OrderDetailDAO().doRetrieveByOrder(1);
        assertEquals(false, books.contains(new BookDAO().doRetrieveByIsbn("9788893817035")));
    }

}
