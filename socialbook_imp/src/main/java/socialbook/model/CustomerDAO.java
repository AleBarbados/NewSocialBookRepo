package socialbook.model;
import java.sql.*;
public class CustomerDAO {

    public final static String DO_RETRIEVE_BY_ID = "SELECT id_customer, customer_name, customer_surname, " +
            "                               customer_pwd, customer_usr, description FROM customer WHERE id=?";

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
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}