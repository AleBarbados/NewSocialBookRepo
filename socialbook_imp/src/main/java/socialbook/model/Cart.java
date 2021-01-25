package socialbook.model;

import java.util.ArrayList;

public class Cart {
    private int id_cart;
    private int price;
    private int id_customer;
    private ArrayList<Book> books;

    public Cart() { }

    public Cart(int id, int p) {
        id_customer = id;
        price = p;
        books = new ArrayList<>();
    }

    public int getId_cart() {
        return id_customer;
    }

    public void setId_cart(int id) {
        id_cart = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int p) {
        price = p;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id) {
        id_customer = id;
    }

    public void insert(Book b) {
        books.add(b);
        price += b.getPrice_cent();
    }

    public void remove(Book b) {
        books.remove(b);
        price -= b.getPrice_cent();
    }

    public boolean contains(Book b) {
        return books.contains(b);
    }

    public Book getLastBook() {
        return books.get(books.size()-1);
    }

    public String getPrice_euro() {
        return String.format("% .2f", price/100.00);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id_cart == cart.id_cart && price == cart.price && id_customer == cart.id_customer;
    }
}
