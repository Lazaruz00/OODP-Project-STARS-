//This class is mainly in charge of receiving inputs from the user regarding his/her ID and password

import java.io.Console;
import java.util.Scanner;

/**
 * RequestInfo class is used by admins to request for student information
 * when adding a new student into the database
 */
public class RequestInfo implements java.io.Serializable{
    //declare input devices
    transient Scanner sc = new Scanner(System.in);
    transient Console con = System.console();

    //declare instance variables
    private String _Name, _MatricNum, _Gender, _ID, _Pwd, _Nation;

    /**
     * To let admin input name of new student
     * @return The name of new student
     */
    public String readName(){
        System.out.print("\nEnter name: ");
        _Name = sc.nextLine();
        return _Name;
    }

    /**
     * To let admin input matriculation number of new student
     * @return The matriculation number of new student
     */
    public String readMatricNum(){
        System.out.print("Enter matriculation number: ");
        _MatricNum = sc.nextLine();
        return _MatricNum;
    }
    /**
     * To let admin input the gender of new student
     * @return The gender of new student
     */
    public String readGender(){
        System.out.print("Enter gender: ");
        _Gender = sc.nextLine();
        return _Gender;
    }
    /**
     * To let admin input username of new student's STARS account
     * @return The username of new student's account
     */
    public String readID(){
        System.out.print("Enter user ID: ");
        _ID = sc.nextLine();
        return _ID;
    }

    /**
     * To let admin input password of new student's STARS account
     * @return The password of new student's account
     */
    public String readPwd(){
        System.out.print("Enter password: ");
        char[] pass = con.readPassword();
        _Pwd = new String(pass);
        return _Pwd;
    }

    /**
     * To let admin input nationality of new student
     * @return The nationality of new student
     */
    public String readNation(){
        System.out.print("Enter nationality: ");
        _Nation = sc.nextLine();
        return _Nation;
    }
}
