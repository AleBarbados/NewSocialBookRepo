package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import socialbook.setup.InitTestDb;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest {
    CustomerDAO customerDAO;

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        new InitTestDb().initeDb();
        customerDAO = new CustomerDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        new InitTestDb().destroyDb();
    }

    /**
     * testa il doRetrievebyid
     */
    @Test
    public void doRetrieveByIdTest(){
        assertNotNull(customerDAO.doRetriveById(1), "Deve tornare un Customer");
    }

    /**
     * testa il doRetrieveAll
     */
    @Test
    public void doRetrieveAllTest(){
        assertNotNull(customerDAO.doRetrieveAll(), "Deve tornare tutti i customer");
    }

    /**
     * testa il doRetriveFormEmail
     */
    @Test
    public void doRetriveByEmailTest(){
        assertNotNull(customerDAO.doRetriveByEmail("nonpuoesistere@gmail.hhi"), "Deve tornare un customer");
    }

    /**
     * testa la funzionalità di salvare un customer
     */
    @Test
    public void doSaveTest(){
        Customer c = new Customer("Utentee", "Fittizioo", "nonpuoesister3@gmail.hhi", "nonpuoiaccedere1", "UtenteFittizioo", "utentee fittizio");
        customerDAO.doSave(c);
        assertNotNull(customerDAO.doRetriveByEmail("nonpuoesister3@gmail.hhi"));
    }

    /**
     * testa la doRetrieveByUsernameTest
     */
    @Test
    public void doRetrieveByUsernameTest(){
        assertNotNull(customerDAO.doRetrieveByUsername("UtenteFittizio"), "Deve tornare un customer");
    }

    /**
     * testa il metodo di controllo della presenza del customer e della correttezza della password
     */
    @Test
    public void validateTest(){
        assertTrue(customerDAO.validate("UtenteFittizio", "nonpuoiaccedere"));
    }

    /**
     * Testa l'aggiornamento di un customer
     */
    @Test
    public void updateTest(){
        Customer customer = customerDAO.doRetriveById(1);
        customer.setDescription("Testino");
        customer.setC_pwd("Testpassword");
        customer.setImage("testimage");
        customerDAO.doUpdate(customer);
        assertAll( () -> assertEquals("Testino", customerDAO.doRetriveById(1).getDescription(), "Deve ritornare Testino"),
                   ()-> assertEquals("Testpassword", customerDAO.doRetriveById(1).getC_pwd(), "Dere ritornare Testpassword"),
                   ()-> assertEquals("testimage", customerDAO.doRetriveById(1).getImage(), "Dere ritornare testimage")
        );
    }

    /**
     * Testa l'eliminazione di un libro dato l'id
     */
    @Test
    public void doDeleteByIdTest(){
        customerDAO.doDeleteById(1);
        assertNull(customerDAO.doRetriveById(1), "Deve tornare null");
    }

    /**
     * Testa doRetriveByReviews
     */
    @Test
    public void doRetrieveByReviewsTest(){
        //assertNotNull(customerDAO.doRetrieveByReviews(), "Deve tornare un customer");
    }
}
