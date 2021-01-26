package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO {
    private final static String DO_UPDATE_CATALOGUE = "UPDATE book SET catalogue = ? WHERE ISBN = ?";
    private final static String DO_UPDATE_PRICE = "UPDATE book SET price_cent = ? WHERE ISBN = ?";
    private final static String DO_SAVE = "INSERT INTO book (ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue, "
            + " image) VALUES (?,?,?,?,?,?,?,?,?)";
    private final static String DO_RETRIEVE_ALL = "SELECT ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue, "
            + " image FROM book";
    private final static String DO_RETRIEVE_CATALOGUE_BY_ISBN = "SELECT catalogue FROM book WHERE ISBN = ?";
    private final static String DO_RETRIEVE_BY_ISBN = "SELECT ISBN, title, genre, price_cent, publication_year, publishing_house, plot, catalogue, "
            + " image FROM book WHERE ISBN = ?";
    private final static String DO_RETRIEVE_BY_TITLE_OR_GENRE = "SELECT ISBN, title, genre, price_cent, publication_year, publishing_house, "
            + " plot, catalogue, image FROM book WHERE title LIKE ? OR genre LIKE ? LIMIT ?,?";
    private final static String DO_RETRIEVE_BY_TITLE = "SELECT ISBN, title, genre, price_cent, publication_year, publishing_house, "
            + " plot, catalogue, image FROM book WHERE title LIKE ? LIMIT ?,?";
    private final static String DO_RETRIEVE_BY_IDAUTHOR = "SELECT b.ISBN, b.title, b.genre, b.price_cent, b.publication_year, b.publishing_house, "
            + " b.plot, b.catalogue, b.image FROM book b,authorAssociation a WHERE a.id_author = ? AND b.ISBN = a.ISBN";

    public void doUpdateCatalogue(String isbn) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_UPDATE_CATALOGUE);
            ps.setBoolean(1, !doRetrieveCatalogueByIsbn(isbn));
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
            ps.setString(9, b.getImage());

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
                books.add(createBook(rs));
            }
            return books;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book doRetrieveByIsbn(String isbn) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ISBN);
            ps.setString(1, isbn);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return createBook(rs);
            }
            return null;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Book> doRetrieveByTitleOrGenre(String like, int offset, int limit) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_TITLE_OR_GENRE);
            ps.setString(1, like+"%");
            ps.setString(2, like+"%");
            ps.setInt(3, offset);
            ps.setInt(4, limit);

            ArrayList<Book> books = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                books.add(createBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Book> doRetrieveByTitle(String like, int offset, int limit) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_TITLE);

            ps.setString(1, like+"%");
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            ArrayList<Book> books = new ArrayList<>();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               books.add(createBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Book> doRetrieveByIdAuthor(int id) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_IDAUTHOR);
            ps.setInt(1, id);

            ArrayList<Book> books = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                books.add(createBook(rs));
            }
            return books;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Book createBook(ResultSet rs) throws SQLException {
        Book b = new Book();

        b.setIsbn(rs.getString(1));
        b.setTitle(rs.getString(2));
        b.setGenre(rs.getString(3));
        b.setPrice_cent(rs.getInt(4));
        b.setPublication_year(rs.getInt(5));
        b.setPublishing_house(rs.getString(6));
        b.setPlot(rs.getString(7));
        b.setCatalogue(rs.getBoolean(8));
        b.setImage(rs.getString(9));

        return b;
    }

    private boolean doRetrieveCatalogueByIsbn(String isbn) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_CATALOGUE_BY_ISBN);
            ps.setString(1, isbn);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBoolean(1);
            }
            return false;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
