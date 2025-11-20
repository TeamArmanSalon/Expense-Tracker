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

    public void showRecords() {
        System.out.println("\n--- All Transactions ---");
        System.out.printf("%-20s | %-10s | %-12s | %-10s | %-10s | %-8s%n",
                "Description", "Amount", "Category", "Account", "User", "Type");
        System.out.println("----------------------------------------------------------------------------------------------");
        List<String> tempDesc = null;
        for (Record r : records) {
            String type = r.isIncome ? "Income" : "Expense";
            String newDesc = "";
            boolean isExpenseDesc_IsGreaterThan = true; //means greater than 20 characters

            if(type.equals("Income")){
                r.category = "No Category";
                newDesc = "No Description";
                isExpenseDesc_IsGreaterThan = false;
            }
            else if(r.description.length() > 20){
                tempDesc = wrapWords(r.description, 20);
                newDesc = tempDesc.getFirst();
            }
            else{
                newDesc = r.description;
                isExpenseDesc_IsGreaterThan = false;
            }

            System.out.printf("%-20s | %c%-9.2f | %-12s | %-10s | %-10s | %-8s%n",
                    newDesc, account.getCurrency(), r.amount, r.category, r.accountName, r.userName, type);

            if(isExpenseDesc_IsGreaterThan){
                for (int i = 1; i < tempDesc.size(); i++) {
                    System.out.println(tempDesc.get(i));
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
            } else {
                if (current.length() > 0) current.append(" ");
                current.append(word);
            }
        }

        if (current.length() > 0) {
            lines.add(current.toString());
        }

        return lines;
    }
}