package socialbook.model;

import java.sql.*;
import java.util.ArrayList;

public class CartDAO {
    private final static String DO_SAVE_CUSTOMER_CART = "INSERT INTO customerOrder (order_price, cart, id_customer) VALUES (?,?,?)";
    private final static String DO_UPDATE_CUSTOMER_CART = "UPDATE customerOrder SET order_price = ? WHERE id_order = ?";
    private final static String DO_SAVE_BOOK_CART = "INSERT INTO orderDetail (id_order, ISBN) VALUES (?,?)";
    private final static String DO_DELETE_BOOK_CART = "DELETE  FROM orderDetail WHERE ISBN = ? AND id_order = ?;";
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_customer = ? AND cart = true";

    public void doSave(Cart c, int id) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE_CUSTOMER_CART, Statement.RETURN_GENERATED_KEYS);
            ps.setFloat(1, c.getPrice());
            ps.setBoolean(2, false);
            ps.setInt(3, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            int id_cart = rs.getInt(1);
            c.setId_cart(id);

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

    private void doSaveBookCart(int id, String isbn) {
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

    private void doDeleteBookFromCart(int id, String isbn){
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BOOK_CART);
            ps.setInt(2, id);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Order> doRetrieveByCustomer(int id_customer){
        try(Connection con = ConPool.getConnection()){
            ArrayList<Order> orders = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order o = new Order();
                o.setId_order(rs.getInt(1));
                o.setOrder_price(rs.getInt(2));
                o.setInvoice_addr(rs.getString(3));
                o.setCart(rs.getBoolean(4));
                o.setDate(rs.getDate(5));
                o.setId_customer(id_customer);
                orders.add(o);
            }return orders;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
