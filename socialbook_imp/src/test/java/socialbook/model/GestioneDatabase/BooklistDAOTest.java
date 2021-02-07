package socialbook.model.GestioneDatabase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
<<<<<<< HEAD
/*
public class BooklistDAOTest extends IntegrationTest{
=======

/*public class BooklistDAOTest extends IntegrationTest{
>>>>>>> origin/develop

    @BeforeEach
    public void setup() throws FileNotFoundException, SQLException {
        super.setup();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        super.tearDown();
    }

<<<<<<< HEAD
    /**
     * Testa la creazione, avvenuta tramite registrazione

=======
>>>>>>> origin/develop
    @Test
    public void createTest1() throws SQLException {
        manager = new TuristaDAO(mockDb);
        Turista turista = new Turista("email", "password", "nome", "cognome");
        manager.create(turista);
        assertNotNull(manager.retrieveById(1), "Deve tornare un turista");
    }

<<<<<<< HEAD
    /**
     * testa il retrievebyid
     *
     * @throws SQLException

=======
>>>>>>> origin/develop
    @Test
    public void retrieveByIdTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        assertNotNull(manager.retrieveById(1), "Deve tornare un turista");
    }

<<<<<<< HEAD
    /**
     * testa il retrieveAll che torna tutti i turisti
     *
     * @throws SQLException
=======
>>>>>>> origin/develop

    @Test
    public void retrieveAllTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        List<Turista> lista = manager.retrieveAll();
        assertEquals(1, lista.size(), "Deve tornare 1");
    }

<<<<<<< HEAD
    /**
     * Testa l'aggiornamento di un turista
     *
     * @throws SQLException
=======
>>>>>>> origin/develop

    @Test
    public void updateTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        Turista turista = manager.retrieveById(1);
        turista.setPassword("password");
        turista.setNome("Nome1");
        manager.update(turista);
        assertEquals("Nome1", manager.retrieveById(1).getNome(), "Deve ritornare Nome1");
    }

<<<<<<< HEAD
    /**
     * Testa la cancellazione di un turista
     *
     * @throws SQLException

=======
>>>>>>> origin/develop
    @Test
    public void deleteTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        manager.delete(1);
        assertNull(manager.retrieveById(1), "Deve tornare null");
    }
}
*/