import java.util.*;

public class ExpenseTracker {

    public static void main(String[] args) {
        launch();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Transaction records = new Transaction();
    private static User user;
    private static Expense expense;
    private static Add add = new Add(scanner, records);


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

        String name;
        String password;

        System.out.print("Enter Name: ");
        name = scanner.nextLine();

        while(true){
            if(!name.matches("[a-z-A-Z ,.]+")){
                System.out.println("[ We only accept Name that contains letters, space, comma, and dot! ]");
                System.out.print("Enter Name: ");
                name = scanner.nextLine();
            }
            else{
                break;
            }
        }

        String regex = "^(?=.{12,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$";

        while(true){
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if(password.isEmpty()){
                System.out.println("[ Must not be empty, Please try again! ]");
            }
            else if(!password.matches(regex)){
                System.out.println("[ Password must be 12 chars, at least one lowercase," +
                        " one uppercase, one digit, and one symbol. Please Try again! ]");
            }
            else{
                break;
            }
        }


        user = new User(name, password);
        user.setAccount(new Account(0.0, name));
        records.setUser(user);
        records.setAccount(user.getAccount());

        System.out.println("\nAccount created successfully!\n");
    }

    private static void showMenu() {
        System.out.println("- Expense Tracker -");
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
            case 1 -> add.addExpense();
            case 2 -> addIncome();
            case 3 -> setBudget();
            case 4 -> viewBudgets();
            case 5 -> viewReport();
            case 0 -> System.out.println("Goodbye!");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addIncome() {
        System.out.print("Enter income amount: ");
        double income = scanner.nextDouble();
        scanner.nextLine();

        //Add add = new Add(income);
        records.addIncome("Income Entry", income, "General");

        System.out.println("Income added successfully!\n");
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
