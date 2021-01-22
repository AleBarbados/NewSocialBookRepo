package socialbook.model;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class  Review {
    private int id_review;
    private String body;
    private String vote;
    private java.sql.Date date;
    private int id_customer;
    private String isbn;

    public Review(){ }

    public Review(String b, String v, java.sql.Date d, int ic, String is) {
       body = b;
       vote = v;
       date = d;
       id_customer = ic;
       isbn = is;
    }

    public int getId_review() {
        return id_review;
    }

    public void setId_review(int id_review) {
        this.id_review = id_review;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return id_review == review.id_review && Objects.equals(body, review.body) && Objects.equals(vote, review.vote) &&
                Objects.equals(date, review.date) && id_customer == review.id_customer && Objects.equals(isbn, review.isbn);
    }
}
