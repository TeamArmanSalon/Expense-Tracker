import java.util.Scanner;

public class Extension {
    private final Scanner scanner = new Scanner(System.in);
    private final Transaction transaction = new Transaction();
    private Account account = new Account();
    private User user;

    public void launch() {
        System.out.println("Welcome to Expense Tracker!");
        registerUser();

        int choice;
        while (true) {
            showMenu();
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input. Please Try again! ]\n");
                continue;
            }
            handleChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("\n- Expense Tracker -");
        System.out.println("1. Add Expense/Income");
        System.out.println("2. View Records");
        System.out.println("3. View Analysis");
        System.out.println("4. Set/View Budgets");
        System.out.println("5. Set/View Accounts");
        System.out.println("6. Set/View Categories");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addMenu();
            case 2 -> transaction.showRecords();
            case 3 -> System.out.println("Analysis feature coming soon...");
            case 4 -> System.out.println("Budget feature coming soon...");
            case 5 -> showAccount();
            case 6 -> System.out.println("Category management coming soon...");
            case 0 -> {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("[ Invalid option. Try again. ]");
        }
    }

    private void addMenu() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. Back");
            System.out.print("Choose option: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input. Please try again! ]");
                continue;
            }

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> addIncome();
                case 3 -> System.out.println("Returning to main menu...");
                default -> System.out.println("[ Invalid option! Try again. ]");
            }
        }
    }

    private void addExpense() {
        Expense expense = new Expense();

        System.out.println("Available Categories:");
        for (String cat : expense.getCategory()) {
            System.out.print(cat + " | ");
        }
        System.out.println();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        if (category.isEmpty()) category = "Uncategorized";

        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        if (desc.isEmpty()) desc = "No Description";

        double amount = 0;
        while (true) {
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                continue;
            }

            try {
                amount = Double.parseDouble(temp);
                if (amount <= 0) {
                    System.out.println("[ Amount must be greater than 0! ]");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! Please enter a valid number. ]");
            }
        }

        transaction.addExpense(desc, amount, category);
        System.out.println("\n[ Expense added successfully! ]\n");
    }

    private void addIncome() {
        System.out.print("Enter Source of Income: ");
        String source = scanner.nextLine();
        if (source.isEmpty()) source = "Unknown Source";

        double income = 0;
        while (true) {
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                continue;
            }

            try {
                income = Double.parseDouble(temp);
                if (income <= 0) {
                    System.out.println("[ Amount must be greater than 0! ]");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! Please enter a valid number. ]");
            }
        }

        account = new Account(source, income);
        account.setBalance(income);
        transaction.addIncome("Income Entry", income, source);

        System.out.println("\n[ Income added successfully! ]\n");
    }

    private void showAccount() {
        System.out.println("\n--- Account Info ---");
        if (user == null) {
            System.out.println("[ No user registered. ]");
            return;
        }
        System.out.println("Name: " + user.getName());
        System.out.println("Current Balance: " + account.getBalance());
        System.out.println("----------------------");
    }

    public void registerUser() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        while (!name.matches("[a-zA-Z ,.]+")) {
            System.out.println("[ Name must contain only letters, spaces, commas, or dots. ]");
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
        }

        String passwordRegex = "^(?=.{12,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$";
        String password;

        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("[ Password cannot be empty. ]");
            } else if (!password.matches(passwordRegex)) {
                System.out.println("[ Password must be at least 12 chars with uppercase, lowercase, number, and symbol. ]");
            } else {
                break;
            }
        }

        user = new User(name, password);
        transaction.setUser(user);

        System.out.println("\n[ Account created successfully! ]\n");
    }
}