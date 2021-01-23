package socialbook.model;

import java.sql.*;
import java.util.ArrayList;

public class ReviewDAO {
    private final static String DO_SAVE = "INSERT INTO review (id_customer, ISBN, review_date, body, vote) VALUES (?,?,?,?,?)";
    private final static String DO_DELETE_BY_ID = "DELETE FROM review WHERE id_review = ?";
    private final static String DO_RETRIEVE_BY_ID_CUSTOMER_E_ISBN = "SELECT body, vote FROM review WHERE ISBN = ? "
            + " AND id_customer = ?";
    private final static String DO_RETRIEVE_BY_ISBN = "SELECT id_review, id_customer, ISBN, review_date, body, vote FROM review WHERE ISBN = ?";

    public void doSave(Review r) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, r.getId_customer());
            ps.setString(2, r.getIsbn());
            ps.setDate(3, r.getDate());
            ps.setString(4, r.getBody());
            ps.setString(5, r.getVote());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int id = rs.getInt(1);
            r.setId_review(id);

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

    public ArrayList<Review> verifyByIsbnEIdCustomer(String isbn, int id_customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID_CUSTOMER_E_ISBN);
            ps.setString(1, isbn);
            ps.setInt(2, id_customer);

            ArrayList<Review> reviews = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Review r = new Review();

                r.setBody(rs.getString(1));
                r.setVote(rs.getString(2));

                reviews.add(r);
            }
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Review> doRetrieveByISBN(String isbn) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ISBN);
            ps.setString(1, isbn);

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

    private Review createReview(ResultSet rs) throws SQLException {
        Review r = new Review();

        r.setId_review(rs.getInt(1));
        r.setId_customer(rs.getInt(2));
        r.setIsbn(rs.getString(3));
        r.setDate(rs.getDate(4));
        r.setBody(rs.getString(5));
        r.setVote(rs.getString(6));

        return r;
    }
}
