package socialbook.setup;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.RunScript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

//singleton per il DB, lo inizializza, lo istanzia e lo pulisce
public class ConPool extends BasicDataSource {
  private static ConPool mockConnection = null;

  private ConPool() {
    super();
  }

  public static ConPool getInstance() {
    if (mockConnection == null) {
      mockConnection = new ConPool();
      mockConnection.setDriverClassName("org.h2.Driver");
      mockConnection.setUrl("jdbc:h2:./SocialBookTest;MODE=MYSQL;DATABASE_TO_UPPER=false;");
      mockConnection.setUsername("root");
      mockConnection.setPassword("root");
      mockConnection.setMaxTotal(512);
      mockConnection.setMaxIdle(512);
    }
    return mockConnection;
  }

  public void initeDb() throws SQLException, FileNotFoundException {
    mockConnection.getConnection().createStatement().execute("drop all objects delete files");
    Path path = Paths.get("src", "test", "resources", "databaseTest.sql");
    String absolutePath = String.valueOf(path.toAbsolutePath());
    File file = new File(absolutePath);
    RunScript.execute(mockConnection.getConnection(), new FileReader(file));
  }

  public void clearDb() throws SQLException {
    mockConnection.getConnection().createStatement().execute("drop all objects delete files");
    mockConnection.getConnection().close();
  }
}
