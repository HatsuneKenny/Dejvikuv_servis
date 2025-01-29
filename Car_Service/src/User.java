public class User {
    private String userName;
    private String heslo;
    private Role role;

    public User(String userName, String heslo, Role role) {
        this.userName = userName;
        this.heslo = heslo;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName= userName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }
}
