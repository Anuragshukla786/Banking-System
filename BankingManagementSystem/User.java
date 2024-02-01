package BankingManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class User {

    //two private variable are declare
    private Connection  connection;
    private Scanner  scanner;


    //Create parametrize constructor
    public User(Connection connection,Scanner scanner)
    {
        //initialize the value of help of constructor and its value we are take the
        // value of banking class 's Connection and Scanner's Instance
        this.connection=connection;
        this.scanner=scanner;
    }
    public  void register()
    {
        //fisrt We take the input of user's name , email and password or check kia kya us email pe
        // koi user exist krta hai ya nhi agr krta hai to humprint krenge if condition
        scanner.nextLine();
        System.out.println("Full Name:");
        String full_name=scanner.nextLine();
        System.out.println("Email:");
        String email=scanner.nextLine();
        System.out.println("Password:");
        String password=scanner.nextLine();

        if(user_exist(email))
        {
            System.out.println("User Already Exists for this Email Address!");
            return;
        }
        //agr user exist nhi krta to ye block chlega or   or ye query chlegi or database ke andr new user  register kregi
        String register_query="INSERT INTO User(full_name,email,password)VALUES(?,?,?)";
        try{
            //We know we are used preparedStatement tab Sql Exception Ata hai isliye  hum use tr catch se handle krenege
            PreparedStatement preparedStatement=connection.prepareStatement(register_query);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,password);
            //We are upadate data on data base We are use method ExecuteUpdate();
            int affectedRows =preparedStatement.executeUpdate();
          // check krengekya koi row affect hui hai agr hui hai to kya wo affected row greater hai 0
            //se to registration successfull othrwise failed
            if(affectedRows>0)
            {
                System.out.println("Registration SuccessFull!");
            }
            else
            {
                System.out.println("Registration failed!");
            }
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    //Login function
    public String login()
    {
        //jab user login kregga tab ye  login function  uski mail ko return krega  ki ye user login kr liya hai
        //uska mail ye hai
        scanner .nextLine();
        System.out.println("Email:");
        String email=scanner.nextLine();
        System.out.println("Password:");
        String password=scanner.nextLine();
        //write login query of user
        String login_query="SELECT * FROM User WHERE email=?AND password =?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(login_query);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            //we are used execute query to retrive data from database
            ResultSet resultSet =preparedStatement.executeQuery();
          //we checked the  table in whic entry are  come or not
            //entry are  come mean user exist and he is enter the true email and password
            if(resultSet.next())
            {
                //we return the email agr entry mili hai
                return email;
            }
            else {
                return null; //agr entry nhi mili
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null;
    }
//this mehod shows the the user is exist or not
    public boolean user_exist(String email)
    {
        String query="SELECT *FROM user Where email=?";
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

}
