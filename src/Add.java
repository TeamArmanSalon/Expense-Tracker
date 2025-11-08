public class Add {
    private double expense;
    private double income;

    public Add(double expense, double income) {
        this.expense = expense;
        this.income = income;
    }
    public Add(double income){
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}
