package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AdminDAOTest {
    AdminDAO adminDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        adminDAO = new AdminDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il recupero di un admin dal database, in base all'username e alla password
     */
    @Test
    public void doRetrieveByUsrEPwdTest() {
        Assertions.assertNotNull(adminDAO.doRetrieveByUsrEPwd("username","password"), "Deve tornare un admin");
    }
}
