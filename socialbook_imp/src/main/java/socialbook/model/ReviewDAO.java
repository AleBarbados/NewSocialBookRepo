package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReviewDAO {
    private final static String DO_SAVE = "INSERT INTO review (id_review, id_customer, ISBN, review_date, body, vote) VALUES (?,?,?,?,?,?)";
    private final static String DO_DELETE_BY_ID = "DELETE FROM review WHERE id_review = ?";

    public void doSave(Review r) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE);

            ps.setInt(1, r.getId_review());
            ps.setInt(2, r.getId_customer());
            ps.setString(3, r.getIsbn());
            ps.setDate(4, r.getDate());
            ps.setString(5, r.getText());
            ps.setInt(6, r.getVote());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeleteById(int id) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BY_ID);
            ps.setInt(1, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
