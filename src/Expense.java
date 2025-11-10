import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String description;
    private List<String> category;
    private double amount;
    private double expense;

    Expense(String description, double amount){
        this.description = description;
        this.amount = amount;
        this.category = new ArrayList<>();
        initializeCat();
    }

    //Overload Cons
    Expense(){
        this.category = new ArrayList<>();
        initializeCat();
    }

    void initializeCat(){
        category.add("Rental");
        category.add("Bills");
        category.add("Education");
        category.add("Transportation");
        category.add("Food");
        category.add("Insurance");
    }

    //Expense
    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }


    //Cat
    public List<String> getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category.add(category);
    }

    //Desc
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}