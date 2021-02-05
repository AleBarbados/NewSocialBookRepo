package socialbook.model.GestioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private static final String DO_RETRIEVE_BY_ORDER = "SELECT od.ISBN, b.title FROM orderDetail od, book b WHERE od.ISBN = b.ISBN AND od.id_order = ?;";

    public List<OrderDetail> doRetrieveByCustomer(int id_customer){
            List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
            List<Order> orders = new OrderDAO().doRetrieveByCustomer(id_customer);

            for (Order order: orders) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId_order(order.getId_order());
            List<Book> books = doRetrieveByOrder(order.getId_order());
            books.stream().forEach(book -> orderDetail.addBook(book.getIsbn(), book.getTitle()));
            orderDetails.add(orderDetail);
            }

            return orderDetails;
    }

    public ArrayList<Book> doRetrieveByOrder(int id_order){

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
        }

        return books;

    }catch(SQLException e){
        e.printStackTrace();
    }
        return null;}




}
