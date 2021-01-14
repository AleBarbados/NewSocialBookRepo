package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public final static String DO_RETRIEVE = "SELECT admn_usr, admmn_pwd, adm_role, " +
            "FROM admin WHERE admn_usr=? AND admn_pwd=?";

    public Admin doRetrieve(String usr, String pwd) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE);
            ps.setString(1, usr);
            ps.setString(2, pwd);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin a = new Admin();

                a.setA_usr(rs.getString(1));
                a.setA_pwd(rs.getString(2));
                a.setA_role(rs.getString(3));

                return a;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
