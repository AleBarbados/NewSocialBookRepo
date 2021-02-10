package socialbook.setup;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.RunScript;
import socialbook.model.GestioneDatabase.ConPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

//singleton per il DB, lo inizializza, lo istanzia e lo pulisce
public class InitTestDb {


    public void initeDb() throws SQLException, FileNotFoundException {
        try (Connection conn = ConPool.getConnection()) {
            conn.createStatement().execute("drop all objects delete files");
            Path path = Paths.get("src", "test", "resources", "databaseTest.sql");
            String absolutePath = String.valueOf(path.toAbsolutePath());
            File file = new File(absolutePath);
            RunScript.execute(conn, new FileReader(file));
        }
    }

    public void destroyDb() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            con.createStatement().execute("drop all objects delete files");
        }
    }
}
