import java.util.*;

public class Transaction {
    private Account account;
    private User user;
    private final List<Record> records = new ArrayList<>();

    Transaction(Account account){
        this.account = account;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addIncome(double amount, String accountName) {
        if (account != null) {
            account.addAmount(accountName, amount);
        }

        records.add(new Record(amount, user.getName(), accountName, true, account.getCurrency()));
    }

    public boolean addExpense(String description, double amount, String category, String accountName) {
        if (account != null) {
            boolean success = account.subtractAmount(accountName, amount);
            if (!success) return false; // not enough balance
        }

        records.add(new Record(description, amount, category, user.getName(), accountName, false, account.getCurrency()));
        return true;
    }

    public boolean addTransfer(String fromAccount, String toAccount, double amount) {
        if (account == null) return false;

        boolean success = account.subtractAmount(fromAccount, amount);

        if (!success) return false;

        account.addAmount(toAccount, amount);

        Record r = new Record("No Description", amount, "No Category",
                user.getName(), fromAccount, false, account.getCurrency());

        r.isTransfer = true;
        r.toAccount = toAccount;

        records.add(r);

        return true;
    }


    public void showRecords() {
        System.out.println("\n--- All Transactions ---");
        System.out.printf("%-20s | %-10s | %-12s | %-20s | %-10s | %-8s%n",
                "Description", "Amount", "Category", "Account(s)", "User", "Type");
        System.out.println("----------------------------------------------------------------------------------------------------");

        List<String> tempDesc = null;

        for (Record r : records) {
            String type;
            if (r.isTransfer) {
                type = "Transfer";
            }
            else if (r.isIncome) {
                type = "Income";
                r.category = "No Category";
            }
            else {
                type = "Expense";
            }

            String newDesc;
            boolean isLong = false;

            if (type.equals("Income")) {
                newDesc = "No Description";
            }
            else if (r.description.length() > 20) {
                tempDesc = wrapWords(r.description, 20);
                newDesc = tempDesc.getFirst();
                isLong = true;
            }
            else {
                newDesc = r.description;
            }

            String accountDisplay;
            if (r.isTransfer) {
                accountDisplay = r.accountName + " -> " + r.toAccount;
            }
            else {
                accountDisplay = r.accountName;
            }

            System.out.printf("%-20s | %c%-9.2f | %-12s | %-20s | %-10s | %-8s%n",
                    newDesc, account.getCurrency(), r.amount, r.category, accountDisplay, r.userName, type);

            if (isLong) {
                for (int i = 1; i < tempDesc.size(); i++) {
                    System.out.printf("%-20s%n", tempDesc.get(i));
                }
            }
        }
        System.out.println();
    }


    public static List<String> wrapWords(String text, int width) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");

        StringBuilder current = new StringBuilder();

        for (String word : words) {
            if (current.length() + word.length() + 1 > width) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            }
            else {
                if (current.length() > 0) {
                    current.append(" ");
                }
                current.append(word);
            }
        }
        if (current.length() > 0) {
            lines.add(current.toString());
        }

        return lines;
    }
}