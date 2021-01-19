package socialbook.model;

import socialbook.Utility.Utility;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


public class Customer {
    private int id_customer;
    private String c_name;
    private String c_surname;
    private String e_mail;
    private String c_pwd;
    private String c_usr;
    private String description;
    private static int last = 0;

    public Customer(){}
    public Customer(String c_name, String c_surname, String e_mail, String c_pwd, String c_usr, String description ){
        this.id_customer = last ++;
        this.c_name = c_name;
        this.c_surname = c_surname;
        this.e_mail = e_mail;
        this.c_pwd = Utility.encryptionSHA1(c_pwd);
        this.c_usr = c_usr;
        this.description = description;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_surname() {
        return c_surname;
    }

    public void setC_surname(String c_surname) {
        this.c_surname = c_surname;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getC_pwd() {
        return c_pwd;
    }

    public void setC_pwd(String c_pwd) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(c_pwd.getBytes(StandardCharsets.UTF_8));
            this.c_pwd = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getC_usr() {
        return c_usr;
    }

    public void setC_usr(String c_usr) {
        this.c_usr = c_usr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((e_mail == null) ? 0 : e_mail.hashCode());
        result = prime * result + id_customer;
        result = prime * result + ((c_name == null) ? 0 : c_name.hashCode());
        result = prime * result + ((c_pwd == null) ? 0 : c_pwd.hashCode());
        result = prime * result + ((c_usr == null) ? 0 : c_usr.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id_customer == customer.id_customer && Objects.equals(c_name, customer.c_name) && Objects.equals(c_surname, customer.c_surname) && Objects.equals(e_mail, customer.e_mail) && Objects.equals(c_pwd, customer.c_pwd) && Objects.equals(c_usr, customer.c_usr);
    }
}
