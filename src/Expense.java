import java.util.ArrayList;
import java.util.List;

public class Expense {
    private final List<String> category;

    public Expense() {
        category = new ArrayList<>();
        initializeCategories();
    }

    private void initializeCategories() {
        category.add("Rental");
        category.add("Bills");
        category.add("Education");
        category.add("Transportation");
        category.add("Food");
        category.add("Insurance");
    }

    public List<String> getCategory() {
        return category;
    }
}
