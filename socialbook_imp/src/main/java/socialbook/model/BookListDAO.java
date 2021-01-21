package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookListDAO {
    private final static String DO_RETRIVE_FROM_CUSTOMER= "SELECT booklist.id_booklist, booklist.booklist_name, booklist.favorite, booklist.image" +
            " FROM booklist JOIN booklistdetail ON booklist.id_booklist=booklistdetail.id_booklist WHERE booklistdetail.id_customer=?";

    public ArrayList<BookList> doRetriveFromCustomer(int id){
        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_FROM_CUSTOMER);

            ArrayList<BookList> bookLists = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            ps.setInt(1, id);
            while(rs.next()){
                BookList b = new BookList();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getInt(3));
                b.setImage(rs.getString(4));;
            }
            return bookLists;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
