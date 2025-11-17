import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Extension {
    private final Scanner scanner = new Scanner(System.in);
    private final Transaction transaction = new Transaction();
    private Account account = new Account('₱'); //default currency
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

    public static String formatThis(String x){
        String accountName;
        String f = x.substring(0, 1).toUpperCase();
        String f2 = x.substring(1).toLowerCase();

        accountName = f + f2;
        return accountName;
    }

    private void mainMenu() {
        System.out.println("- Expense Tracker -");
        System.out.println("1. Expense & Income");
        System.out.println("2. View Records");
        System.out.println("3. View Analysis");
        System.out.println("4. Manage Budgets");
        System.out.println("5. Manage Accounts");
        System.out.println("6. Manage Categories");
        System.out.println("7. Settings");
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
            case 7 -> settings();
            case 0 -> {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("[ Invalid option. Try again. ]");
        }
    }

    public void settings() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n- Settings -");
            System.out.println("1. Profiles");
            System.out.println("2. Edit Records");
            System.out.println("3. Delete Records");
            System.out.println("4. Change Currency");
            System.out.println("5. Help");
            System.out.println("6. About Us");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! Enter a number. ]");
                continue;
            }

            switch (choice) {
                case 1 -> manageProfiles();
                case 2 -> editRecord();
                case 3 -> deleteRecord();
                case 4 -> changeCurrency();
                case 5 -> showHelp();
                case 6 -> about();
                case 0 -> System.out.println("...");
                default -> System.out.println("[ Invalid option! Try again. ]");
            }
        }
    }

    private void manageProfiles() {
        int choice = -1;

        while(choice != 0){
            System.out.println("1. View Current Profile");
            System.out.println("2. Change Name");
            System.out.println("3. Change Password");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                return;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! ]");
                return;
            }

            switch (choice) {
                case 1 -> System.out.printf("\nYour current profile is [ %s ]\n\n", user.getName());
                case 2 -> {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    while (!newName.matches("[a-zA-Z ,.]+")) {
                        System.out.println("[ Name must contain only letters, spaces, commas, or dots. ]");
                        System.out.print("Enter Name: ");
                        newName = scanner.nextLine();
                    }

                    user.setName(newName);
                    System.out.println("Name updated...");
                }
                case 3 -> {
                    String passwordRegex = "^(?=.{12,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$";
                    String newPassword;

                    while (true) {
                        System.out.print("Enter new password: ");
                        newPassword = scanner.nextLine();
                        if (newPassword.isEmpty()) {
                            System.out.println("[ Password cannot be empty. ]");
                        } else if (!newPassword.matches(passwordRegex)) {
                            System.out.println("[ Password must be at least 12 chars with uppercase, lowercase, number, and symbol. ]");
                        } else {
                            break;
                        }
                    }

                    user.setPassword(newPassword);
                    System.out.println("Password updated...");
                }
                case 0 -> System.out.println("...");
                default -> System.out.println("[ Invalid option! ]");
            }
        }
    }

    private void editRecord() {
        List<Record> records = transaction.getRecords();

        if (records.isEmpty()) {
            System.out.println("[ No records found! ]");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            System.out.println(i + ". " + records.get(i));
        }

        System.out.print("Select record number you want to edit: ");
        String temp = scanner.nextLine();

        if (temp.isEmpty()) {
            System.out.println("[ Must not be empty! ]");
            return;
        }

        int index;
        try {
            index = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            System.out.println("[ Invalid number! ]");
            return;
        }

        if (index < 0 || index >= records.size()) {
            System.out.println("[ Invalid record index! ]");
            return;
        }

        Record r = records.get(index);

        System.out.printf("Old description: %s\n", r.description);
        System.out.print("Enter new description (press \"s\" to keep): ");
        String newDesc = scanner.nextLine().trim();

        if(newDesc.isEmpty()){
            System.out.println("[ Must not be empty! ]");
            return;
        }

        if (!newDesc.equalsIgnoreCase("s")) {
            r.description = newDesc;
        }
        else{
            System.out.println("[ We keep the old description. ]");
        }

        System.out.print("Enter new amount (press \"s\" to keep): ");
        String amt = scanner.nextLine().trim();

        if(amt.isEmpty()){
            System.out.println("[ Must not be empty! ]");
            return;
        }

        if (!amt.equalsIgnoreCase("s")) {
            try {
                double newAmount = Double.parseDouble(amt);
                r.amount = newAmount;
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid amount! ]");
            }
        }
        else{
            System.out.println("[ We keep the old amount. ]");
        }

        System.out.println("Record updated!...");
    }

    private void deleteRecord() {
        int choice = -1;

        while(choice != 0){
            System.out.println("1. Delete one record");
            System.out.println("2. Delete ALL records");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("[ Must not be empty! ]");
                return;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("[ Invalid input! ]");
                return;
            }

            switch (choice) {
                case 1 -> {
                    List<Record> records = transaction.getRecords();
                    if (records.isEmpty()) {
                        System.out.println("[ No records to delete! ]");
                        return;
                    }

                    for (int i = 0; i < records.size(); i++) {
                        System.out.println(i + ". " + records.get(i));
                    }

                    System.out.print("Select record number: ");
                    String idx = scanner.nextLine();

                    if (idx.isEmpty()) {
                        System.out.println("[ Must not be empty! ]");
                        return;
                    }

                    try {
                        int index = Integer.parseInt(idx);
                        if (index >= 0 && index < records.size()) {
                            records.remove(index);

                            System.out.println("Record deleted successfully!...");
                        }
                        else {
                            System.out.println("[ Invalid record index! ]");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[ Invalid Input! Please enter a number. ]");
                    }
                }

                case 2 -> {
                    transaction.getRecords().clear();
                    System.out.println("All records cleared!...");
                }

                case 0 -> System.out.println("...");
                default -> System.out.println("[ Invalid option! ]");
            }
        }
    }


    private void changeCurrency() {
        String[] currencies = { "₱", "$", "€", "¥", "£", "₹", "₩", "₫", "₦", "₲", "₴", "₡", "₸", "₽", "฿", "₭"};
        System.out.println("""
                \n⚠ Please be cautious when entering currency symbols.
                Some symbols may not be supported by our system or may cause errors.
                Make sure you type the correct symbol as required.
                """);
        System.out.print("Available currencies in our system: ");
        for(String currency : currencies){
            System.out.print(currency + " | ");
        }
        System.out.println();

        System.out.print("Enter new currency symbol: ");
        String temp = scanner.nextLine();

        if (temp.isEmpty()) {
            System.out.println("\n[ Must not be empty! ]");
            return;
        }
        else if(temp.length() != 1){
            System.out.println("\n[ Enter only one symbol ]");
            return;
        }

        char symbol = '\0'; //empty char
        boolean notContains = true;
        for(String c : currencies){
            if(temp.contains(c)){
                symbol = temp.charAt(0);
                notContains = false;
                break;
            }
        }

        if(notContains){
            System.out.println("\n[ Enter currency that available in our system. ]");
        }
        else{
            account.setCurrency(symbol);
            System.out.println("\nCurrency changed to \"" + symbol + "\"...");
        }
    }

    private void showHelp() {
        System.out.println("Use settings to manage profiles, edit records, change currency.");
    }

    private void about() {
        System.out.println("Expense Tracker v1.0");
        System.out.println("Project in OOP (Object Oriented Programming)");
    }


    private void addExpenseAndIncome() {
        int choice = 0;
        while (choice != 3) {
            System.out.println("\n- Expense/Income -");
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
                case 3 -> System.out.println("...\n");
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
        String tempAccountName = scanner.nextLine().trim();

        if(tempAccountName.isEmpty()) {
            System.out.println("[ Account name is empty! ]");
            return;
        }

        String accountName = formatThis(tempAccountName);

        if (!account.getCategoriesAccount().contains(accountName)) {
            System.out.println("[ Account does not exist! Expense cancelled. ]");
            return;
        }

        System.out.println("Available Expense Categories (Can be Added):");
        for (String cat : expense.getExpenseCategories()) {
            System.out.print(cat + " | ");
        }
        System.out.println();

        System.out.print("Enter Category: ");
        String tempCategory = scanner.nextLine();

        if (tempCategory.isEmpty()) {
            System.out.println("Please Enter Category!");
            return;
        }

        String category =  formatThis(tempCategory);

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
        System.out.println("Existing Accounts: ");
        for (String acc : account.getCategoriesAccount()) {
            System.out.print(acc + " | ");
        }
        System.out.println();

        System.out.print("Select an Account: ");
        String tempSource = scanner.nextLine().trim();
        if(tempSource.matches("[0-9]")){
            System.out.println("Incorrect source! Please enter valid source!. ]");
            return;
        }

        String source = formatThis(tempSource);

        if (source.isEmpty()){
            System.out.println("Please Enter Source of Income!");
            return;
        }

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

        transaction.addIncome(income, source,  source);

        System.out.println("\n[ Income added successfully to " + source + "! ]\n");
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
        System.out.println("------------------------\n");

        System.out.println("\n--- Income Overview  ---");
        for (Record r : records) {
            if (r.isIncome) {
                System.out.printf("%-15s : %.2f%n", r.category, r.amount);
            }
        }

        System.out.println("\n--- Expense Overview ---\n");
        for (Record r : records) {
            if (!r.isIncome) {
                System.out.printf("%-15s : %.2f%n", r.category, r.amount);
            }
        }
        System.out.println("------------------------\n");
    }

    //Budget method here!


    private void showAccountMenu() {
        int choice = -1;

        while (choice != 4) {
            System.out.println("\n- Account -");
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
        System.out.print("Enter New Account: ");
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
        String tempSource = scanner.nextLine();
        if (tempSource.isEmpty()){
            System.out.println("[ Account name cannot be empty! ]");
            return;
        }

        String source = formatThis(tempSource);

        if (!account.accountExists(source)) {
            System.out.println("[ Account doesn't exists! ]");
            return;
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
        transaction.addIncome( income, source, user.getName());

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

        System.out.println("----------------------\n");
    }

    //  Manage Categories Here


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