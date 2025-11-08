import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Account account;
    private User user;

    // Simple record structure for income/expense entries
    private static class Record {
        String name;
        double amount;
        String category;
        String userName;
        boolean isIncome;

        Record(String name, double amount, String category, String userName, boolean isIncome) {
            this.name = name;
            this.amount = amount;
            this.category = category;
            this.userName = userName;
            this.isIncome = isIncome;
        }
    }

    // List to store all transactions
    private List<Record> records = new ArrayList<>();

    // Account
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // User
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Add income
    public void addIncome(String name, double amount, String category) {
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
        }
        records.add(new Record(name, amount, category, user.getName(), true));
    }

    // Add expense
    public void addExpense(String name, double amount, String category) {
        if (account != null) {
            account.setBalance(account.getBalance() - amount);
        }
        records.add(new Record(name, amount, category, user.getName(), false));
    }

    // Display all records
    public void showRecords() {
        System.out.println("\n--- All Transactions ---");
        System.out.printf("%-12s | %-8s | %-10s | %-10s | %-8s%n", "Name", "Amount", "Category", "User Name", "Type");
        System.out.println("------------------------------------------------------------------");

        for (Record r : records) {
            String type = r.isIncome ? "Income" : "Expense";
            System.out.printf("%-12s | %-8.2f | %-10s | %-10s | %-8s%n",
                    r.name, r.amount, r.category, r.userName, type);
        }
    }

    public int size(){
        return records.size();
    }
}
