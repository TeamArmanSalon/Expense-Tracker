import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Extension {
    private final Scanner scanner = new Scanner(System.in);
    private final Transaction transaction = new Transaction();
    private Account account = new Account();
    Expense expense = new Expense();
    private User user;

    public void launch() {
        System.out.println("Welcome to Expense Tracker!");
        registerUser();

        int choice;
        while (true) {
            mainMenu();
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

    private void mainMenu() {
        System.out.println("- Expense Tracker -");
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
            case 1 -> addExpenseAndIncome();
            case 2 -> transaction.showRecords();
            case 3 -> viewAnalysis();
            case 4 -> System.out.println("Budget feature coming soon...");
            case 5 -> showAccountMenu();
            case 6 -> System.out.println("Category management coming soon...");
            case 0 -> {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("[ Invalid option. Try again. ]");
        }
    }

    private void addExpenseAndIncome() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("1. Add Expense");
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
                case 3 -> System.out.println("Returning to main menu...\n");
                default -> System.out.println("[ Invalid option! Try again. ]");
            }
        }
    }

    private void addExpense() {

        System.out.println("Available Accounts:");
        for (String acc : account.getCategoriesAccount()) {
            System.out.print(acc + " | ");
        }
        System.out.println();

        System.out.print("Select Account: ");
        String accountName = scanner.nextLine();
        if (!account.getCategoriesAccount().contains(accountName)) {
            System.out.println("[ Account does not exist! Expense cancelled. ]");
            return;
        }

        System.out.println("Available Expense Categories:");
        for (String cat : expense.getExpenseCategories()) {
            System.out.print(cat + " | ");
        }
        System.out.println();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        if (category.isEmpty()) category = "Uncategorized";

        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        if (desc.isEmpty()) desc = "No Description";

        double amount;
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
                System.out.println("[ Invalid input! Enter a number. ]");
            }
        }

        if (!expense.getExpenseCategories().contains(category)) {
            expense.setExpenseCategories(category);
        }

        boolean success = transaction.addExpense(desc, amount, category, accountName);

        if (!success) {
            System.out.println("[ Not enough balance in this account! ]");
            return;
        }

        System.out.println("\n[ Expense added successfully! ]\n");
    }


    private void addIncome() {
        System.out.println("Available Accounts: ");
        for (String acc : account.getCategoriesAccount()) {
            System.out.print(acc + " | ");
        }
        System.out.println();

        System.out.print("Enter Source of Income: ");
        String source = scanner.nextLine().trim();
        if (source.isEmpty()) source = "Unknown Source";

        if(!account.getCategoriesAccount().contains(source)){
            account.addNewAccount(source);
        }

        double income;
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

        account.addAmount(source, income);

        transaction.addIncome("Income Entry", income, source,  source);

        System.out.println("\n[ Income added successfully to " + source + "! ]\n");
    }


    private void showAccountMenu() {
        int choice = -1;

        while (choice != 4) {
            System.out.println("1. View Accounts");
            System.out.println("2. Add New Account");
            System.out.println("3. Add Balance Account");
            System.out.println("4. Back");
            System.out.print("Choose option: ");

            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! Please enter a valid number. ]");
                continue;
            }

            switch (choice) {
                case 1 -> showAccount();
                case 2 -> addNewAccount();
                case 3 -> addBalanceToAccount();
                case 4 -> System.out.println("Returning to main menu...");
                default -> System.out.println("[ Invalid option! Try again. ]");
            }
        }
    }

    private void addNewAccount() {
        System.out.print("Enter new account name: ");
        String newAccount = scanner.nextLine().trim();

        if (newAccount.isEmpty()) {
            System.out.println("[ Account name cannot be empty! ]");
            return;
        }

        if (account.accountExists(newAccount)) {
            System.out.println("[ Account already exists! ]");
            return;
        }

        account.addNewAccount(newAccount);
        System.out.println("[ New account '" + newAccount + "' added with balance 0.0 ]");
    }

    private void addBalanceToAccount() {
        System.out.println("Available Accounts: ");
        for (String acc : account.getAllBalances().keySet()) {
            System.out.print(acc + " | ");
        }
        System.out.println();

        System.out.print("Enter Account: ");
        String source = scanner.nextLine();
        if (source.isEmpty()) source = "Unknown Source";

        double income;
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

        account.addAmount(source, income);
        transaction.addIncome("Income Entry", income, source, user.getName());

        System.out.println("\n[ Balance added successfully to " + source + "! ]\n");
    }

    private void showAccount() {
        System.out.println("\n--- Account Info ---");

        if (user == null) {
            System.out.println("[ No user registered. ]");
            return;
        }

        System.out.println("Name: " + user.getName());
        System.out.println("----------------------");
        System.out.println("Balances per Account:");

        for (var entry : account.getAllBalances().entrySet()) {
            System.out.printf("%-15s : %.2f%n", entry.getKey(), entry.getValue());
        }

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

    private void viewAnalysis() {
        List<Record> records = transaction.getRecords();

        if (records.isEmpty()) {
            System.out.println("\n[ No records available for analysis. ]\n");
            return;
        }

        double totalIncome = 0;
        double totalExpense = 0;

        Map<String, Double> categoryTotals = new HashMap<>();

        for (Record r : records) {
            if (r.isIncome) {
                totalIncome += r.amount;
            } else {
                totalExpense += r.amount;
            }

            categoryTotals.put(r.category, categoryTotals.getOrDefault(r.category, 0.0) + r.amount);
        }

        double balance = totalIncome - totalExpense;

        System.out.println("\n--- Analysis Report ---");
        System.out.printf("Total Income : %.2f\n", totalIncome);
        System.out.printf("Total Expense: %.2f\n", totalExpense);
        System.out.printf("Total Balance  : %.2f\n", balance);
        System.out.println("------------------------");

        System.out.println("\n--- Income Overview  ---");
        for (Record r : records) {
            if (r.isIncome) {
                System.out.printf("%-15s : %.2f%n", r.category, r.amount);
            }
        }

        System.out.println("\n--- Expense Overview ---");
        for (Record r : records) {
            if (!r.isIncome) {
                System.out.printf("%-15s : %.2f%n", r.category, r.amount);
            }
        }
        System.out.println("------------------------\n");
    }

}