import java.util.ArrayList;
import java.util.List;

public class Account {
    private double balance;
    private String source;
    private List<String> categoriesAccount;

    public Account() {
        categoriesAccount = new ArrayList<>();
        initializeAccount();
    }

    public Account(String source, double initialBalance) {
        this.source = source;
        this.balance = initialBalance;
        categoriesAccount = new ArrayList<>();
        initializeAccount();
    }

    public void initializeAccount(){
        categoriesAccount.add("Cash");
        categoriesAccount.add("Card");
        categoriesAccount.add("Savings");
    }

    public List<String> getCategoriesAccount() {
        return categoriesAccount;
    }

    public void setCategoriesAccount(String category) {
        this.categoriesAccount.add(category);
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
