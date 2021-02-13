package socialbook.model.GestioneDatabase;

import java.util.Objects;

public class InfoPayment {
    /*    card_number, payment_name, surname, exp_date_mm, exp_date_yy, cvv, id_customer 	*/
    private int id_customer;
    private String card_number;
    private String payment_name;
    private String payment_surname;
    private String exp_month;
    private String exp_year;
    private int cvv;

    public InfoPayment(){};
    public InfoPayment(int customer, String cn, String pn, String ps, String em, String ey, int cvv){
        this.id_customer = customer;
        this.card_number = cn;
        this.payment_name = pn;
        this.payment_surname = ps;
        this.exp_month = em;
        this.exp_year = ey;
        this.cvv = cvv;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getPayment_surname() {
        return payment_surname;
    }

    public void setPayment_surname(String payment_surname) {
        this.payment_surname = payment_surname;
    }

    public String getExp_month() {
        return exp_month;
    }

    public void setExp_month(String exp_month) {
        this.exp_month = exp_month;
    }

    public String getExp_year() {
        return exp_year;
    }

    public void setExp_year(String exp_year) {
        this.exp_year = exp_year;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoPayment that = (InfoPayment) o;
        return id_customer == that.id_customer ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_customer, card_number, payment_name, payment_surname, exp_month, exp_year, cvv);
    }
}
