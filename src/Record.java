public class Record {
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