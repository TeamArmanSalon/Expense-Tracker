import java.util.*;

public class ExpenseTracker {

    public static void main(String[] args) {
        launch();
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Transaction transaction = new Transaction();
    private static Add add = new Add(scanner, transaction);
    private static Account account = new Account();
    private static Extension extension = new Extension(transaction);


    private static void launch(){
        System.out.println("Welcome to Expense Tracker!");
        extension.registerUser();

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



    private static void showMenu() {
        System.out.println("- Expense Tracker -");
        System.out.println("1. Add");
        System.out.println("2. Records");
        System.out.println("3. Analysis");
        System.out.println("4. Budgets");
        System.out.println("5. Accounts");
        System.out.println("6. Categories");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> add();
            case 2 -> transaction.showRecords();
            case 3 -> System.out.println("Currently working on it...");
            case 4 -> System.out.println("Currently working on it...");
            case 5 -> System.out.println("Currently working on it...");
            case 6 -> System.out.println("Currently working on it....");
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
        transaction.addIncome("Income Entry", income, typeOfIncome);

        System.out.println("\nIncome added successfully!\n");

    }

}
