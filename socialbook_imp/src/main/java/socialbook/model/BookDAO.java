package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO {
    private final static String DO_UPDATE_CATALOGUE = "UPDATE book SET catalogue = ? WHERE ISBN = ?";
    private final static String DO_UPDATE_PRICE = "UPDATE book SET price_cent = ? WHERE ISBN = ?";
    private final static String DO_SAVE = "INSERT INTO book (ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue) VALUES (?,?,?,?,?,?,?,?)";
    private final static String DO_RETRIEVE_ALL = "SELECT ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue FROM book";

    public void doUpdateCatalogue(String isbn, boolean b) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_UPDATE_CATALOGUE);
            ps.setBoolean(1, b);
            ps.setString(2, isbn);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdatePrice(String isbn, int p) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_UPDATE_PRICE);
            ps.setInt(1, p);
            ps.setString(2, isbn);

            if(ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Book b) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE);

            ps.setString(1, b.getIsbn());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getGenre());
            ps.setInt(4, b.getPrice_cent());
            ps.setInt(5, b.getPublication_year());
            ps.setString(6, b.getPublishing_house());
            ps.setString(7, b.getPlot());
            ps.setBoolean(8,b.getCatalogue());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Book> doRetrieveAll() {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_ALL);

            ArrayList<Book> books = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Book b = new Book();

                b.setIsbn(rs.getString(1));
                b.setTitle(rs.getString(2));
                b.setGenre(rs.getString(3));
                b.setPrice_cent(rs.getInt(4));
                b.setPublication_year(rs.getInt(5));
                b.setPublishing_house(rs.getString(6));
                b.setPlot(rs.getString(7));
                b.setCatalogue(rs.getBoolean(8));

                books.add(b);
            }
            return books;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
