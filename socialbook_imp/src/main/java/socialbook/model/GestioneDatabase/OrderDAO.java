package socialbook.model.GestioneDatabase;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_customer = ? AND cart = false";
    private final static String DO_RETRIEVE_BY_CUSTOMER_CART = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer FROM customerOrder " +
            " WHERE id_customer = ? AND cart = true";
    private final static String DO_UPDATE = "UPDATE customerOrder SET invoice_addr = ?, cart = ?, order_date = ? WHERE id_order = ?";


    public void doUpdate(Order o){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_UPDATE);
            System.out.println("in update id ordine" + o.getId_order() + "id_customer" + o.getId_customer());
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
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER_CART);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Order o = new Order();
                o.setId_order(rs.getInt(1));
                o.setOrder_price(rs.getInt(2));
                o.setInvoice_addr(rs.getString(3));
                o.setCart(rs.getBoolean(4));
                o.setDate(rs.getDate(5));
                o.setId_customer(id_customer);
                return o;
            }return null;

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}