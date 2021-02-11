package socialbook.model.GestioneDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDAOTest {
    BookDAO bookDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        bookDAO = new BookDAO();

    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il doRetrieveCatalogueByIsbn
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveCatalogueByIsbnTest() throws SQLException {
        bookDAO = new BookDAO();
        assertNotNull(bookDAO.doRetrieveCatalogueByIsbn("9788869183157"));
    }

    /**
     * testa il doRetrieveByIsbn
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByIsbnTest() throws SQLException{
        bookDAO = new BookDAO();
        assertNotNull(bookDAO.doRetrieveByIsbn("9788869183157"));
    }

    /**
     * testa il doUpdateCatalogue
     *
     * @throws SQLException
     */
    @Test
    public void updateCatalogueTest() throws SQLException {
        bookDAO = new BookDAO();
        Boolean test = bookDAO.doRetrieveByIsbn("9788869183157").getCatalogue();
        bookDAO.doUpdateCatalogue("9788869183157");
        assertEquals(!bookDAO.doRetrieveCatalogueByIsbn("9788869183157"),test );


    }

    /**
     * testa il doUpdatePrice
     *
     * @throws SQLException
     */
    @Test
    public void doUpdatePriceTest() throws SQLException{
        bookDAO = new BookDAO();
        Book book = bookDAO.doRetrieveByIsbn("9788869183157");
        bookDAO.doUpdatePrice("9788869183157", 2345);
        assertNotEquals(bookDAO.doRetrieveByIsbn("9788869183157").getPrice_cent(), book);
    }

    /**
     * testa il doSave
     *
     * @throws SQLException
     */
    @Test
    public void doSaveTest() throws SQLException{
        bookDAO = new BookDAO();
        Book book = new Book("9788869183159", "Il maestro e Margherita", "boh", 900, 1945, "Feltrinelli",
                "ci sono dei russi e il diavolo", true, "ciao");

        bookDAO.doSave(book);
        assertEquals(book, bookDAO.doRetrieveByIsbn("9788869183159"));
    }
    /**
     * testa il doRetrieveAll
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveAllTest() throws SQLException{
        bookDAO = new BookDAO();
        assertNotNull(bookDAO.doRetrieveAll());
    }

    /**
     * testa il doRetrieveByTitleOrGenre
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByTitleOrGenreTest() throws SQLException{
        bookDAO = new BookDAO();
        assertNotNull(bookDAO.doRetrieveByTitleOrGenre("ciao",0,4));
    }

    /**
     * testa il doRetrieveByAuthor
     *
     * @throws SQLException
     */
    @Test
    public void doRetrieveByAuthor() throws SQLException{
        bookDAO = new BookDAO();
        assertNotNull(bookDAO.doRetrieveByIdAuthor(1));
    }
}
