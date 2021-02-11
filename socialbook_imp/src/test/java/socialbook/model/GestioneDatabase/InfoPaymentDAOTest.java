package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class InfoPaymentDAOTest {
    InfoPaymentDAO infoPaymentDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        infoPaymentDAO = new InfoPaymentDAO();

    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il doSave
     *
     * @throws SQLException
     */
    @Test
    public void doSaveTest() throws SQLException {
        infoPaymentDAO = new InfoPaymentDAO();
        InfoPayment infoPayment = new InfoPayment(1,"1111222233334444", "ale", "bar", "03", "2020", 123);
        //assertEquals();
    }
}
