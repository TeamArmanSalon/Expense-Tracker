import java.util.*;

public class Extension {
    private final Scanner scanner = new Scanner(System.in);
    private Account account = new Account('₱'); //default currency
    private final Transaction transaction = new Transaction(account);
    Expense expense = new Expense();
    private User user;
    private Budget budget = new Budget();


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
            case 4 -> manageBudgets();
            case 5 -> showAccountMenu();
            case 6 -> manageCategories();
            case 7 -> settings();
            case 0 -> {
                System.out.println("Exiting... Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("\n[ Invalid option. Try again. ]\n");
        }
    }

    public void settings() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("- Settings -");
            System.out.println("1. Profiles");
            System.out.println("2. Edit Records");
            System.out.println("3. Delete Records");
            System.out.println("4. Change Currency");
            System.out.println("5. Help");
            System.out.println("6. About");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Enter a number. ]\n");
                continue;
            }

            switch (choice) {
                case 1 -> manageProfiles();
                case 2 -> editRecord();
                case 3 -> deleteRecord();
                case 4 -> changeCurrency();
                case 5 -> help();
                case 6 -> about();
                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid option! Try again. ]\n");
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
                System.out.println("\n[ Must not be empty! ]\n");
                return;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! ]\n");
                return;
            }

            switch (choice) {
                case 1 -> System.out.printf("\nYour current profile is [ %s ]\n\n", user.getName());
                case 2 -> {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    while (!newName.matches("[a-zA-Z ,.]+")) {
                        System.out.println("\n[ Name must contain only letters, spaces, commas, or dots. ]\n");
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
                            System.out.println("\n[ Password cannot be empty. ]\n");
                        } else if (!newPassword.matches(passwordRegex)) {
                            System.out.println("\n[ Password must be at least 12 chars with uppercase, lowercase, number, and symbol. ]\n");
                        } else {
                            break;
                        }
                    }

                    user.setPassword(newPassword);
                    System.out.println("Password updated...");
                }
                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid option! ]\n");
            }
        }
    }

    private void editRecord() {
        List<Record> records = transaction.getRecords();

        if (records.isEmpty()) {
            System.out.println("\n[ No records found! ]\n");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            System.out.println(i + ". " + records.get(i).toString());
        }

        System.out.print("Select record number you want to edit: ");
        String temp = scanner.nextLine();

        if (temp.isEmpty()) {
            System.out.println("\n[ Must not be empty! ]\n");
            return;
        }

        int index;
        try {
            index = Integer.parseInt(temp);
        } catch (NumberFormatException e) {
            System.out.println("\n[ Invalid number! ]\n");
            return;
        }

        if (index < 0 || index >= records.size()) {
            System.out.println("\n[ Invalid record index! ]\n");
            return;
        }

        Record r = records.get(index);

        if(r.isIncome || r.isTransfer){
            System.out.println("\n[ You select Income/Transfer type. \n" +
                    "We don't have edit description features for this type yet.\n" +
                    "However, you can edit the amount. please set your amount... ]\n");
        }

        boolean isUpdated = false;
        String newDesc = "No Description";
        if(!r.isIncome){
            System.out.printf("Old description: %s\n", r.description);
            System.out.print("Enter new description (press \"s\" to keep): ");
            newDesc = scanner.nextLine().trim();
        }

        if(newDesc.isEmpty()){
            System.out.println("\n[ Must not be empty! Cancelled Change! ]\n");
            return;
        }

        if (!newDesc.equalsIgnoreCase("s")) {
            r.description = newDesc;
            isUpdated = true;
        }
        else{
            System.out.println("\n[ We keep the old description. ]\n");
        }

        System.out.println("Old Amount: " + account.getCurrency() + r.amount);
        System.out.print("Enter new amount (press \"s\" to keep): ");
        String amt = scanner.nextLine().trim();

        if(amt.isEmpty()){
            System.out.println("\n[ Must not be empty! Cancelled Change! ]\n");
            return;
        }

        if (!amt.equalsIgnoreCase("s")) {
            try {
                r.amount = Double.parseDouble(amt);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid amount! ]\n");
                return;
            }
            isUpdated = true;
        }
        else{
            System.out.println("\n[ We keep the old amount. ]\n");
        }

        if(isUpdated){
            System.out.println("Record updated!...");
        }
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
                System.out.println("\n[ Must not be empty! ]\n");
                return;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! ]\n");
                return;
            }

            switch (choice) {
                case 1 -> {
                    List<Record> records = transaction.getRecords();
                    if (records.isEmpty()) {
                        System.out.println("\n[ No records to delete! ]\n");
                        return;
                    }

                    for (int i = 0; i < records.size(); i++) {
                        System.out.println(i + ". " + records.get(i).toString());
                    }

                    System.out.print("Select record number: ");
                    String idx = scanner.nextLine();

                    if (idx.isEmpty()) {
                        System.out.println("\n[ Must not be empty! ]\n");
                        return;
                    }

                    try {
                        int index = Integer.parseInt(idx);
                        if (index >= 0 && index < records.size()) {
                            records.remove(index);

                            System.out.println("Record deleted successfully!...");
                        }
                        else {
                            System.out.println("\n[ Invalid record index! ]\n");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\n[ Invalid Input! Please enter a number. ]\n");
                    }
                }

                case 2 -> {
                    transaction.getRecords().clear();
                    System.out.println("All records cleared!...");
                }

                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid option! ]\n");
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
        String temp = scanner.nextLine().trim();

        if (temp.isEmpty()) {
            System.out.println("\n[ Must not be empty! ]\n");
            return;
        }
        else if(temp.length() != 1){
            System.out.println("\n[ Enter only one symbol ]\n");
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
            System.out.println("\n[ Enter currency that available in our system. ]\n");
        }
        else{
            account.setCurrency(symbol);
            System.out.println("\nCurrency changed to \"" + symbol + "\"...");
        }
    }

    private void help() {
        System.out.println("\nIf you need help or you want to report some error or bug,\n" +
                "contact us via github.");
        System.out.println("Github: https://github.com/TeamArmanSalon\n");
    }

    private void about() {
        System.out.println("""
                \nExpense Tracker v1.0
                Project in OOP (Object Oriented Programming)
                
                The Expense Tracker is a simple Java console-based application 
                that helps users manage their personal finances.
                It allows users to add expenses and income, set budgets, view financial summaries,
                and generate reports—all while applying key Object-Oriented Programming principles
                
                Created: November 2025
                
                [Member]
                    * Lucmayan, Joshan
                    * Cubero, Marc lean N.
                    * Evangelista, Ahldee
                    * Heruela, Dave
                    * Tenorio, Nadine D.
                    * Arellano, Ericka
                    * Dela Cruz, Tricia C
                """);
    }

    private void addExpenseAndIncome() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("- Expense/Income -");
            System.out.println("1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("0. Back");
            System.out.print("Choose option: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input. Please try again! ]\n");
                continue;
            }

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> addIncome();
                case 0 -> System.out.println("...\n");
                default -> System.out.println("\n[ Invalid option! Try again. ]\n");
            }
        }
    }

    private void addExpense() {

        Map<String, Double> accounts = account.getAllBalances();
        boolean zeroBalance = accounts.values().stream().allMatch(a -> a == 0);

        if(zeroBalance){
            System.out.println("\n[ Cannot add Expense! ]");
            System.out.println("Reason: You have zero balance in your accounts!");
            System.out.println("Tip: Go to \"Add Income\" to set your balance.\n");
            return;
        }

        System.out.println("\nNOTE: Please enter your expense amount carefully.");
        System.out.println("Make sure the amount does not exceed your available balance.");
        System.out.println("Entering an amount higher than your balance may cause error.\n");

        System.out.println("Available Accounts: ");
        for(var entry : account.getAllBalances().entrySet()){
            if(entry.getValue() != 0){
                System.out.printf("%s: %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
            }
        }
        System.out.println();

        System.out.print("Select Account: ");
        String tempAccountName = scanner.nextLine().trim();

        if(tempAccountName.isEmpty()) {
            System.out.println("\n[ Account name is empty! ]\n");
            return;
        }

        String accountName = formatThis(tempAccountName);

        if (!account.getCategoriesAccount().contains(accountName)) {
            System.out.println("\n[ Account does not exist! Expense cancelled. ]\n");
            return;
        }

        System.out.println("Available Expense Categories:");
        for (String cat : expense.getExpenseCategories()) {
            System.out.print(cat + " | ");
        }
        System.out.println();

        System.out.print("Enter (or Add) Category: ");
        String tempCategory = scanner.nextLine();

        if (tempCategory.isEmpty()) {
            System.out.println("\n[ Please Enter Category! ]\n");
            return;
        }

        String category =  formatThis(tempCategory);

        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        if (desc.isEmpty()){
            System.out.println("\n[ Must not be empty ]\n");
        }

        double amount;
        while (true) {
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                amount = Double.parseDouble(temp);
                if (amount <= 0) {
                    System.out.println("\n[ Amount must be greater than 0! ]\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Enter a number. ]\n");
            }
        }

        if (!expense.getExpenseCategories().contains(category)) {
            expense.setExpenseCategories(category);
        }

        boolean success = transaction.addExpense(desc, amount, category, accountName);

        if (!success) {
            System.out.println("\n[ Not enough balance! Expense Cancelled... ]\n");
            return;
        }

        System.out.println("\nExpense added successfully!...\n");
    }


    private void addIncome() {
        System.out.println("Existing Accounts: ");
        for(var entry : account.getAllBalances().entrySet()){
            System.out.printf("%s: %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
        }
        System.out.println();

        System.out.print("Select (or Add) an Account: ");
        String tempSource = scanner.nextLine().trim();

        if (tempSource.isEmpty()){
            System.out.println("\n[ Please Enter Source of Income! ]\n");
            return;
        }

        if(tempSource.matches("[0-9]")){
            System.out.println("\n[ Incorrect source! Please enter valid source!. ]\n");
            return;
        }

        String source = formatThis(tempSource);

        if(!account.getCategoriesAccount().contains(source)){
            account.addNewAccount(source);
        }

        double income;
        while (true) {
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                income = Double.parseDouble(temp);
                if (income <= 0) {
                    System.out.println("\n[ Amount must be greater than 0! ]\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Please enter a valid number. ]\n");
            }
        }

        transaction.addIncome(income, source);

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
        System.out.printf("Total Income   : %c%.2f\n", account.getCurrency(), totalIncome);
        System.out.printf("Total Expense  : %c%.2f\n", account.getCurrency(), totalExpense);
        System.out.printf("Total Balance  : %c%.2f\n", account.getCurrency(), balance);
        System.out.println("------------------------\n");

        System.out.println("\n--- Income Overview  ---");

        records.sort(Comparator.comparingDouble((Record a) -> a.amount).reversed());

        boolean haveNoIncome = true;
        boolean haveNoExpense = true;
        for (Record r : records) {
            if (r.isIncome && r.amount != 0.00) {
                System.out.printf("%-15s : %c%.2f%n", r.accountName, account.getCurrency(), r.amount);
                haveNoIncome = false;
            }
        }

        if(haveNoIncome){
            System.out.println("[ You don't have income yet. ]");
        }

        System.out.println("\n--- Expense Overview ---");
        for (Record r : records) {
            if (!r.isIncome && r.amount != 0.00) {
                System.out.printf("%-15s : %c%.2f%n", r.category, account.getCurrency(), r.amount);
                haveNoExpense = false;
            }
        }

        if(haveNoExpense){
            System.out.println("[ You don't have expenses yet. ]");
        }

        System.out.println("\n--- Budget Overview ---");

        if(budget.getMapBudgets().isEmpty()){
            System.out.println("[ You don't have budget yet. ]\n");
            return;
        }

        for(var bud : budget.getMapBudgets().entrySet()){
            if(bud.getValue() != 0.00){
                System.out.printf("%-15s : %c%.2f%n", bud.getKey(), account.getCurrency(), bud.getValue());
            }
        }
        System.out.println();


        System.out.println("\n------------------------\n");
    }

    public void manageBudgets() {
        int choice = -1;

        do {
            System.out.println("- Manage Budgets -");
            System.out.println("1. View Budgets");
            System.out.println("2. Set / Update Budget");
            System.out.println("3. Remove Budget");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            String temp = scanner.nextLine();

            if(temp.isEmpty()){
                System.out.println("\n[ Must not be empty ]\n");
                continue;
            }


            try{
                choice = Integer.parseInt(temp);
            }catch (NumberFormatException e){
                System.out.println("\n[ Please enter valid option! ]\n");
                continue;
            }

            switch (choice) {
                case 1 -> viewBudgets();
                case 2 -> setOrUpdateBudget();
                case 3 -> removeBudget();
                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid choice. ]\n");
            }

        } while (choice != 0);
    }

    private void viewBudgets() {
        if (budget.getMapBudgets().isEmpty()) {
            System.out.println("\n[ No budgets set. ]");
            System.out.println("Tip: Set Budget\n");
            return;
        }

        System.out.println("\n--- Current Budgets ---");

        for (Map.Entry<String, Double> entry : budget.getMapBudgets().entrySet()) {
            String category = entry.getKey();
            double budgetLimit = entry.getValue();

            double spent = 0;
            for (Record r : transaction.getRecords()) {
                if (!r.isIncome && r.category.equalsIgnoreCase(category)) {
                    spent += r.amount;
                }
            }

            double remaining = budgetLimit - spent;

            String status = "";
            if (remaining < 0) {
                status = "Over spent";
            }
            else if (spent < remaining && spent != 0){
                status = "Low spent";
            }
            else if(spent == 0){
                status = "No Status";
            }

            System.out.printf("%-15s : %c%.2f | Spent: %c%.2f | Left: %c%.2f | Status: %s%n", category,  account.getCurrency(), budgetLimit,
                    account.getCurrency(), spent, account.getCurrency(), remaining, status);
        }
    }


    private void setOrUpdateBudget() {
        System.out.println("Available Budget categories: ");
        for(String budget : budget.getBudgetCategories()){
            System.out.print(budget + " | ");
        }
        System.out.println();
        System.out.print("Enter (or Add) Category: ");
        String tempCat = scanner.nextLine().trim();

        String category = formatThis(tempCat);

        if (category.isEmpty()) {
            System.out.println("\n[ Category cannot be empty. ]\n");
            return;
        }

        double amount;
        while(true){
            System.out.print("Enter Budget Amount: ");
            String temp = scanner.nextLine();

            try{
                amount = Double.parseDouble(temp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[ Please enter valid amount ]\n");
            }
        }

        if(!budget.getBudgetCategories().contains(category)){
            budget.addCategory(category);
        }

        budget.setBudget(category, amount);

        System.out.printf("\nBudget for \"%s\" set to %c%.2f...\n", category, account.getCurrency(), amount);
    }

    private void removeBudget() {
        System.out.println("Select budget categories you want to remove: ");
        for(String budget : budget.getBudgetCategories()){
            System.out.print(budget + " | ");
        }
        System.out.println();

        System.out.print("Enter category to remove budget: ");
        String tempCat = scanner.nextLine().trim();

        String category = formatThis(tempCat);

        if (budget.getBudgetCategories().contains(category)) {
            budget.removeCategory(category);
            System.out.println("\nBudget removed for category: " + category + "...");
        } else {
            System.out.println("\n[ No budget found for that category. ]\n");
        }
    }

    private void showAccountMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("- Account -");
            System.out.println("1. View Accounts");
            System.out.println("2. Add New Account");
            System.out.println("3. Add Balance Account");
            System.out.println("4. Delete Account");
            System.out.println("5. Transfer");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Please enter a valid number. ]\n");
                continue;
            }

            switch (choice) {
                case 1 -> showAccount();
                case 2 -> addNewAccount();
                case 3 -> addBalanceToAccount();
                case 4 -> deleteAccount();
                case 5 -> transfer();
                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid option! Try again. ]\n");
            }
        }
    }

    private void transfer(){
        if (account.getAllBalances().isEmpty()) {
            System.out.println("\n[ No accounts available. ]\n");
            return;
        }

        boolean zeroBalance = true;
        for (var entry : account.getAllBalances().entrySet()) {
            if(entry.getValue() > 0){
                zeroBalance = false;
                break;
            }
        }

        if(zeroBalance){
            System.out.println("\nYou cannot Transfer!");
            System.out.println("[ You have zero balance in your account! ]\n");
            return;
        }

        System.out.println("Transfer [From] Accounts:");
        for (var entry : account.getAllBalances().entrySet()) {
            System.out.printf("%s : %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
        }
        System.out.println();

        System.out.print("Select an Account: ");
        String tempAccount = scanner.nextLine();

        if(tempAccount.isEmpty()){
            System.out.println("\n[ Must not be empty! ]\n");
            return;
        }

        String accFrom = formatThis(tempAccount);

        if(!account.accountExists(accFrom)){
            System.out.println("\n[ Account doesn't exist! ]\n");
            return;
        }

        System.out.println("Transfer [To] Accounts:");
        for (var entry : account.getAllBalances().entrySet()) {
            if(!entry.getKey().equals(accFrom)){
                System.out.printf("%s : %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
            }
        }
        System.out.println();

        System.out.print("Select an Account: ");
        String tempAccount2 = scanner.nextLine();

        if(tempAccount2.isEmpty()){
            System.out.println("\n[ Must not be empty! ]\n");
            return;
        }

        String accTo = formatThis(tempAccount2);

        if(accFrom.equals(accTo)){
            System.out.println("\n[ Must not same as [From] Account! ]\n");
            return;
        }

        if(!account.accountExists(accTo)){
            System.out.println("\n[ Account doesn't exist! ]\n");
            return;
        }

        double amt;
        while(true){
            Map<String, Double> balances = account.getAllBalances();

            Double fromBalance = balances.get(accFrom);
            Double toBalance   = balances.get(accTo);

            char currency = account.getCurrency();

            if (fromBalance != null) {
                System.out.printf("%s : %c%.2f -> ", accFrom, currency, fromBalance);
            }

            if (toBalance != null) {
                System.out.printf("%s : %c%.2f", accTo, currency, toBalance);
            }

            System.out.println();

            System.out.print("Enter amount to transfer: ");
            String tempAmt = scanner.nextLine();

            if(tempAmt.isEmpty()){
                System.out.println("\n[ Must not be empty ]\n");
                continue;
            }

            try{
                amt = Double.parseDouble(tempAmt);

                if(amt < 0.0){
                    System.out.println("\n[ Must not less than 0! ]\n");
                    return;
                }

                boolean greaterThan = false;
                for (var entry : account.getAllBalances().entrySet()) {
                    if(entry.getKey().equals(accFrom) && amt > entry.getValue()){
                        System.out.println("\n[ Must not be greater than the amount\n" +
                                "from the account you want to transfer. ]\n");

                        System.out.printf("Balance of %s: %c%.2f%n", accFrom, account.getCurrency(),
                                entry.getValue());
                        greaterThan = true;
                        break;
                    }
                }

                if(greaterThan){
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Please Enter number. ]\n");
            }
        }

        transaction.addTransfer(accFrom, accTo, amt);

        System.out.println("\nSuccessfully transfer...\n");
    }

    private void deleteAccount() {
        if (account.getAllBalances().isEmpty()) {
            System.out.println("\n[ No accounts available. ]\n");
            return;
        }

        System.out.println("Available Accounts: ");
        for (var entry : account.getAllBalances().entrySet()) {
            System.out.printf("%s : %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
        }
        System.out.println();

        System.out.print("Enter account name to delete: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("\n[ Account name cannot be empty! ]\n");
            return;
        }

        String accName = formatThis(name);

        if (!account.accountExists(accName)) {
            System.out.println("\n[ Account does not exist! ]\n");
            return;
        }

        double balance = account.getBalance(accName);

        if (balance > 0) {
            System.out.printf(
                    "\n[ WARNING: '%s' still has %c%.2f balance. ]\n",
                    accName, account.getCurrency(), balance
            );
            System.out.print("Are you sure you want to delete it? (Y/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();

            if(confirm.equals("N")){
                System.out.println("\nDelete cancelled...\n");
                return;
            }
            if (!confirm.equals("Y")) {
                System.out.println("\n[ Please Enter \"Y\" YES / \"N\" NO! Delete cancelled. ]\n");
                return;
            }
        }

        boolean removed = account.removeAccount(accName);

        if(removed){
            System.out.println("\nAccount '" + accName + "' deleted successfully...\n");
        }
        else {
            System.out.println("\n[ Failed to delete account. Something went wrong! ]\n");
        }
    }


    private void addNewAccount() {
        System.out.print("Enter New Account: ");
        String tempAccount = scanner.nextLine().trim();

        if (tempAccount.isEmpty()) {
            System.out.println("\n[ Account name cannot be empty! ]\n");
            return;
        }

        String newAccount = formatThis(tempAccount);

        if (account.accountExists(newAccount)) {
            System.out.println("\n[ Account already exists! ]\n");
            return;
        }

        account.addNewAccount(newAccount);
        System.out.println("\n[ New account '" + newAccount + "' added with balance 0.0 ]\n");
    }

    private void addBalanceToAccount() {
        System.out.println("Available Accounts: ");
        for(var entry : account.getAllBalances().entrySet()){
            System.out.printf("%s: %c%.2f | ", entry.getKey(), account.getCurrency(), entry.getValue());
        }
        System.out.println();

        System.out.print("Enter Account: ");
        String tempSource = scanner.nextLine();
        if (tempSource.isEmpty()){
            System.out.println("\n[ Account name cannot be empty! ]\n");
            return;
        }

        String accountName = formatThis(tempSource);

        if (!account.accountExists(accountName)) {
            System.out.println("\n[ Account doesn't exists! ]\n");
            return;
        }

        double amount;
        while (true) {
            System.out.print("Enter Amount: ");
            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                amount = Double.parseDouble(temp);
                if (amount <= 0) {
                    System.out.println("\n[ Amount must be greater than 0! ]\n");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Please enter a valid number. ]\n");
            }
        }

        transaction.addIncome(amount, accountName);

        System.out.println("\n[ Balance added successfully to " + accountName + "! ]\n");
    }

    private void showAccount() {
        System.out.println("\n---- Account Info ----");

        if (user == null) {
            System.out.println("\n[ No user registered. ]");
            return;
        }

        System.out.println("Name: " + user.getName());
        System.out.println("Total balance : " + account.getCurrency() + account.getBalance());
        System.out.println("----------------------");
        System.out.println("Balances per Account:");

        for (var entry : account.getAllBalances().entrySet()) {
            System.out.printf("%-15s : %c%.2f%n", entry.getKey(), account.getCurrency(), entry.getValue());
        }

        System.out.println("----------------------\n");
    }

    public void manageCategories() {
        int choice = -1;
        do {
            System.out.println("- Manage Categories -");
            System.out.println("1. View Categories");
            System.out.println("2. Add Category");
            System.out.println("3. Remove Category");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            String temp = scanner.nextLine();

            if (temp.isEmpty()) {
                System.out.println("\n[ Must not be empty! ]\n");
                continue;
            }

            try {
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("\n[ Invalid input! Please enter a valid number. ]\n");
                continue;
            }

            switch (choice) {
                case 1 -> viewCategories();
                case 2 -> addCategory();
                case 3 -> removeCategory();
                case 0 -> System.out.println("...");
                default -> System.out.println("\n[ Invalid choice. ]\n");
            }

        } while (choice != 0);
    }

    private void viewCategories() {
        System.out.println("\n--- Expense Categories ---");
        for (String cat : expense.getExpenseCategories()) {
            System.out.println("* " + cat);
        }

        System.out.println("\n--- Income Categories ---");
        for (String cat : account.getCategoriesAccount()) {
            System.out.println("* " + cat);
        }

        System.out.println("\n--- Budget Categories ---");
        for (String cat : budget.getBudgetCategories()) {
            System.out.println("* " + cat);
        }
        System.out.println();
    }


    private void addCategory() {
        System.out.print("Is this for Income, Expense, or Budget? (I/E/B): ");
        String tempType = scanner.nextLine().trim();

        if(tempType.isEmpty()){
            System.out.println("\n[ Must not be empty ]\n");
            return;
        }
        else if(tempType.length() > 1){
            System.out.println("\n[ Please enter \"I\" for Income, \"E\" Expense, \"B\" Budget. ]\n");
            return;
        }

        char type = tempType.toUpperCase().charAt(0);

        if(type != 'I' && type != 'E' && type != 'B'){
            System.out.println("\n[ Please enter \"I\" for Income, \"E\" Expense, \"B\" Budget. ]\n");
            return;
        }

        System.out.print("Enter new category name: ");
        String category = scanner.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("\n[ Category cannot be empty. ]\n");
            return;
        }

        if (type == 'I') {
            if (!account.getCategoriesAccount().contains(category)) {
                account.addNewAccount(category);
                System.out.println("Income category added...");
            }
            else System.out.println("\nCategory already exists...\n");
        }
        else if (type == 'E') {
            if (!expense.getExpenseCategories().contains(category)) {
                expense.setExpenseCategories(category);
                System.out.println("Expense category added...");
            }
            else System.out.println("\nCategory already exists...\n");
        }
        else if (type == 'B') {
            if (!budget.getBudgetCategories().contains(category)) {
                budget.addCategory(category);
                System.out.println("Budget category added...");
            }
            else System.out.println("\nCategory already exists...\n");
        }
        else{
            System.out.println("\n[ Invalid type. ]\n");
        }
    }

    private void removeCategory() {
        System.out.print("Remove from Income, Expense, or Budget? (I/E/B): ");
        String tempType = scanner.nextLine().trim();

        if(tempType.isEmpty()){
            System.out.println("\n[ Must not be empty ]\n");
            return;
        }
        else if(tempType.length() > 1){
            System.out.println("\n[ Please enter \"I\" for Income, \"E\" Expense, \"B\" Budget. ]\n");
            return;
        }

        char type = tempType.toUpperCase().charAt(0);

        if(type != 'I' && type != 'E' && type != 'B'){
            System.out.println("\n[ Please enter \"I\" for Income, \"E\" Expense, \"B\" Budget. ]\n");
            return;
        }

        if (type == 'I') {
            System.out.println("\n--- Income Categories ---");
            for (String cat : account.getCategoriesAccount()) {
                System.out.println("* " + cat);
            }
        }
        else if (type == 'E') {
            System.out.println("\n--- Expense Categories ---");
            for (String cat : expense.getExpenseCategories()) {
                System.out.println("* " + cat);
            }
        }
        else if (type == 'B') {
            System.out.println("\n--- Budget Categories ---");
            for (String cat : budget.getBudgetCategories()) {
                System.out.println("* " + cat);
            }
        }

        System.out.print("Enter category to remove: ");
        String category = scanner.nextLine().trim();

        if (type == 'I') {
            if (account.removeAccount(category)) {
                System.out.println("Income category removed...");
            }
            else System.out.println("\nCategory not found...\n");
        }
        else if (type == 'E') {
            if (expense.removeExpenseCat(category)) {
                System.out.println("Expense category removed...");
            }
            else System.out.println("\nCategory not found...\n");
        }
        else if (type == 'B') {
            if (budget.getBudgetCategories().contains(category)) {
                budget.removeCategory(category);
                budget.getMapBudgets().remove(category); // remove assigned budget too
                System.out.println("Budget category removed...");
            }
            else System.out.println("\nCategory not found...\n");
        }
        else {
            System.out.println("\n[ Invalid type. ]\n");
        }
    }


    public void registerUser() {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        while (!name.matches("[a-zA-Z ,.]+")) {
            System.out.println("\n[ Name must contain only letters, spaces, commas, or dots. ]\n");
            System.out.print("Enter Name: ");
            name = scanner.nextLine();
        }

        String passwordRegex = "^(?=.{12,64}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).*$";
        String password;

        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("\n[ Password cannot be empty. ]\n");
            } else if (!password.matches(passwordRegex)) {
                System.out.println("\n[ Password must be at least 12 chars with uppercase, lowercase, number, and symbol. ]\n ");
            } else {
                break;
            }
        }

        user = new User(name, password);
        transaction.setUser(user);

        System.out.println("\n[ Account created successfully! ]\n");
    }
}