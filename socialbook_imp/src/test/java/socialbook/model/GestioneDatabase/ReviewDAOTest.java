package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewDAOTest {
    ReviewDAO reviewDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        reviewDAO = new ReviewDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il recupero di una recensione dal database, in base all'ISBN del libro a cui si riferisce
     */
    @Test
    public void doRetrieveByISBN() {
       assertNotNull(reviewDAO.doRetrieveByISBN("9788893817035"), "Deve tornare una recensione");
    }

    /**
     * testa il recupero di una recensione dal database, in base all'ISBN del libro e il customer che l'ha scritta
     */
    @Test
    public void doRetrieveByISBNCustomer() {
        assertNotNull(reviewDAO.doRetrieveByISBNCustomer("9788869183157",3), "Deve tornare una recensione");
    }

    /**
     * testa la cancellazione di una recensione
     */
    @Test
    public void doDeleteById() {
        reviewDAO.doDeleteById(2);
        assertNull(reviewDAO.doRetrieveByISBNCustomer("9788893817035", 2), "Deve tornare null");
    }

    /**
     * testa l'aggiornamento di una recensione
     */
     @Test
     public void doUpdateById() {
        Review review = reviewDAO.doRetrieveByISBNCustomer("9788869183157", 3);
        review.setBody("Test body");
        review.setVote("2");
        reviewDAO.doUpdateById(review);
         assertAll( () -> assertEquals("Test body", reviewDAO.doRetrieveByISBNCustomer("9788869183157", 3).getBody(), "Deve tornare Test body"),
                 ()-> assertEquals("2", reviewDAO.doRetrieveByISBNCustomer("9788869183157", 3).getVote(), "Dere tornare 2")
         );
     }

    /**
     * testa il salvataggio di una recensione
     */
    @Test
    public void doSave() {
        Calendar calendar = Calendar.getInstance();
        java.sql.Date date = new Date(calendar.getTimeInMillis());

        Review review = new Review("Test recensione", "1", date, 1, "9788893817035");
        reviewDAO.doSave(review);
        assertEquals(review, reviewDAO.doRetrieveByISBNCustomer("9788893817035", 1));
    }
}
