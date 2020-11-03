package socialBook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BookListDAO {

    private final static String DO_RETRIEVE_BY_UTENTE ="SELECT id_libro FROM booklist WHERE id_utente = ? AND creatore = ? ";
    private final static String DO_SAVE = "insert into booklist(id_utente,id_libro) values(?,?);";

    public Set<Integer> doRetrieveByUtente(int id_utente, boolean creatore){
        PreparedStatement ps = null;

        Set<Integer> libri = new HashSet<>();

        try (Connection conn = ConPool.getConnection();) {
            ps = conn.prepareStatement(DO_RETRIEVE_BY_UTENTE);
            ps.setInt(1, id_utente);
            ps.setBoolean(2, creatore);


            ResultSet result = ps.executeQuery();
            while(result.next()){
                libri.add(result.getInt("id_libro"));
            }
            return libri;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_SET;

        }
    }
    public synchronized int doSave(int id_utente, int id_libro) {

        PreparedStatement ps = null;
        try (Connection conn = ConPool.getConnection()) {

            ps = conn.prepareStatement(DO_SAVE);
            ps.setInt(1, id_utente);
            ps.setInt(2, id_libro);
            int rs = ps.executeUpdate();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
