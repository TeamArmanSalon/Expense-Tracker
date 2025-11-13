import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Account account;
    private User user;

    private final List<Record> records = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addIncome(String name, double amount, String category) {
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
        }
        records.add(new Record(name, amount, category, user.getName(), true));
    }

    public void addExpense(String name, double amount, String category) {
        if (account != null) {
            account.setBalance(account.getBalance() - amount);
        }
        records.add(new Record(name, amount, category, user.getName(), false));
    }

    public void showRecords() {
        System.out.println("\n--- All Transactions ---");
        System.out.printf("%-15s | %-10s | %-12s | %-10s | %-8s%n", "Description", "Amount", "Category", "User Name", "Type");
        System.out.println("--------------------------------------------------------------------------------");

        for (Record r : records) {
            String type = r.isIncome ? "Income" : "Expense";
            System.out.printf("%-15s | %-10.2f | %-12s | %-10s | %-8s%n",
                    r.name, r.amount, r.category, r.userName, type);
        }
    }
}
