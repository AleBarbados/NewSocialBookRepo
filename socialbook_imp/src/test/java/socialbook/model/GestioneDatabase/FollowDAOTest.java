package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FollowDAOTest {
    FollowDAO followDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        followDAO = new FollowDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * Testa il controllo
     */
    @Test
    public void checkFollowerTest(){
        assertTrue(followDAO.checkFollower(1,2), "Deve tornare true");
    }

    /**
     * Testa l'eliminazione di un seguito
     */
    @Test
    public void doDeleteTest(){
        followDAO.doDelete(2, 1);
        assertFalse(followDAO.checkFollower(1,2), "Deve tornare false");
    }

    /**
     * testa il doRetrievebyid
     */
    @Test
    public void doRetriveAllFollowedTest(){
        assertNotNull(followDAO.doRetriveAllFollowed(2), "Deve tornare una lista di customer");
    }

    /**
     * testa il doRetrievebyid
     */
    @Test
    public void doRetriveAllFollowersTest(){
        assertNotNull(followDAO.doRetriveAllFollowers(1), "Deve tornare una lista di customer");
    }

    /**
     * testa il follow
     */
    @Test
    public void doFollowTest(){
        followDAO.doFollow(2, 1);
        assertNotNull(followDAO.doRetriveAllFollowers(2));
    }
}
