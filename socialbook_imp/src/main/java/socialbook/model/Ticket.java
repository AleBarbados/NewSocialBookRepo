package socialbook.model;

import socialbook.Utility.StatusEnumeration;
import socialbook.Utility.Utility;

import java.util.Date;

public class Ticket {
    private int id_ticket;
    private int id_customer;
    private String admn_usr;
    private java.sql.Date open_date;
    private String issue;
    private java.sql.Date close_date;
    private StatusEnumeration status;

    public Ticket(){}
    public Ticket(String admn_usr, java.sql.Date open_date, String issue, java.sql.Date close_date, StatusEnumeration status){
        this.admn_usr = admn_usr;
        this.open_date = open_date;
        this.issue = issue;
        this.close_date = close_date;
        this.status = status;
    }

    public int getId_ticket(){return id_ticket; }

    public void setId_ticket(int id_ticket){ this.id_ticket = id_ticket; }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getAdmn_usr() {
        return admn_usr;
    }

    public void setAdmn_usr(String admn_usr) {
        this.admn_usr = admn_usr;
    }

    public java.sql.Date getOpen_date() {
        return open_date;
    }

    public String getStringOpen_date(){
        System.out.println(Utility.formatDate(open_date));
        return Utility.formatDate(open_date);
    }

    public void setOpen_date(java.sql.Date open_date) {
        this.open_date = open_date;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public java.sql.Date getClose_date() {
        return close_date;
    }

    public String getStringClose_date(){
        System.out.println(Utility.formatDate(close_date));
        return Utility.formatDate(close_date);
    }

    public void setClose_date(java.sql.Date close_date) {
        this.close_date = close_date;
    }

    public StatusEnumeration getStatus() {
        return status;
    }

    public void setStatus(StatusEnumeration status) {
        this.status = status;
    }
}
