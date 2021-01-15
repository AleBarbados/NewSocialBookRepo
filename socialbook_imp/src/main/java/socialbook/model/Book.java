package socialbook.model;

import java.util.Objects;

public class Book {
    private String isbn;
    private String title;
    private String genre;
    private int price_cent;
    private int publication_year;
    private String publishing_house;
    private String plot;
    private boolean catalogue;
    private String price_euro;

    public Book() { }

    public Book(String i, String t, String g, int pc, int py, String ph, String pl, boolean c) {
        isbn = i;
        title = t;
        genre = g;
        price_cent = pc;
        publication_year = py;
        publishing_house = ph;
        plot = pl;
        catalogue = c;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String i) {
        isbn = i;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        title = t;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String g) {
        genre = g;
    }

    public int getPrice_cent() {
        return price_cent;
    }

    public void setPrice_cent(int pc) {
        price_cent = pc;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int py) {
        publication_year = py;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public void setPublishing_house(String ph) {
        publishing_house = ph;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String p) {
        plot = p;
    }

    public boolean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(boolean c) {
        catalogue = c;
    }

    public String getPrice_euro() {
        return price_euro;
    }

    public void setPrice_euro(String pe) {
        price_euro = String.format("% .2f", price_cent/100.00);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(genre, book.genre)
                && price_cent == book.price_cent && publication_year == book.publication_year && Objects.equals(publishing_house, book.publishing_house)
                && Objects.equals(plot, book.plot);
    }
}