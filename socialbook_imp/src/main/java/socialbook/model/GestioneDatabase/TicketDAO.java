package socialbook.model.GestioneDatabase;

import socialbook.utility.AdminRole;
import socialbook.utility.StatusEnumeration;

import java.sql.*;
import java.util.ArrayList;

public class TicketDAO {

    private final static String DO_RETRIEVE_BY_ROLE = "SELECT id_ticket, id_customer, admn_usr, open_date, issue, close_date , t_status " +
            " FROM ticket  WHERE destination = ? and admn_usr IS NULL  ";

    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_ticket, id_customer, admn_usr, open_date, issue, close_date , t_status, destination " +
            " FROM ticket  WHERE id_customer = ?";

    private final static String DO_RETRIEVE_BY_ID = "SELECT id_customer, admn_usr, open_date, issue, close_date , t_status, destination FROM ticket WHERE " +
            " id_ticket = ?";

    private final static String DO_DELETE_BY_ID = "DELETE FROM ticket WHERE id_ticket = ? ";

    private final static String DO_RETRIEVE_BY_ADMIN = "SELECT id_ticket, id_customer, open_date, issue, close_date , t_status, destination FROM ticket " +
            " WHERE admn_usr = ?";

    private final static String DO_SAVE = "INSERT INTO ticket(id_customer, admn_usr, open_date, issue, close_date , t_status, destination) VALUES(?, ?, ?, ?, ?, ?, ?) ";

    private final static String DO_UPDATE_TICKET = "UPDATE ticket SET admn_usr = ?, t_status = ?, close_date = ? WHERE " +
            " id_ticket = ?";

    public ArrayList<Ticket> doRetrieveByAdmin(String admn_usr) {
        try (Connection con = ConPool.getConnection()) {
            ArrayList<Ticket> tickets = new ArrayList<>();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ADMIN);
            ps.setString(1, admn_usr);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt(1));
                t.setId_customer(rs.getInt(2));
                t.setAdmn_usr(admn_usr);
                t.setOpen_date(rs.getDate(3));
                t.setIssue(rs.getString(4));
                t.setClose_date(rs.getDate(5));
                t.setStatus(StatusEnumeration.valueOf(rs.getString(6)));
                t.setDestination(AdminRole.valueOf(rs.getString(7)));
                tickets.add(t);
            }
            return tickets;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Ticket> doRetrieveByRole(AdminRole role) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ROLE);
            String s_role = role.name();
            ps.setString(1, s_role);
            ResultSet rs = ps.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt(1));
                t.setId_customer(rs.getInt(2));
                t.setAdmn_usr(rs.getString(3));
                t.setOpen_date(rs.getDate(4));
                t.setIssue(rs.getString(5));
                t.setClose_date(rs.getDate(6));
                t.setStatus(StatusEnumeration.valueOf(rs.getString(7)));
                t.setDestination(role);
                tickets.add(t);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Ticket> doRetrieveByCustomer(int id_customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            ArrayList<Ticket> tickets = new ArrayList<>();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setId_ticket(rs.getInt(1));
                t.setId_customer(rs.getInt(2));
                t.setAdmn_usr(rs.getString(3));
                t.setOpen_date(rs.getDate(4));
                t.setIssue(rs.getString(5));
                t.setClose_date(rs.getDate(6));
                t.setStatus(StatusEnumeration.valueOf(rs.getString(7)));
                t.setDestination(AdminRole.valueOf(rs.getString(8)));

                tickets.add(t);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Ticket doRetrieveById(int id_ticket) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
            ps.setInt(1, id_ticket);
            ResultSet rs = ps.executeQuery();
            Ticket ticket = new Ticket();
            if (rs.next()) {
                ticket.setId_ticket(id_ticket);
                ticket.setId_customer(rs.getInt(1));
                ticket.setAdmn_usr(rs.getString(2));
                ticket.setOpen_date(rs.getDate(3));
                ticket.setIssue(rs.getString(4));
                ticket.setClose_date(rs.getDate(5));
                ticket.setStatus(StatusEnumeration.valueOf(rs.getString(6)));
                ticket.setDestination(AdminRole.valueOf(rs.getString(7)));

            }
            return ticket;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doDeleteById(int id_ticket) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE_BY_ID);
            ps.setInt(1, id_ticket);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Ticket t) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getId_customer());
            ps.setString(2, t.getAdmn_usr());
            ps.setDate(3, new Date(new java.util.Date().getTime()));
            ps.setString(4, t.getIssue());
            ps.setDate(5, t.getClose_date());
            ps.setString(6, t.getStatus().name());
            ps.setString(7, t.getDestination().name());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            t.setId_ticket(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doUpdate(Ticket ticket) {
        try (Connection c = ConPool.getConnection()) {

            PreparedStatement ps = c.prepareStatement(DO_UPDATE_TICKET);
            System.out.println("status: " + ticket.getStatus().name());
            ps.setString(1, ticket.getAdmn_usr());
            ps.setString(2, ticket.getStatus().name());
            ps.setDate(3, ticket.getClose_date() == null ? null : new Date(ticket.getClose_date().getTime()));
            ps.setInt(4, ticket.getId_ticket());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
