public class Account {
    private double balance;
    private String username;
    private double income;
    private String types;

    public Account( String types, double income) {
        this.balance = income;
        this.types = types;
    }

    public Account(){
        //NOthing
    }

    public Account(double balance, String username) {
        this.balance = balance;
        this.username = username;
    }

    public Account(double balance, String username, String types) {
        this.balance = balance;
        this.username = username;
        this.types = types;
    }

    //Types

    public void setTypes(String types){
        this.types = types;
    }

    public String getTypes(){
        return this.types;
    }

    //Income

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    //Balance

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //User Name

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

