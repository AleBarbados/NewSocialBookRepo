package socialbook.model.GestioneDatabase;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {

    /*     id_order order_price invoice_addr cart order_date id_customer */

    private final static String DO_RETRIEVE_BY_ID = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_order = ?";
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_customer = ? AND cart = false";
    private final static String DO_DELETE = "DELETE FROM customerOrder WHERE id = ?";
    private final static String DO_RETRIEVE_BY_CART = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer FROM customerOrder " +
            " WHERE id_customer = ? AND cart = true";
    private final static String DO_UPDATE = "UPDATE customerOrder SET invoice_addr = ?, cart = ?, order_date = ? WHERE id_order = ?";


    public void doUpdate(Order o){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_UPDATE);
            ps.setString(1, o.getInvoice_addr());
            ps.setBoolean(2, false);
            ps.setDate(3, new Date(new java.util.Date().getTime()));
            ps.setInt(4, o.getId_order());
            if(ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Order> doRetrieveById(int id_order){
        try(Connection con = ConPool.getConnection()){
            ArrayList<Order> orders = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
            ps.setInt(1, id_order);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Order o = new Order();
                o.setId_order(rs.getInt(1));
                o.setOrder_price(rs.getInt(2));
                o.setInvoice_addr(rs.getString(3));
                o.setCart(rs.getBoolean(4));
                o.setDate(rs.getDate(5));
                o.setId_customer(rs.getInt(6));
                orders.add(o);
            }return orders;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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

    public Order doRetrieveByCart(int id_customer){
        try(Connection con = ConPool.getConnection()){
            Order o = new Order();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CART);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                o.setId_order(rs.getInt(1));
                o.setOrder_price(rs.getInt(2));
                o.setInvoice_addr(rs.getString(3));
                o.setCart(rs.getBoolean(4));
                o.setDate(rs.getDate(5));
                o.setId_customer(id_customer);
            }return o;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void doDelete(int id_order){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_DELETE);
            ps.setInt(1, id_order);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
