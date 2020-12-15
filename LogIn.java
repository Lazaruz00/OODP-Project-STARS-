//This class will read input data from user and authenticate login

import java.io.Console;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Login class to instantiate an object which validates the login credentials of a user
 */
public class LogIn{
    private List<String> studID = new ArrayList<>();
    private List<String> studPwd = new ArrayList<>();
    private List<String> adminID = new ArrayList<>();
    private List<String> adminPwd = new ArrayList<>();
    private HashPassword hasher = new HashPassword();
    private CompileData compileStudent = new CompileData();
    private CompileData compileAdmin = new CompileData();
    Console con = System.console();

    /**
     * Authenticate the login username and password of a user
     * @param ipUserID The username of user account to be validated
     * @param ipUserPwd The password of user account to be validated
     * @return A integer representing student or admin and depending on whether username and password matches in database
     * @throws NoSuchAlgorithmException When an algorithm is unavailable in environment
     */
    public int authenticate(String ipUserID, String ipUserPwd, int choice) throws NoSuchAlgorithmException {
        int indexOf, count, typeOfUser;
        count = 2;
        String savedHashedPwd, ipHashedPwd;
        compileStudent.getStudentData();
        compileAdmin.getAdminData();
        studID = compileStudent.getStudIDList();
        studPwd = compileStudent.getStudPwdList();
        adminID = compileAdmin.getAdminIDList();
        adminPwd = compileAdmin.getAdminPwdList();


        if(studID.contains(ipUserID) & choice == 1) {
            indexOf = studID.indexOf(ipUserID);
            savedHashedPwd = studPwd.get(indexOf);        //get the saved hashed password for authentication
            ipHashedPwd = hasher.HexString(hasher.SHA256(ipUserPwd));
            typeOfUser = 1;
        } else if (adminID.contains(ipUserID) & choice == 2) {
            indexOf = adminID.indexOf(ipUserID);
            savedHashedPwd = adminPwd.get(indexOf);        //get the saved hashed password for authentication
            ipHashedPwd = hasher.HexString(hasher.SHA256(ipUserPwd));
            typeOfUser = 2;
        } else {
            System.out.println("\n======================================");
            System.out.println("| UserID does not exist in database. |");
            System.out.println("======================================");
            return -1;
        }
        while(count>0 & !savedHashedPwd.equals(ipHashedPwd)){
            System.out.println("\n==========================================================");
            System.out.println("| You have entered the wrong password! Please try again. |");
            System.out.println("==========================================================\n");
            System.out.print("Enter password: ");
            char[] characs = con.readPassword();  //lets user input password again
            ipHashedPwd = hasher.HexString(hasher.SHA256(new String(characs))); //convert user password to hashed one before comparing
            count-=1;
        }
        if(count==0){
            System.out.println("\nYou have been locked out.\n");
            System.out.println("Terminating session...");
            return -1;
        }
        System.out.println("\n=====================");
        System.out.println("| Login successful! |");
        System.out.println("=====================");
        return typeOfUser;
    }
}

