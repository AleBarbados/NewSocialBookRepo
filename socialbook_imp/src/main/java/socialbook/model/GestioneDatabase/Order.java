package socialbook.model.GestioneDatabase;

import socialbook.utility.Utility;

import java.sql.Date;

public class Order {

    private int id_order;
    private int order_price;
    private String invoice_addr;
    private Boolean cart;
    private Date date;
    private int id_customer;

    public Order(){}
    public Order(int order_price, String addr, Boolean cart, Date date, int id_c){
        this.order_price = order_price;
        this.invoice_addr = addr;
        this.cart = cart;
        this.date = date;
        this.id_customer = id_c;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public String getStringPrice_euro() {
        return String.format("% .2f", order_price/100.00);
    }


    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public String getInvoice_addr() {
        return invoice_addr;
    }

    public void setInvoice_addr(String invoice_addr) {
        this.invoice_addr = invoice_addr;
    }

    public Boolean getCart() {
        return cart;
    }

    public void setCart(Boolean cart) {
        this.cart = cart;
    }

    public Date getDate() {
        return date;
    }
    public String getStringDate(){
        System.out.println(Utility.formatDate(date));
        return Utility.formatDate(date);
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
}
