package socialbook.model;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.TimeZone;

public class ConPool {
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException {
        /*
        Properties prop = new Properties();

        try {
            prop.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("/db/db.properties")));
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }
        */

        if (datasource == null) {
            PoolProperties p = new PoolProperties();
            /*p.setUrl("jdbc:mysql://" +prop.getProperty("server")+ ":" +prop.getProperty("porta")+ "/" +prop.getProperty("database")+ "?serverTimezone="
            + TimeZone.getDefault().getID()); */
            p.setUrl("jdbc:mysql://localhost:3306/SocialBook?serverTimezone=" + TimeZone.getDefault().getID());
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            //p.setUsername(prop.getProperty("utente"));
            //p.setPassword(prop.getProperty("password"));
            p.setUsername("root");
            p.setPassword("basedidati");
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