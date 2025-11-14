public class Record {
    public String description;
    public double amount;
    public String category;
    public String userName;
    public boolean isIncome;

    public Record(String description, double amount, String category, String userName, boolean isIncome) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.isIncome = isIncome;
    }
}
