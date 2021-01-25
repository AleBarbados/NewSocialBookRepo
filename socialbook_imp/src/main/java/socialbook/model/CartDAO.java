package socialbook.model;

import java.sql.*;

public class CartDAO {
    private final static String DO_SAVE_CUSTOMER_CART = "INSERT INTO customerorder (order_price, cart, id_customer) VALUES (?,?,?)";
    private final static String DO_SAVE_BOOK_CART = "INSERT INTO orderdetail (id_order, ISBN) VALUES (?,?)";
    //private final static String DO_UPDATE_CUSTOMER_CART = "UPDATE customerorder SET order_price = ? WHERE

    public void doSave(Cart c, int id) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE_CUSTOMER_CART, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, c.getPrice());
            ps.setBoolean(2, false);
            ps.setInt(3, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int id_cart = rs.getInt(1);
            c.setId_cart(id);

            Book b = c.getLastBook();
            doSaveBookCart(id_cart, b.getIsbn(), b.getPrice_cent());
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doSaveBookCart(int id, String isbn, int price) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE_BOOK_CART);
            ps.setInt(1, id);
            ps.setString(2, isbn);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
