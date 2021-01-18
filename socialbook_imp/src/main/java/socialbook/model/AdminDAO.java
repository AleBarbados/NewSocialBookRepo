package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private final static String DO_RETRIEVE_BY_USR_E_PWD = "SELECT admn_usr, admn_pwd, admn_role FROM admin WHERE admn_usr = ?" +
            "AND admn_pwd = ?";

    public Admin doRetrieveByUsrEPwd(String u, String p) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_USR_E_PWD);
            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();
            if(rs != null) {
                Admin a = new Admin();

                a.setA_usr(rs.getString(1));
                a.setA_pwd(rs.getString(2));
                a.setA_role(rs.getString(3));

                return a;
            }
            return null;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
