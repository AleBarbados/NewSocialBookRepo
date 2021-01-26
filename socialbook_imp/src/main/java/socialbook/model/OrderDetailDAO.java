package socialbook.model;

import socialbook.Utility.AdminRole;
import socialbook.Utility.StatusEnumeration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAO {
    private static final String DO_RETRIEVE_BY_ORDER = "SELECT ISBN FROM orderDetail WHERE id_order = ?;";

    private ArrayList<Book> doRetrieveByOrder(int id_order){

        try(Connection con = ConPool.getConnection()){
        ArrayList<Book> books = new ArrayList<>();
        BookDAO bookDAO = new BookDAO();
        PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ORDER);
        ps.setInt(1, id_order);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String ISBN = rs.getString(1);
            Book b = bookDAO.doRetrieveByIsbn(ISBN);
            books.add(b);
        }return books;

    }catch(SQLException e){
        e.printStackTrace();
    }
        return null;}




}
