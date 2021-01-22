package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReviewDAO {
    private final static String DO_SAVE = "INSERT INTO review (id_review, id_customer, ISBN, review_date, body, vote) VALUES (?,?,?,?,?,?)";
    private final static String DO_DELETE_BY_ID = "DELETE FROM review WHERE id_review = ?";
    private final static String DO_RETRIEVE_BY_ID = "SELECT id_review, id_customer, ISBN, review_date, body, vote FROM review WHERE id_review = ?";
    private final static String DO_RETRIEVE_BY_ISBN = "SELECT id_review, id_customer, ISBN, review_date, body, vote FROM review WHERE ISBN = ?";
    private final static String DO_RETRIEVE_ALL = "SELECT id_review, id_customer, ISBN, review_date, body, vote FROM review";

    public void doSave(Review r) {
        try (Connection con = ConPool.getConnection()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeleteById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BY_ID);
            ps.setInt(1, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Review doRetrieveById(int id_review) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
            ps.setInt(1, id_review);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createReview(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Review createReview(ResultSet rs) throws SQLException {
        Review r = new Review();

        r.setId_review(rs.getInt(1));
        r.setId_customer(rs.getInt(2));
        r.setIsbn(rs.getString(3));
        r.setDate(rs.getDate(4));
        r.setText(rs.getString(5));
        r.setVote(rs.getInt(6));

        return r;
    }

    public ArrayList<Review> doRetrieveByISBN(String isbn) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ISBN);

            ArrayList<Review> reviews = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(createReview(rs));
            }
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Review> doRetrieveAll() {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_ALL);

            ArrayList<Review> reviews = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                reviews.add(createReview(rs));
            }
            return reviews;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
