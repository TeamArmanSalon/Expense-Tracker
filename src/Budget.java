import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Budget {

    private List<String> budgetCategories;
    private Map<String, Double> mapBudgets = new HashMap<>();

    public Budget() {
        this.budgetCategories = new ArrayList<>();
        initializeCategories();
    }

    private void initializeCategories() {
        budgetCategories.add("Bills");
        budgetCategories.add("Education");
        budgetCategories.add("Food");
        budgetCategories.add("Health");
    }

    public List<String> getBudgetCategories() {
        return budgetCategories;
    }

    public void addCategory(String category) {
        if (!budgetCategories.contains(category)) {
            budgetCategories.add(category);
        }
    }

    public void removeCategory(String category) {
        budgetCategories.remove(category);
        mapBudgets.remove(category);
    }

    public Map<String, Double> getMapBudgets() {
        return mapBudgets;
    }

    public void setBudget(String category, double amount) {
        if (budgetCategories.contains(category)) {
            mapBudgets.put(category, amount);
        } else {
            System.out.println("[ Category does not exist! Add it first. ]");
        }
    }

    public double getBudget(String category) {
        return mapBudgets.getOrDefault(category, 0.0);
    }

    public void deleteBudget(String category) {
        mapBudgets.remove(category);
    }

    public boolean hasBudget(String category) {
        return mapBudgets.containsKey(category);
    }

}
