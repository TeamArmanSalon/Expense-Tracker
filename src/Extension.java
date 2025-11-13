import java.util.Scanner;

public class Extension {
    private User user;
    private Transaction transaction;

    Extension(Transaction transaction){
        this.transaction = transaction;
    }
    public void registerUser(){
        Scanner scanner = new Scanner(System.in);

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
        transaction.setUser(user);

        System.out.println("\nAccount created successfully!\n");
    }
}
