package socialbook.model;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {

    public final static String DO_RETRIEVE_BY_ID = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, description, email FROM customer WHERE id = ?";
    public final static String DO_RETRIEVE_ALL = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, description, email FROM customer";
    public final static String DO_RETRIEVE_BY_EMAIL = "SELECT id_customer,customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, description FROM customer WHERE email = ?";

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

}