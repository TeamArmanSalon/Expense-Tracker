import java.util.Scanner;

public class User {
    private String name;
    private String password;
    private Account account;
    private Scanner scanner;
    private Transaction records;

    User(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

}