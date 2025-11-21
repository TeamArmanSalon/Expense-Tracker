public class Record{
    public String description;
    public double amount;
    public String category;
    public String userName;
    public String accountName;
    public boolean isIncome;
    public char currency;
    public boolean isTransfer;
    public String toAccount;

    //for expense
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

    //for Income
    public Record( double amount, String userName, String accountName, boolean isIncome, char currency) {
        this.amount = amount;
        this.userName = userName;
        this.accountName = accountName;
        this.isIncome = isIncome;
        this.currency = currency;
    }

    @Override
    public String toString() {
        String type = isIncome ? "Income" : "Expense";
        if(description == null){
            description = "No Description";
        }

        if(type.equals("Income")){
            return String.format("%s | %c%.2f | %s | %s | %s",
                    description, currency, amount, accountName, userName, type);
        }
        else{
            return String.format("%s | %c%.2f | %s | %s | %s | %s",
                    description, currency, amount, category, accountName, userName, type);
        }
    }
}