package socialbook.model;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {

    public final static String DO_RETRIEVE_BY_ID = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, c_description, email FROM customer WHERE id_customer = ?";
    public final static String DO_RETRIEVE_ALL = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, c_description, email FROM customer";
    public final static String DO_RETRIEVE_BY_EMAIL = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, c_description FROM customer WHERE email = ?";
    public final static String DO_SAVE = "INSERT INTO customer(id_customer, customer_name, customer_surname, customer_pwd, customer_usr, email, c_description)" +
            "                             VALUES (?, ?, ?, ?, ?, ?, ?);";

    public final static String DO_RETRIEVE_BY_USERNAME = "SELECT id_customer,customer_name, customer_surname, " +
            "                               customer_pwd, email, c_description FROM customer WHERE customer_usr = ?";


    public final static String DO_UPDATE = "UPDATE customer SET customer_pwd=?, c_description=? WHERE id_customer=?";

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
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void doSave( Customer customer){

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
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }


        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public boolean validate(String usr, String pwd){

        Customer customer = doRetrieveByUsername(usr);
        System.out.println("validating pwd given:"+pwd+" stored:"+customer.getC_pwd());
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
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdate(Customer customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con
                    .prepareStatement(DO_UPDATE );
            ps.setString(1, customer.getC_pwd());
            ps.setString(2, customer.getDescription());
            ps.setInt(3, customer.getId_customer());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}