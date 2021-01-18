package socialbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAO {
    private final String DO_SAVE_AUTHOR = "INSERT INTO author (author_name, author_surname) VALUES (?,?)";
    private final String DO_RETRIEVE_IDAUTHOR_BY_ISBN = "SELECT id_author FROM authorAssociation WHERE ISBN = ?";
    private final String DO_RETRIEVE_AUTHORS_BY_ISBN = "SELECT author_name, author_surname FROM author WHERE id_author = ?";

    public void doSave(ArrayList<Author> authors, String isbn) {
        try (Connection con = ConPool.getConnection()) {
            for (Author a: authors) {
                PreparedStatement ps = con.prepareStatement(DO_SAVE_AUTHOR);

                ps.setString(1, a.getName());
                ps.setString(2, a.getSurname());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                ps = con.prepareStatement("INSERT INTO authorAssociation (id_author, isbn) VALUES (?,?)");
                ps.setString(1,isbn);
                ps.setInt(2,returnMaxCodice());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int returnMaxCodice(){
        try (Connection con = ConPool.getConnection()) {
            int value = 0;

            PreparedStatement ps = con.prepareStatement("SELECT MAX(id_author) FROM author");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                value = rs.getInt(1);
            }
            return value;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Author> doRetrieveAuthorsByIsbn(String isbn) {
        try(Connection con = ConPool.getConnection()) {
            int id = doRetrieveIdAuthorByIsbn(isbn);

            if(id != -1) {
                PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_AUTHORS_BY_ISBN);
                ps.setInt(1, id);

                ArrayList<Author> authors = new ArrayList<Author>();

                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    String name = rs.getString(1);
                    String surname = rs.getString(2);

                    authors.add(new Author(name, surname));
                }
                return authors;
            } else
                return null;
        } catch(SQLException e) {
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
