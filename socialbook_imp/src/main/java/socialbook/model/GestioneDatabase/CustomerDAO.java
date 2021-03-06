package socialbook.model.GestioneDatabase;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {

    private final static String DO_RETRIEVE_BY_ID = "SELECT id_customer, customer_name, customer_surname, " +
            " customer_pwd, customer_usr, c_description, email, image FROM customer WHERE id_customer = ?";
    private final static String DO_RETRIEVE_ALL = "SELECT id_customer, customer_name, customer_surname, " +
            " customer_pwd, customer_usr, c_description, email, image FROM customer";
    private final static String DO_RETRIEVE_BY_EMAIL = "SELECT id_customer, customer_name, customer_surname, " +
            " customer_pwd, customer_usr, c_description, image FROM customer WHERE email = ?";
    private final static String DO_SAVE = "INSERT INTO customer(id_customer, customer_name, customer_surname, customer_pwd, customer_usr," +
            " email, c_description, image) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
    private final static String DO_RETRIEVE_BY_USERNAME = "SELECT id_customer,customer_name, customer_surname, " +
            " customer_pwd, email, c_description, image FROM customer WHERE customer_usr = ?";
    private final static String DO_UPDATE = "UPDATE customer SET customer_pwd=?, c_description=?, image=? WHERE id_customer=?";
    private final static String DO_DELETE_BY_ID = "DELETE FROM customer WHERE id_customer = ?";
    private final static String DO_RETRIEVE_BY_REVIEWS = "SELECT c.id_customer, c.customer_name, c.customer_surname FROM customer c, review r " +
            " WHERE c.id_customer = r.id_customer AND r.ISBN = ?";

    public Customer doRetriveById(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getInt(1));
                c.setC_name(rs.getString(2));
                c.setC_surname(rs.getString(3));
                c.setC_pwd(rs.getString(4));
                c.setC_usr(rs.getString(5));
                c.setDescription(rs.getString(6));
                c.setE_mail(rs.getString(7));

                String image = rs.getString(8);
                if(image != null)
                    c.setImage(image);

                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Customer> doRetrieveAll(){

        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_ALL);

            ArrayList<Customer> customers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                Customer c = new Customer();
                c.setId_customer(rs.getInt(1));
                c.setC_name(rs.getString(2));
                c.setC_surname(rs.getString(3));
                c.setC_pwd(rs.getString(4));
                c.setC_usr(rs.getString(5));
                c.setDescription(rs.getString(6));
                c.setE_mail(rs.getString(7));
                c.setImage(rs.getString(8));

                customers.add(c);
            }
            return customers;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Customer doRetriveByEmail(String email){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setE_mail(email);
                c.setId_customer(rs.getInt(1));
                c.setC_name(rs.getString(2));
                c.setC_surname(rs.getString(3));
                c.setC_pwd(rs.getString(4));
                c.setC_usr(rs.getString(5));
                c.setDescription(rs.getString(6));
                c.setImage(rs.getString(7));
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Customer customer){

        try(Connection c = ConPool.getConnection()){
            PreparedStatement ps =
                    c.prepareStatement(DO_SAVE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, customer.getId_customer());
            ps.setString(2, customer.getC_name());
            ps.setString(3, customer.getC_surname());
            ps.setString(4, customer.getC_pwd());
            ps.setString(5, customer.getC_usr());
            ps.setString(6, customer.getE_mail());
            ps.setString(7, customer.getDescription());
            ps.setString(8, customer.getImage());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id_customer = rs.getInt(1);
            customer.setId_customer(id_customer);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public boolean validate(String usr, String pwd){

        Customer customer = doRetrieveByUsername(usr);
        if(customer == null)
            return false;
        else
            return (customer.getC_pwd().equals(pwd));
    }

    public Customer doRetrieveByUsername(String username){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_USERNAME);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setC_usr(username);
                c.setId_customer(rs.getInt(1));
                c.setC_name(rs.getString(2));
                c.setC_surname(rs.getString(3));
                c.setC_pwd(rs.getString(4));
                c.setE_mail(rs.getString(5));
                c.setDescription(rs.getString(6));
                c.setImage(rs.getString(7));
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdate(Customer customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_UPDATE );
            ps.setString(1, customer.getC_pwd());
            ps.setString(2, customer.getDescription());
            ps.setString(3, customer.getImage());
            ps.setInt(4, customer.getId_customer());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
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

    public ArrayList<Customer> doRetrieveByReviews(String isbn) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_REVIEWS);
            ps.setString(1, isbn);

            ArrayList<Customer> customers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Customer c = new Customer();

                c.setId_customer(rs.getInt(1));
                c.setC_name(rs.getString(2));
                c.setC_surname(rs.getString(3));

                customers.add(c);
            }
            return customers;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}