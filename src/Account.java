import java.util.*;

public class Account {

    private List<String> categoriesAccount;
    private Map<String, Double> accountBalances;

    public Account() {
        categoriesAccount = new ArrayList<>();
        accountBalances = new HashMap<>();
        initializeAccount();
    }

    private void initializeAccount() {
        addNewAccount("Cash");
        addNewAccount("Card");
        addNewAccount("Savings");
    }

    public boolean accountExists(String name) {
        return accountBalances.containsKey(name);
    }

    public void addNewAccount(String accountName) {
        if (!accountExists(accountName)) {
            categoriesAccount.add(accountName);
            accountBalances.put(accountName, 0.0);
        }
    }

    public List<String> getCategoriesAccount() {
        return categoriesAccount;
    }

    public void addAmount(String accountName, double amount) {
        if (accountExists(accountName)) {
            accountBalances.put(accountName, accountBalances.get(accountName) + amount);
        }
    }

    public boolean subtractAmount(String accountName, double amount) {
        if (accountExists(accountName)) {
            double current = accountBalances.get(accountName);
            if (current >= amount) {
                accountBalances.put(accountName, current - amount);
                return true;
            }
        }
        return false;
    }

    public Map<String, Double> getAllBalances() {
        return accountBalances;
    }
}