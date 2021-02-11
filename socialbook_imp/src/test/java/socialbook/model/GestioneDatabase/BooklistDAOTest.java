package socialbook.model.GestioneDatabase;//package dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * testa il doRetrievebyid
     */
    @Test
    public void doRetrieveBooklistTest(){
        assertNotNull(bookListDAO.doRetriveBooklist(1), "Deve tornare una booklist");
    }

    /**
     * testa il doRetrieveFollowed
     */
    @Test
    public void doRetriveFollowedTest(){
        assertNotNull(bookListDAO.doRetriveFollowed(1), "Deve tornare una lista di booklist");
    }

    /**
     * testa il doRetriveFromCustomer
     */
    @Test
    public void doRetriveFromCustomerTest(){
        assertNotNull(bookListDAO.doRetriveFromCustomer(1), "Deve tornare una lista di booklist");
    }

    /**
     * testa il doRetriveBooks
     */
    @Test
    public void doRetriveBooksTest(){
        assertNotNull(bookListDAO.doRetriveBooks(1), "Deve tornare una lista di libri");
    }

    /**
     * testa il doRetriveBooks
     */
    @Test
    public void doRetriveFavoriteTest(){
        assertNotNull(bookListDAO.doRetriveFavorite(1), "Deve tornare la booklist preferiti");
    }

    /**
     * Testa l'aggiornamento di una booklist
     */
    @Test
    public void updateTest(){
        BookList bookList = bookListDAO.doRetriveBooklist(1);
        bookList.setName("Nome1");
        bookListDAO.doUpdate(bookList);
        assertEquals("Nome1", bookListDAO.doRetriveBooklist(1).getName(), "Deve ritornare Nome1");
    }

    /**
     * Testa la cancellazione di una booklist
     */
    @Test
    public void doDeleteTest(){
        bookListDAO.doDelete(1);
        assertNull(bookListDAO.doRetriveBooklist(1), "Deve tornare null");
    }

    /**
     * testa la funzionalità di seguire una booklist
     */
    @Test
    public void doFollowTest() {
        bookListDAO.doFollow(1, 1);
        ArrayList<BookList> bookLists = bookListDAO.doRetriveFollowed(1);
        assertTrue(bookLists.contains(bookListDAO.doRetriveBooklist(1)));
    }

    /**
     * testa la funzionalità di smettere di seguire una booklist
     */
    @Test
    public void doUnFollowTest(){
        bookListDAO.doFollow(1, 1);
        bookListDAO.doUnFollow(1, 1);
        ArrayList<BookList> bookLists = bookListDAO.doRetriveFollowed(1);
        assertFalse(bookLists.contains(bookListDAO.doRetriveBooklist(1)));
    }

    /**
     * testa la funzionalità di salvare una booklist
     */
    @Test
    public void doSaveTest(){
        BookList bookList = new BookList("Test", false, "");
        bookListDAO.doSave(bookList, 1);
        assertNotNull(bookListDAO.doRetriveBooklist(6));
    }

    /**
     * testa la funzionalità di salvare un libro in una booklist
     */
    @Test
    public void doSaveBookTest(){
        bookListDAO.doSaveBook(1, "9788869186127");
        ArrayList<Book> books = bookListDAO.doRetriveBooks(1);
        BookDAO bookDAO = new BookDAO();
        assertTrue(books.contains(bookDAO.doRetrieveByIsbn("9788869186127")));
    }

}