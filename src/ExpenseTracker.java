import java.util.*;

public class ExpenseTracker {

    public static void main(String[] args) {
        launch();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Transaction records = new Transaction();
    private static User user;
    private static Add add = new Add(scanner, records);
    private static Account account = new Account();


    private static void launch(){
        System.out.println("Welcome to Expense Tracker!");
        registerUser();

        int choice;
        while(true){
            showMenu();
            String temp = scanner.nextLine();

            if(temp.isEmpty()){ //check if empty
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try{
                choice = Integer.parseInt(temp); //convert to int
            }catch (Exception e){
                System.out.println("\n[ Invalid input. Please Try again! ]\n");
                continue;
            }
            handleChoice(choice);
        }
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
        records.setUser(user);

        System.out.println("\nAccount created successfully!\n");
    }



    private static void showMenu() {
        System.out.println("- Expense Tracker -");
        System.out.println("1. Add");
        System.out.println("2. Set Budget");
        System.out.println("3. View Budgets");
        System.out.println("4. View Report");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> add();
            case 2 -> setBudget();
            case 3 -> viewBudgets();
            case 4 -> viewReport();
            case 0 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void add(){
        int choice = 0;
        while(choice != 3){
            System.out.println("1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. Back");
            System.out.print("Choose option: ");
            String temp = scanner.nextLine();

            try{
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, Please try Again!");
            }
            switch (choice){
                case 1 -> add.addExpense();
                case 2 -> addIncome();
                case 3 -> System.out.println("...");
            }
        }
    }

    public static void addIncome(){
        System.out.print("Enter What type of Income: ");
        String typeOfIncome = scanner.nextLine();

        double income;
        while(true){
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if(temp.isEmpty()){
                System.out.println("[ Must not be empty ]");
                continue;
            }

            try{
                income = Double.parseDouble(temp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input, Please Try again! ]");
            }
        }

        account = new Account(typeOfIncome, income);
        account.setBalance(income);
        records.addIncome("Income Entry", income, typeOfIncome);

        System.out.println("\nIncome added successfully!\n");

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
        System.out.println("Balance: ₱" + account.getBalance()); //Temp
        System.out.println("Total Transactions: " + records.size());
        records.showRecords();
    }
}
