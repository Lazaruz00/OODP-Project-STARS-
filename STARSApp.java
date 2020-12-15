import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


/**
 * Main driver class which validates the login of a user
 * and redirects the user to their respective menus and available choices
 * to navigate the STARS course registration system
 */
public class STARSApp {
    /**
     * Main method to instantiate system objects and call their respective methods
     * for user to use the STARS registration system
     * @param args Unused, there are no command line arguments
     * @throws IOException When a file cannot be found
     * @throws NoSuchAlgorithmException When an algorithm cannot be found in the environment
     * @throws ParseException When there is an error in parsing
     */

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException {
        //----------------------Create data for existing students and admins. Run this only once---------------------//

        //var create2 = new CreateExisting();
        //create2.createForAdmins();
        //var create1 = new CreateExisting();
        //create1.createForStudents(false);
        //var createCourses = new CreateExisting();
        //createCourses.createCourses(false);
        //var createIndexes = new CreateExisting();
        //createIndexes.createIndexes();

        //-----------------------------------------------------------------------------------------------------------//

        //=======================for first test run (comment out accordingly)======================//
        //=======================Mainly for Github usage==========================================//
//        Scanner sc = new Scanner(System.in);
//        Admin administrator = new Admin();
//        AccessWindow window = new AccessWindow();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date TdyDate;
//        LocalDate today = LocalDate.now();
//        String todayDateString = new String(String.valueOf(today));
//        TdyDate = sdf.parse(todayDateString);
//        System.out.print("Enter start date: ");
//        String sDate = sc.nextLine();
//        System.out.println("Enter end date: ");
//        String eDate = sc.nextLine();
//        administrator.editAccessPrd(sDate, eDate);

      //========================for second test run (comment out accordingly)===================//

        AccessWindow window = new AccessWindow();
        CompileData com = new CompileData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date TdyDate;
        com.readAccPrd();
        window = com.getPeriod();
        LocalDate today = LocalDate.now();
        String todayDateString = new String(String.valueOf(today));
        TdyDate = sdf.parse(todayDateString);
        Scanner sc = new Scanner(System.in);

        System.out.println("=============================");
        System.out.println("|                           |");
        System.out.println("| STARS Registration System |");
        System.out.println("|                           |");
        System.out.println("=============================");

        System.out.println("\nLogin as:\n");
        System.out.println("1. Student");
        System.out.println("2. Admin\n");
        System.out.println("Enter choice: ");
        int choiceSA;
        choiceSA = sc.nextInt();
        sc.nextLine();

        if(TdyDate.compareTo(window.getStartDate())>=0 & TdyDate.compareTo(window.getEndDate())<=0){
//            declare variables
            int choice, result, choiceTries = 0;
            String userID, userPwd;
            Console con = System.console();
            LogIn logIn = new LogIn();


            System.out.print("Enter User ID: ");
            userID = sc.nextLine();
            System.out.print("Enter password: ");
            char[] charPwd = con.readPassword();
            userPwd = new String(charPwd);
            result = logIn.authenticate(userID, userPwd, choiceSA);

            if(result==1){
                int Pos=0;
                com.getStudentData();
                List<Student> stdList = com.getStudList();
                for(Student s: stdList){
                    if(s.getMyId().equals(userID)){
                        break;
                    }
                    Pos+=1;
                }
                stdList.get(Pos).newStud(userID);

            }
            else if(result==2){
                int Pos=0;
                com.getAdminData();
                List<Admin> admList = com.getAdminList();
                for(Admin a : admList){
                    if(a.getMyId().equals(userID)){
                        break;
                    }
                    Pos+=1;
                }
                admList.get(Pos).newAdmin(userID);
            }
        }
        else{
            System.out.println("\nToday is not within the access period!");
        }

    }
}

