package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookListDAO {
    private final static String DO_RETRIVE_FROM_CUSTOMER = "SELECT booklist.id_booklist, booklist.booklist_name, booklist.favorite, booklist.image" +
            " FROM booklist JOIN booklistdetail ON booklist.id_booklist=booklistdetail.id_booklist WHERE booklistdetail.id_customer=? AND booklist.favorite<>1";
    private final static String DO_FOLLOW = "INSERT INTO booklistdetail(id_customer, id_booklist, property) VALUES(?,?,1)";
    private final static String DO_CREATE_DETAIL = "INSERT INTO booklistdetail(id_customer, id_booklist, property) VALUES(?,?,0)";
    private final static String DO_CREATE = "INSERT INTO booklist(id_booklist, boklist_name, favorite, image) VALUES(?,?,?,?)";
    private final static String DO_DELETE = "DELETE FROM booklistdetail WHERE id_customer=? AND id_booklist=?";

    public ArrayList<BookList> doRetriveFromCustomer(int id){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_FROM_CUSTOMER);

            ArrayList<BookList> bookLists = new ArrayList<>();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                BookList b = new BookList();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getBoolean(3));
                b.setImage(rs.getString(4));;
            }
            return bookLists;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doFollow(int id_customer, int id_booklist){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_FOLLOW);
            ps.setInt(1, id_customer);
            ps.setInt(2, id_booklist);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doDelete(int id_customer, int id_booklist){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_DELETE);
            ps.setInt(1, id_customer);
            ps.setInt(2, id_booklist);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
