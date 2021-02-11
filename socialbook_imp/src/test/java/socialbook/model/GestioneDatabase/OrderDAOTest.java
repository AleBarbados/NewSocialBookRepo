package socialbook.model.GestioneDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class OrderDAOTest {
    OrderDetailDAO orderDetailDAO;
    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        orderDetailDAO = new OrderDetailDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il doRetrieveByOrder
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByOrderTest() throws SQLException {
        orderDetailDAO = new OrderDetailDAO();
        assertNotNull(orderDetailDAO.doRetrieveByOrder(1));
    }

    /**
     * testa il doRetrieveByCustomer
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByCustomerTest() throws SQLException {
        orderDetailDAO = new OrderDetailDAO();
        assertNotNull(orderDetailDAO.doRetrieveByCustomer(1));
    }

}
