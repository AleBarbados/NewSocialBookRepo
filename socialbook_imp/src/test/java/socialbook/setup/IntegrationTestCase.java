package socialbook.setup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class IntegrationTestCase {
  protected static ConPool mockDb = ConPool.getInstance();
//inizializza e pulisce il DB per ogni dao
  @BeforeEach
  public void setup() throws SQLException, FileNotFoundException {
    System.out.println("Integration InitDB");
    mockDb.initeDb();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    System.out.println("Integration ClearDB");
    mockDb.clearDb();
  }
}
