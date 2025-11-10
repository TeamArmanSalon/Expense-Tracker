import java.util.InputMismatchException;
import java.util.Scanner;

public class Add extends Expense {
    private Scanner scanner;
    private Transaction records;

    Add(Scanner scanner, Transaction records){
        this.scanner = scanner;
        this.records = records;
    }

    public void addExpense(){
        Expense expense = new Expense();
        while (true) {
            try {
                System.out.print("Enter amount: ");
                if (!scanner.hasNextDouble()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    continue; // restart loop
                }
                double amount = scanner.nextDouble();
                scanner.nextLine();

                System.out.print("Here are some categories: ");
                for (String cat : expense.getCategory()) {
                    System.out.print(cat + " | ");
                }
                System.out.println();

                System.out.print("Enter Category: ");
                String category = scanner.nextLine();

                System.out.print("Enter Description: ");
                String desc = scanner.nextLine();

                expense = new Expense(desc, amount);
                expense.setCategory(category);
                records.addExpense(desc, amount, category);

                System.out.println("Expense added successfully!\n");
                break; // exit loop when done

            } catch (InputMismatchException e) {
                System.out.println("Invalid input type. Please try again.");
                scanner.nextLine(); // clear buffer
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }

}
