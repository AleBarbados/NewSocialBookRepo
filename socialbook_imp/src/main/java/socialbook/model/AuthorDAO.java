package socialbook.model;

import java.sql.*;
import java.util.ArrayList;

public class AuthorDAO {
    private final static String DO_SAVE_AUTHOR = "INSERT INTO author (author_name, author_surname) VALUES (?,?)";
    private final static String DO_SAVE_AUTHOR_ASSOCIATION = "INSERT INTO authorAssociation (id_author, ISBN) VALUES (?,?)";
    private final static String DO_RETRIEVE_IDAUTHOR_BY_ISBN = "SELECT id_author FROM authorAssociation WHERE ISBN = ?";
    private final static String DO_RETRIEVE_AUTHORS_BY_ISBN = "SELECT author.author_name, author.author_surname FROM author" +
            " JOIN authorassociation ON author.id_author=authorassociation.id_author WHERE ISBN=?";
    private final static String DO_RETRIEVE_IDAUTHOR_LIKE = "SELECT id_author FROM author WHERE author_name LIKE ? OR " +
            " author_surname LIKE ? LIMIT ?,?";

    public void doSave(ArrayList<Author> authors, String isbn) {
        try (Connection con = ConPool.getConnection()) {
            for (Author a: authors) {
                PreparedStatement ps = con.prepareStatement(DO_SAVE_AUTHOR, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, a.getName());
                ps.setString(2, a.getSurname());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                a.setId(id);

                ps = con.prepareStatement(DO_SAVE_AUTHOR_ASSOCIATION);
                ps.setInt(1,id);
                ps.setString(2,isbn);

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Author> doRetrieveAuthorsByIsbn(String isbn) {
        try (Connection con = ConPool.getConnection()) {
            ArrayList<Author> authors = new ArrayList<Author>();

            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_AUTHORS_BY_ISBN);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String surname = rs.getString(2);

                authors.add(new Author(name, surname));
            }
            return authors;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Integer> doRetrieveIdAuthorLike(String like, int offset, int limit) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_IDAUTHOR_LIKE);
            ps.setString(1, like+"%");
            ps.setString(2, like+"%");
            ps.setInt(3, offset);
            ps.setInt(4, limit);

            ArrayList<Integer> id = new ArrayList<>();

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
               id.add(rs.getInt(1));
            }
            return id;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int doRetrieveIdAuthorByIsbn(String isbn) {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_IDAUTHOR_BY_ISBN);
            ps.setString(1, isbn);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return rs.getInt(1);
            else
                return -1;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
