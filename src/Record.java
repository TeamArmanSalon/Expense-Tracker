public class Record {
    public String name;
    public double amount;
    public String category;
    public String userName;
    public boolean isIncome;

    public Record(String name, double amount, String category, String userName, boolean isIncome) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.userName = userName;
        this.isIncome = isIncome;
    }
}
