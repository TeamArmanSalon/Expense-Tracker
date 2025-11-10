public class Account {
    private double balance;
    private String username;
    private double income;

    public Account(double balance) {
        this.balance = balance;
    }

    public Account(double balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

