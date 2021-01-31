package socialbook.model;

import socialbook.Utility.AdminRole;

import java.util.Objects;

public class Admin {
    private String a_usr;
    private String a_pwd;
    private AdminRole a_role;

    public Admin() { }

    public Admin(String u, String p, AdminRole r) {
        a_usr = u;
        a_pwd = p;
        a_role = r;
    }

    public String getA_usr() { return a_usr; }

    public void setA_usr(String u) {
        a_usr = u;
    }

    public String getA_pwd() {
        return a_pwd;
    }

    public void setA_pwd(String p) {
        a_pwd = p;
    }

    public AdminRole getA_role() {
        return a_role;
    }

    public void setA_role(AdminRole r) {
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
