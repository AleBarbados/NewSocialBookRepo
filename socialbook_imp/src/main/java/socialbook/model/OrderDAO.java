package socialbook.model;

import socialbook.Utility.AdminRole;
import socialbook.Utility.StatusEnumeration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /*     id_order order_price invoice_addr cart order_date id_customer */

    private final static String DO_RETRIEVE_BY_ID = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_order = ?";
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_order, order_price, invoice_addr, cart, order_date, id_customer" +
            " FROM customerOrder WHERE id_customer = ? AND cart = false";
    private final static String DO_DELETE = "DELETE FROM customerOrder WHERE id = ?";


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
