package socialbook.model.GestioneDatabase;//package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.IntegrationTestCase;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class BooklistDAOTest extends IntegrationTestCase {
    BookListDAO manager;

    @Override
    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        super.setup();
    }

    @Override
    @AfterEach
    public void tearDown() throws SQLException {
        super.tearDown();
    }


    /**
     * testa il retrievebyid
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveBooklistTest() throws SQLException {
        manager = new BookListDAO();
        assertNotNull(manager.doRetriveBooklist(1), "Deve tornare un turista");
    }

    /**
     * Testa l'aggiornamento di una booklist
     *
     * @throws SQLException
     */
    @Test
    public void updateTest() throws SQLException {
        manager = new BookListDAO();
        BookList bookList = manager.doRetriveBooklist(1);
        bookList.setName("Nome1");
        assertEquals("Nome1", manager.doRetriveBooklist(1).getName(), "Deve ritornare Nome1");
    }

    /**
     * Testa la cancellazione di un turista
     *
     * @throws SQLException
     */
    @Test
    public void doDeleteTest() throws SQLException {
        manager = new BookListDAO();
        manager.doDelete(1);
        assertNull(manager.doRetriveBooklist(1), "Deve tornare null");
    }
}