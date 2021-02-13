package socialbook.model.GestioneDatabase;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.TimeZone;

public class ConPool {
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException {

        if (datasource == null) {

            Properties prop = new Properties();
            try {
                prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
                // System.out.print(prop);
            } catch (IOException io) {
                io.printStackTrace();
                return null;
            }

            PoolProperties p = new PoolProperties();
            p.setUrl( prop.getProperty("connection.url"));
            p.setDriverClassName(prop.getProperty("setDriverClassName"));
            p.setUsername(prop.getProperty("database.user"));
            p.setPassword(prop.getProperty("database.password"));
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }
}