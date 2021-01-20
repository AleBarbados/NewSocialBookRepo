package socialbook.model;

import socialbook.Utility.AdminRole;
import socialbook.Utility.StatusEnumeration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TicketDAO {

    private final static String DO_RETRIEVE_BY_ROLE = "SELECT ticket.id_ticket, ticket.id_customer, ticket.admn_usr, ticket.open_date, ticket.issue, ticket.close_date , ticket.status " +
            " FROM ticket , admin WHERE ticket.admn_usr = admin.admn_usr AND admin.role = ?";

    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_ ticket, id_customer, admn_usr, open_date, issue, close_date , status " +
            "             FROM ticket  WHERE id_customer = ?";

    private final static String DO_DELETE_BY_ID = "DELETE FROM ticket WHERE id_ticket = ? ";

    private final static String DO_SAVE = "INSERT INTO ticket(id_customer, admn_usr, open_date, issue, close_date , status) VALUES(?, ?, ?, ?, ?, ?) ";

    public ArrayList<Ticket> doRetrieveByRole( AdminRole role){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ROLE);
            String s_role = role.name();
            ps.setString(1, s_role);
            ResultSet rs = ps.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while(rs.next()){
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt(1));
                t.setId_customer(rs.getInt(2));
                t.setAdmn_usr(rs.getString(3));
                t.setOpen_date(rs.getDate(4));
                t.setIssue(rs.getString(5));
                t.setClose_date(rs.getDate(6));
                t.setStatus(StatusEnumeration.valueOf(rs.getString(7)));
                tickets.add(t);
            }
        return tickets;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Ticket> doRetrieveByCustomer( int id_customer){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while(rs.next()){
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt(1));
                t.setId_customer(rs.getInt(2));
                t.setAdmn_usr(rs.getString(3));
                t.setOpen_date(rs.getDate(4));
                t.setIssue(rs.getString(5));
                t.setClose_date(rs.getDate(6));
                t.setStatus(StatusEnumeration.valueOf(rs.getString(7)));
                tickets.add(t);
            }
            return tickets;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doDeleteById(int id_ticket){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BY_ID);
            ps.setInt(1, id_ticket);
            ResultSet rs = ps.executeQuery();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doSave(Ticket t){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_SAVE);
            ps.setInt(1, t.getId_customer());
            ps.setString(2, t.getAdmn_usr());
            ps.setDate(3, t.getOpen_date());
            ps.setString(4, t.getIssue());
            ps.setDate(5, t.getClose_date());
            ps.setString(6, t.getStatus().name());
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
