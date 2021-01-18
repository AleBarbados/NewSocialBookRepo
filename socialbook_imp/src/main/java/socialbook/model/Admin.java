package socialbook.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Admin {
    private String a_usr;
    private String a_pwd;
    private String a_role;

    public Admin() { }

    public Admin(String u, String p, String r) {
        a_usr = u;
        a_pwd = p;
        a_role = r;
    }

    public String getA_usr() {
        return a_usr;
    }

    public void setA_usr(String u) {
        a_usr = u;
    }

    public String getA_pwd() {
        return a_pwd;
    }

    public void setA_pwd(String p) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(a_pwd.getBytes(StandardCharsets.UTF_8));
            this.a_pwd = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getA_role() {
        return a_role;
    }

    public void setA_role(String r) {
        a_role = r;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(a_usr, admin.a_usr) && Objects.equals(a_pwd, admin.a_pwd) && Objects.equals(a_role, admin.a_role);
    }
}
