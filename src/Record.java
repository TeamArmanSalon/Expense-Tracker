public class Record{
    public String description;
    public double amount;
    public String category;
    public String userName;
    public String accountName;
    public boolean isIncome;
    public char currency;

    public Record(String description, double amount, String category,
                  String userName, String accountName, boolean isIncome, char currency) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.accountName = accountName;
        this.isIncome = isIncome;
        this.currency = currency;
    }

    public Record( double amount, String category,
                   String userName, String accountName, boolean isIncome, char currency) {
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.accountName = accountName;
        this.isIncome = isIncome;
        this.currency = currency;
    }

    public Record(String description, double amount, String category,
                  String userName, boolean isIncome, char currency) {
        this(description, amount, category, userName, "Unknown", isIncome, currency);
    }

    @Override
    public String toString() {
        String type = isIncome ? "Income" : "Expense";
        if(description == null){
            description = "No Description";
        }
        return String.format("%s | %c%.2f | %s | %s | %s | %s",
                description,
                currency,
                amount,
                category,
                accountName,
                userName,
                type);
    }
}