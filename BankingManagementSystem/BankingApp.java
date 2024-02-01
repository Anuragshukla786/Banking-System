package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;

//This is main class and it is Driver class.this class is used all other classes
public class BankingApp {
    //We are create a url username and password  final.no anyone can
    //modified and private keyword using not a access outside the class static declare a variable
    //without creating object it can access,
    //These three variables are database's credential
    private static  final String url="jdbc:mysql://127.0.0.1:3306/banking_system";
    private  static  final String username="root";
    private static  final String password="root";

    //
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            //here we are load  a Drivers
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            //we are established  a connection with a database using get connection method jo hota hai
            //Driver manager  class ke pass and it is take the three arguments  url username password
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner scanner =  new Scanner(System.in);

            //we are create a object of  User accounts and AccountManager class and creating the constructor
            //and passing instance of Scanner and conncection .
            User user = new User(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            //We are create two varibale because we are prereserve the email of user
            String email;
            long account_number; //and account mtlb ek barr user ne sign kr liya to uski email or constant rahega

            //ye while lop tb tk end nhi hoga jab user exit nhi kr deta
            while(true){
                System.out.println("********* WELCOME TO BANKING SYSTEM **********");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.println("Enter your choice: ");
                int choice1 = scanner.nextInt();
                //user can choose option 1 he is register in bank
                switch (choice1){
                    case 1:
                        user.register();
                        break;
                    case 2://user login krega or ye method usre class me hai
                        email = user.login();
                        //login ke baad ager user ki email agr exist krta hai to hum print kr denge open a bank account
                        //and exit
                        if(email!=null){
                            System.out.println();
                            System.out.println("User Logged In!");
                            if(!accounts.account_exist(email)){
                                System.out.println();
                                System.out.println("1. Open a new Bank Account");
                                System.out.println("2. Exit");
                                if(scanner.nextInt() == 1) {
                                    account_number = accounts.open_account(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Your Account Number is: " + account_number);
                                }else{
                                    break;
                                }

                            }
                            //user can perfoem operation of acount
                            account_number = accounts.getAccount_number(email);
                            int choice2 = 0;
                            //yha user aninchocice enter krgea or ye loop tb tk chalega jab tk user 5 nhi enter kr deta
                            while (choice2 != 5) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Log Out");
                                System.out.println("Enter your choice: ");
                                // are call all method  using  instance of account managr and perform one one by one
                                //condition till than 5;
                                choice2 = scanner.nextInt();
                                switch (choice2) {
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        break;
                                    case 4:
                                        accountManager.getBalance(account_number);
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Enter Valid Choice!");
                                        break;
                                }
                            }

                        }
                        else{
                            System.out.println("Incorrect Email or Password!");
                        }
                    case 3://agr case three enter krta hai to wo exit kr jayaega
                        System.out.println("THANK YOU FOR USING BANKING SYSTEM!!!");
                        System.out.println("Exiting System!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice");
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}