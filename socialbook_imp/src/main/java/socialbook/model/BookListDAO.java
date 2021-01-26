package socialbook.model;

import java.sql.*;
import java.util.ArrayList;

public class BookListDAO {
    private final static String DO_RETRIVE_BOOKLIST="SELECT id_booklist, booklist_name, favorite, image FROM booklist WHERE id_booklist=?";
    private final static String DO_RETRIVE_FROM_CUSTOMER = "SELECT booklist.id_booklist, booklist.booklist_name, booklist.favorite, booklist.image" +
            " FROM booklist JOIN booklistdetail ON booklist.id_booklist=booklistdetail.id_booklist WHERE booklistdetail.id_customer=? AND booklist.favorite=0 AND booklistdetail.property=0";
    private final static String DO_FOLLOW = "INSERT INTO booklistdetail(id_customer, id_booklist, property) VALUES(?,?,1)";
    private final static String DO_SAVE_DETAIL = "INSERT INTO booklistdetail(id_customer, id_booklist, property) VALUES(?,?,0)";
    private final static String DO_SAVE = "INSERT INTO booklist(id_booklist, booklist_name, favorite, image) VALUES(?,?,?,?)";
    private final static String DO_SAVE_ASSOCIATION = "INSERT INTO booklistassociation(id_booklist, id_book) VALUES(?,?)";
    private final static String DO_DELETE = "DELETE FROM booklistdetail WHERE id_customer=? AND id_booklist=?";
    private final static String DO_RETRIVE_BOOKS = "SELECT book.ISBN, book.title, book.genre, book.price_cent, book.publication_year, book.publishing_house," +
            " book.plot, book.catalogue, book.image FROM book JOIN booklistassociation ON book.ISBN=booklistassociation.id_book WHERE booklistassociation.id_booklist=?";
    private final static String DO_RETRIVE_FROM_FOLLOW = "SELECT booklist.id_booklist, booklist.booklist_name, booklist.favorite, booklist.image" +
            " FROM booklist JOIN booklistdetail ON booklist.id_booklist=booklistdetail.id_booklist WHERE booklistdetail.id_customer=? AND booklistdetail.property=1";
    private final static String DO_UPDATE = "UPDATE booklist SET booklist_name = ? , image = ? WHERE booklist.id_booklist=?";
    private final static String DO_RETRIVE_FAVORITE = "SELECT booklist.id_booklist, booklist.booklist_name, booklist.favorite, booklist.image" +
            " FROM booklist JOIN booklistdetail ON booklist.id_booklist=booklistdetail.id_booklist WHERE booklistdetail.id_customer=? AND booklist.favorite=1 AND booklistdetail.property=0";

    public BookList doRetriveBooklist(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_BOOKLIST);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BookList b = new BookList();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getBoolean(3));
                b.setImage(rs.getString(4));
                return b;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<BookList> doRetriveFromCustomer(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_FROM_CUSTOMER);

            ArrayList<BookList> bookLists = new ArrayList<>();

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookList b = new BookList();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getBoolean(3));
                b.setImage(rs.getString(4));
                bookLists.add(b);
            }
            return bookLists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doFollow(int id_customer, int id_booklist) {
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

    public void doDelete(int id_customer, int id_booklist) {
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

    public ArrayList<Book> doRetriveBooks(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_BOOKS);

            ArrayList<Book> books = new ArrayList<>();

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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
                books.add(b);
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkFollower (int customer, int booklist){
        ArrayList<BookList> booklists = doRetriveFromFollower(customer);
        if(!booklists.isEmpty()){
            for (BookList b:booklists) {
                if(b.getId()==booklist){
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<BookList> doRetriveFromFollower(int customer) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_FROM_FOLLOW);

            ArrayList<BookList> bookLists = new ArrayList<>();

            ps.setInt(1, customer);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookList b = new BookList();
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getBoolean(3));
                b.setImage(rs.getString(4));
                bookLists.add(b);
            }
            return bookLists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(BookList bookList, int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, bookList.getId());
            ps.setString(2, bookList.getName());
            ps.setBoolean(3, bookList.getFavorite());
            ps.setString(4, bookList.getImage());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            PreparedStatement ps2 = con.prepareStatement(DO_SAVE_DETAIL);

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int id_customer = rs.getInt(1);

            ps2.setInt(1, id);
            ps2.setInt(2, id_customer);

            if (ps2.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSaveBook(int id_booklist, String ISBN_book){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_SAVE_ASSOCIATION);

            ps.setInt(1, id_booklist);
            ps.setString(2, ISBN_book);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdate(BookList booklist){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_UPDATE );
            ps.setString(1, booklist.getName());
            ps.setString(2, booklist.getImage());
            ps.setInt(3, booklist.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BookList doRetriveFavorite(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIVE_FAVORITE);

            BookList b = new BookList();

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b.setId(rs.getInt(1));
                b.setName(rs.getString(2));
                b.setFavorite(rs.getBoolean(3));
                b.setImage(rs.getString(4));
            }
            return b;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFavorite(int id, String ISBN){
        BookList bookList = doRetriveFavorite(id);

        doSaveBook(bookList.getId(), ISBN);
    }

}
