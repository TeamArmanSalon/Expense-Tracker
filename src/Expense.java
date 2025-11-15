import java.util.ArrayList;
import java.util.List;

public class Expense {
    private final List<String> expenseCategories;

    public Expense() {
        expenseCategories = new ArrayList<>();
        initializeCategories();
    }

    private void initializeCategories() {
        expenseCategories.add("Rental");
        expenseCategories.add("Bills");
        expenseCategories.add("Education");
        expenseCategories.add("Transportation");
        expenseCategories.add("Food");
        expenseCategories.add("Insurance");
    }

    public List<String> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(String category){
        expenseCategories.add(category);
    }
}