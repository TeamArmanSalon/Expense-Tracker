public class Record {
    public String description;
    public double amount;
    public String category;
    public String userName;
    public String accountName;
    public boolean isIncome;

    public Record(String description, double amount, String category,
                  String userName, String accountName, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.accountName = accountName;
        this.isIncome = isIncome;
    }

    public Record( double amount, String category,
                   String userName, String accountName, boolean isIncome) {
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.accountName = accountName;
        this.isIncome = isIncome;
    }

    public Record(String description, double amount, String category,
                  String userName, boolean isIncome) {
        this(description, amount, category, userName, "Unknown", isIncome);
    }
}