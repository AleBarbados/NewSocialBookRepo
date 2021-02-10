package socialbook.model.GestioneDatabase;//package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class BooklistDAOTest  {
    BookListDAO bookListDAO;


    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        bookListDAO = new BookListDAO();

    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }


    /**
     * testa il retrievebyid
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveBooklistTest() throws SQLException {
            assertNotNull(bookListDAO.doRetriveBooklist(1), "Deve tornare un turista");
    }

    /**
     * Testa l'aggiornamento di una booklist
     *
     * @throws SQLException
     */
    @Test
    public void updateTest() throws SQLException {
        bookListDAO = new BookListDAO();
        BookList bookList = bookListDAO.doRetriveBooklist(1);
        bookList.setName("Nome1");
        bookListDAO.doUpdate(bookList);
        assertEquals("Nome1", bookListDAO.doRetriveBooklist(1).getName(), "Deve ritornare Nome1");
    }

    /**
     * Testa la cancellazione di un turista
     *
     * @throws SQLException
     */
    @Test
    public void doDeleteTest() throws SQLException {
        bookListDAO = new BookListDAO();
        bookListDAO.doDelete(1);
        assertNull(bookListDAO.doRetriveBooklist(1), "Deve tornare null");
    }
}