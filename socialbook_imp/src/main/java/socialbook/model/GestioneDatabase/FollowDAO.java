package socialbook.model.GestioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowDAO {
    private final static String DO_FOLLOW = "INSERT INTO follow(id_customer, id_follower) VALUES(?,?)";
    private final static String DO_RETRIVE_ALL_FOLLOWERS = "SELECT id_customer, id_follower FROM follow WHERE  id_customer=?";
    private final static String DO_RETRIVE_ALL_FOLLOWED = "SELECT id_customer, id_follower FROM follow WHERE  id_follower=?";
    private final static String DO_DELETE = "DELETE FROM follow WHERE id_customer=? AND id_follower=?";

    public void doFollow(int customer, int follower) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_FOLLOW);
            ps.setInt(1, customer);
            ps.setInt(2, follower);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Follow> doRetriveAllFollowers(int customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_ALL_FOLLOWERS);
            ps.setInt(1, customer);
            ArrayList<Follow> followers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow f = new Follow();
                f.setCostumer(rs.getInt(1));
                f.setFollower(rs.getInt(2));
                followers.add(f);
            }
            return followers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Follow> doRetriveAllFollowed(int customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_ALL_FOLLOWED);
            ps.setInt(1, customer);
            ArrayList<Follow> followers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow f = new Follow();
                f.setCostumer(rs.getInt(1));
                f.setFollower(rs.getInt(2));
                followers.add(f);
            }
            return followers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkFollower (int customer, int follower){
        ArrayList<Follow> followers = doRetriveAllFollowers(customer);
        if(!followers.isEmpty()){
            for (Follow f:followers) {
                if(f.getFollower()==follower){
                    return true;
                }
            }
        }
        return false;
    }

    public void doDelete(int follower, int customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE);
            ps.setInt(customer, 1);
            ps.setInt(follower, 2);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}