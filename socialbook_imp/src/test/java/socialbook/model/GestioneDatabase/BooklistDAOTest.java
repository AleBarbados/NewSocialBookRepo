package dao;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class TuristaDAOtest extends IntegrationTestCase {
    TableTuristaManager manager;

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
     * Testa la creazione, avvenuta tramite registrazione
     */
    @Test
    public void createTest1() throws SQLException {
        manager = new TuristaDAO(mockDb);
        Turista turista = new Turista("email", "password", "nome", "cognome");
        manager.create(turista);
        assertNotNull(manager.retrieveById(1), "Deve tornare un turista");
    }

    /**
     * testa il retrievebyid
     *
     * @throws SQLException
     */
    @Test
    public void retrieveByIdTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        assertNotNull(manager.retrieveById(1), "Deve tornare un turista");
    }

    /**
     * testa il retrieveAll che torna tutti i turisti
     *
     * @throws SQLException
     */
    @Test
    public void retrieveAllTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        List<Turista> lista = manager.retrieveAll();
        assertEquals(1, lista.size(), "Deve tornare 1");
    }

    /**
     * Testa l'aggiornamento di un turista
     *
     * @throws SQLException
     */
    @Test
    public void updateTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        Turista turista = manager.retrieveById(1);
        turista.setPassword("password");
        turista.setNome("Nome1");
        manager.update(turista);
        assertEquals("Nome1", manager.retrieveById(1).getNome(), "Deve ritornare Nome1");
    }

    /**
     * Testa la cancellazione di un turista
     *
     * @throws SQLException
     */
    @Test
    public void deleteTest() throws SQLException {
        manager = new TuristaDAO(mockDb);
        manager.delete(1);
        assertNull(manager.retrieveById(1), "Deve tornare null");
    }
}