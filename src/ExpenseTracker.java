import java.util.*;

public class ExpenseTracker {

    public static void main(String[] args) {
        launch();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Transaction records = new Transaction();
    private static User user;
    private static Expense expense;


    private static void launch(){
        System.out.println("Welcome to Expense Tracker!");
        registerUser();

        int choice;
        do {
            showMenu();
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            handleChoice(choice);
        } while (choice != 0);
    }

    private static void registerUser(){
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        user = new User(name, password);
        user.setAccount(new Account(0.0, name));
        records.setUser(user);
        records.setAccount(user.getAccount());

        System.out.println("\nAccount created successfully!\n");
    }

    private static void showMenu() {
        System.out.println("\n----- MENU -----");
        System.out.println("1. Add Expense");
        System.out.println("2. Add Income");
        System.out.println("3. Set Budget");
        System.out.println("4. View Budgets");
        System.out.println("5. View Report");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addExpense();
            case 2 -> addIncome();
            case 3 -> setBudget();
            case 4 -> viewBudgets();
            case 5 -> viewReport();
            case 0 -> System.out.println("Goodbye!");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addExpense() {
        expense = new Expense();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Here are some categories: ");
        for(String cat : expense.getCategory()){
            System.out.print(cat + " | ");
        }
        System.out.println(); //add new line

        System.out.print("Category: ");
        String category = scanner.nextLine();

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        Expense expense = new Expense(desc, amount);
        expense.setCategory(category);
        records.addExpense(desc, amount, category);

        System.out.println("Expense added successfully!");
    }

    private static void addIncome() {
        System.out.print("Enter income amount: ");
        double income = scanner.nextDouble();
        scanner.nextLine();

        Add add = new Add(income);
        records.addIncome("Income Entry", income, "General");

        System.out.println("Income added successfully!");
    }

    // Placeholder for future features
    private static void setBudget() {
        System.out.println("Budget setting feature coming soon...");
    }

    private static void viewBudgets() {
        System.out.println("View budgets feature coming soon...");
    }

    private static void viewReport() {
        System.out.println("\n--- Account Report ---");
        System.out.println("Name: " + user.getName());
        System.out.println("Balance: ₱" + user.getAccount().getBalance());
        System.out.println("Total Transactions: " + records.size());
        records.showRecords();
    }
}
