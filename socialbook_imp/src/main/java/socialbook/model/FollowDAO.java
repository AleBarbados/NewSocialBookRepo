package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FollowDAO {
    public final static String DO_FOLLOW = "INSERT INTO follow(id_customer, id_follower) VALUES(?,?)";
    public final static String DO_RETRIVE_ALL_FOLLOWERS = "SELECT id_customer, id_follower FROM follow WHERE id_customer=?";
    public final static String DO_RETRIVE_ALL_FOLLOWED = "SELECT id_customer, id_follower FROM follow WHERE id_follower=?";
    public void doFollow(int customer, int follower) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_FOLLOW);
            ps.setInt(customer, 1);
            ps.setInt(customer, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Follow> doRetriveAllFollowers(int customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_ALL_FOLLOWERS);
            ps.setInt(customer, 1);
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

    public ArrayList<Follow> doRetriveAllFollowed(int follower) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_ALL_FOLLOWED);
            ps.setInt(follower, 1);
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
}