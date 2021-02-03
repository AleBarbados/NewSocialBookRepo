package socialbook.model.GestioneDatabase;

import socialbook.utility.BookAlreadyInsertException;

import java.sql.*;

public class CartDAO {
    private final static String DO_SAVE_CUSTOMER_CART = "INSERT INTO customerOrder ( order_price, cart, id_customer) VALUES (?,?,?)";
    private final static String DO_UPDATE_CUSTOMER_CART = "UPDATE customerOrder SET order_price = ? WHERE id_order = ?";
    private final static String DO_SAVE_BOOK_CART = "INSERT INTO orderDetail (id_order, ISBN) VALUES (?,?)";
    private final static String DO_DELETE_BOOK_CART = "DELETE  FROM orderDetail WHERE ISBN = ? AND id_order = ?;";
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_order, order_price, id_customer FROM customerOrder " +
            " WHERE id_customer = ? AND cart = true";

    public void doSave(Cart c, int id) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE_CUSTOMER_CART, Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1, c.getPrice());
            ps.setBoolean(2, true);
            ps.setInt(3, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int id_cart = rs.getInt(1);
            c.setId_cart(id_cart);

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateCustomerCart( Cart cart){
        try(Connection c = ConPool.getConnection()){

            PreparedStatement ps = c.prepareStatement(DO_UPDATE_CUSTOMER_CART);
            ps.setFloat(1, cart.getPrice());
            ps.setInt(2, cart.getId_cart());
            if(ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        }
        catch (SQLException e){
            System.out.println(e);

        }

    }

    public void doSaveBookCart(int id_order, String isbn) throws  BookAlreadyInsertException{
        try(Connection con = ConPool.getConnection()) {
            System.out.println("do save book cart id "+id_order+" isbn "+isbn);
            PreparedStatement ps = con.prepareStatement(DO_SAVE_BOOK_CART);
            ps.setInt(1, id_order);
            ps.setString(2, isbn);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        }catch (SQLIntegrityConstraintViolationException sicve){
            throw new BookAlreadyInsertException(sicve);
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doDeleteBookFromCart(int id, String isbn){
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BOOK_CART);
            ps.setInt(2, id);
            ps.setString(1, isbn);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cart doRetrieveByCustomer(int id_customer){
        try(Connection con = ConPool.getConnection()){
            Cart c = new Cart();

            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setId_cart(rs.getInt(1));
                System.out.println("set id cart "+ c.getId_cart());
                c.setPrice(rs.getFloat(2));
                c.setId_customer(id_customer);
                c.setBooks(new OrderDetailDAO().doRetrieveByOrder(c.getId_cart()));
            }return c;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
