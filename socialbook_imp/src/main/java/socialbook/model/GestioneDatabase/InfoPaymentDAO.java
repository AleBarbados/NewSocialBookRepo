package socialbook.model.GestioneDatabase;


import java.sql.*;
import java.util.Optional;

public class InfoPaymentDAO {
    private final static String DO_SAVE = "INSERT INTO infoPayment(id_customer, card_number, payment_name, payment_surname, " +
            "           exp_date_mm, exp_date_yy, cvv) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final static String DO_DELETE = "DELETE FROM infoPayment WHERE id_customer = ?";
    private final static String DO_RETRIEVE_BY_CUSTOMER = "SELECT id_customer, card_number, payment_name, payment_surname, " +
            " exp_date_mm, exp_date_yy, cvv FROM infoPayment WHERE id_customer = ?";

    public Optional<InfoPayment> doRetrieveByCustomer(int id_customer){
        try(Connection con = ConPool.getConnection()){
            InfoPayment i = new InfoPayment();
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CUSTOMER);
            ps.setInt(1, id_customer);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                i.setId_customer(rs.getInt(1));
                i.setCard_number(rs.getString(2));
                i.setPayment_name(rs.getString(3));
                i.setPayment_surname(rs.getString(4));
                i.setExp_month(rs.getString(5));
                i.setExp_year(rs.getString(6));
                i.setCvv(rs.getInt(7));
                return Optional.of(i);
            }
            return Optional.empty();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void doDeleteById(int id_customer){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_DELETE);
            ps.setInt(1, id_customer);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(InfoPayment i){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_SAVE);
            ps.setInt(1, i.getId_customer());
            ps.setString(2, i.getCard_number());
            ps.setString(3, i.getPayment_name());
            ps.setString(4, i.getPayment_surname());
            ps.setString(5, i.getExp_month());
            ps.setString(6, i.getExp_year());
            ps.setInt(7, i.getCvv());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}