public class User {
    private String name;
    private String password;

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}