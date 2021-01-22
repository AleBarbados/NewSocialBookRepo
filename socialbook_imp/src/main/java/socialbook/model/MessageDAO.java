package socialbook.model;

import java.sql.*;
import java.util.ArrayList;

public class MessageDAO {

    private final static String DO_RETRIEVE_BY_TICKET = "SELECT id_message, sender, time_stamp, message_body FROM  " +
            "                                            message WHERE id_ticket = ?";
    private final static String DO_RETRIEVE_BY_ID = "SELECT id_message, sender, time_stamp, message_body, id_ticket FROM  " +
            "                                        message WHERE id_message = ?";
    private final static String DO_SAVE = "INSERT INTO message(sender, time_stamp, message_body, id_ticket)" +
            " VALUES(?,?,?,?) ";
    private final static String DO_DELETE = "DELETE FROM message WHERE id = ?";

    public ArrayList<Message> doRetrieveByTicket(int id_ticket){
        try(Connection con = ConPool.getConnection()){
            ArrayList<Message> messages = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_TICKET );
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, id_ticket);
            while(rs.next()){
                Message m = new Message();
                m.setId_message(rs.getInt(1));
                m.setSender(rs.getBoolean(2));
                m.setTimestamp(rs.getTimestamp(3));
                m.setMessage_body(rs.getString(4));
                m.setId_ticket(id_ticket);
            }
            return messages;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Message> doRetrieveById(int id_message){
        try(Connection con = ConPool.getConnection()){
            ArrayList<Message> messages = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, id_message);
            while(rs.next()){
                Message m = new Message();
                m.setId_message(rs.getInt(1));
                m.setSender(rs.getBoolean(2));
                m.setTimestamp(rs.getTimestamp(3));
                m.setMessage_body(rs.getString(4));
                m.setId_ticket(rs.getInt(5));
            }
            return messages;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doSave(Message m){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_SAVE, Statement.RETURN_GENERATED_KEYS);
            ps.setBoolean(1,m.getSender());
            ps.setTimestamp(2, new Timestamp(new Date(new java.util.Date().getTime()).getTime()));
            ps.setString(3, m.getMessage_body());
            ps.setInt(4, m.getId_ticket());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            m.setId_message(id);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void DoDelete(int id_message){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_DELETE);
            ps.setInt(1, id_message);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
