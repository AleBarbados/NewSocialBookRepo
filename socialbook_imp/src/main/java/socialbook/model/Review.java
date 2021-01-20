package socialbook.model;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Review {
    private String text;
    private int id_review;
    private int vote;
    private java.sql.Date date;
    private int id_customer;
    private String isbn;


    public Review(String text, java.sql.Date date, int id, int vote){
        this.date=date;
        this.id_review=id_review;
        this.text=text;
        this.vote=vote;
        this.id_customer=id_customer;
        this.isbn=isbn;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return Objects.equals(text, review.text) && Objects.equals(date, review.date)
                && Objects.equals(isbn,review.isbn)
                && id_review == review.id_review && vote == review.vote && id_customer==review.id_customer;
    }
}
