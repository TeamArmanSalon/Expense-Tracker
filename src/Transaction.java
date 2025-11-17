import java.util.*;

public class Transaction {
    private Account account;
    private User user;

    private final List<Record> records = new ArrayList<>();

    public List<Record> getRecords() {
        return records;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addIncome(double amount, String category, String accountName) {
        if (account != null) {
            account.addAmount(accountName, amount);
        }

        records.add(new Record(amount, category, user.getName(), accountName, true));
    }

    public boolean addExpense(String description, double amount, String category, String accountName) {
        if (account != null) {
            boolean success = account.subtractAmount(accountName, amount);
            if (!success) return false; // not enough balance
        }

        records.add(new Record(description, amount, category, user.getName(), accountName, false));
        return true;
    }

    public void showRecords() {
        System.out.println("\n--- All Transactions ---");
        System.out.printf("%-15s | %-10s | %-12s | %-10s | %-10s | %-8s%n",
                "Description", "Amount", "Category", "Account", "User", "Type");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (Record r : records) {
            String type = r.isIncome ? "Income" : "Expense";
            String setDesc = "";
            if(type.equals("Income")) {
                setDesc = "No Description";
            }
            else if(type.equals("Expense")) {
                setDesc = r.description;
            }
            System.out.printf("%-15s | %-10.2f%c | %-12s | %-10s | %-10s | %-8s%n",
                    setDesc, r.amount, account.getCurrency(), r.category, r.accountName, r.userName, type);
        }
        System.out.println();
    }
}