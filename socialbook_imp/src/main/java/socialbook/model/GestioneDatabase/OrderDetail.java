package socialbook.model.GestioneDatabase;

import java.util.HashMap;
import java.util.Map;

public class OrderDetail {
    private int id_order;
    private Map<String, String> book = new HashMap<>();
    public OrderDetail(){}
    public OrderDetail(int id_order, Map<String, String> book){
        this.id_order = id_order;
        this.book = book;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public Map<String, String> getBook() {

        return book;
    }

    public void addBook(String isbn, String title){

        book.put(isbn, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return id_order == that.id_order;
    }


    public void setBook(Map<String, String> book) {
        this.book = book;
    }
}
