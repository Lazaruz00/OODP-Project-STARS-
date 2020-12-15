//This class is to show menus for students, admins, courses and indexes

import java.util.ArrayList;
import java.util.List;

/**
 * ShowMenus class instantiates an object which displays the respective menus
 * for a user to aid them in the navigation of STARS
 */
public class ShowMenus implements java.io.Serializable{
    /**
     * Displays the menu for a student to navigate STARS
     * @param ipUserID The username of a student's account
     */
    public void showStudentMenu(String ipUserID){
        System.out.println();
        System.out.println("===================Welcome, "+ipUserID+"!=============================================");
        System.out.println("||             What would you like to do today?                                    ||");
        System.out.println("||                    1. Add Course                                                ||");
        System.out.println("||                    2. Drop Course                                               ||");
        System.out.println("||                    3. Check courses registered                                  ||");
        System.out.println("||                    4. Check indexes registered                                  ||");
        System.out.println("||                    5. Check vacancy of index                                    ||");
        System.out.println("||                    6. Change index of course                                    ||");
        System.out.println("||                    7. Print students by index                                   ||");
        System.out.println("||                    8. Swap index number with another student                    ||");
        System.out.println("||                    9. Print my timetable                                        ||");
        System.out.println("||                    10. Quit                                                     ||");
        System.out.println("=====================================================================================");
        System.out.print("                        Choice: ");
    }

    /**
     * Displays the menu for an admin to navigate STARS
     * @param ipUserID The username of an admin account
     */
    public void showAdminMenu(String ipUserID){
        System.out.println();
        System.out.println("===================Welcome, "+ipUserID+"!===================");
        System.out.println("||             What would you like to do today?            ||");
        System.out.println("||                    1. Edit access period                ||");
        System.out.println("||                    2. Add Course                        ||");
        System.out.println("||                    3. Update Course                     ||");
        System.out.println("||                    4. Add a student                     ||");
        System.out.println("||                    5. Check vacancy of class(index)     ||");
        System.out.println("||                    6. Print students by index           ||");
        System.out.println("||                    7. Print students by course          ||");
        System.out.println("||                    8. Quit                              ||");
        System.out.println("=============================================================");
        System.out.print("                        Choice: ");
    }

    /**
     * Displays the courses available
     */
    public void showCourseMenu(){
        CompileData com = new CompileData();
        com.getCourseData(); //reads in course file
        List<Courses> courses = com.getAllCourses();
        String cName;
        System.out.println("\n====================  Course Menu  =======================");
        for(int i=0; i<courses.size(); i++){
            cName = courses.get(i).getCourseName();
            System.out.println("                 "+(i+1)+". "+cName);
        }
        System.out.println("                 "+(courses.size()+1)+". Quit");
        System.out.println("==========================================================");
        System.out.print("                       Choice: ");
    }

    /**
     * Displays the indexes available for selected course
     * @param couPos The array position of Courses object from a list of courses
     */
    public void showIndMenu(int couPos){
        CompileData com = new CompileData();
        com.getIndexData();
        List<ArrayList<Indexes>> indArr = com.getAllCourseIndexes();
        List<Indexes> selInd = indArr.get(couPos);
        int indNum, LSt, LEnd, TSt, TEnd;
        String Lday, Tday;
        System.out.println("\n================ Indexes ==================");
        for(int i=0; i<selInd.size(); i++){
            indNum = selInd.get(i).getCourseIndex();
            LSt = selInd.get(i).getLessonTimes().get(0);
            LEnd = selInd.get(i).getLessonTimes().get(1);
            TSt = selInd.get(i).getLessonTimes().get(2);
            TEnd = selInd.get(i).getLessonTimes().get(3);
            Lday = selInd.get(i).getLecDay();
            Tday = selInd.get(i).getTutLabDay();
            System.out.println("              "+(i+1)+". "+indNum);
            System.out.println("     Lecture timing: "+Lday+"("+LSt+"-"+LEnd+")");
            System.out.println("    Tutorial timing: "+Tday+"("+TSt+"-"+TEnd+")");
            System.out.println();
        }
        System.out.println("              "+(selInd.size()+1)+". Quit");
        System.out.println("===========================================");
        System.out.print("               Choice: ");

    }

    /**
     * Displays the menu when updating information on courses or indexes
     */
    public void updateMenu(){
        System.out.println();
        System.out.println("\n============== Update options ==============");
        System.out.println("||           1. Change course code        ||");
        System.out.println("||                                        ||");
        System.out.println("||           2. Change index number       ||");
        System.out.println("||                                        ||");
        System.out.println("||           3. Add index/classes         ||");
        System.out.println("||                                        ||");
        System.out.println("||           4. Quit                      ||");
        System.out.println("============================================");
        System.out.print("                  Choice: ");
    }

    /**
     * Displays the courses a student can swap
     * @param std A Student object
     */
    public void swapMenu(Student std){
        int i;
        System.out.println("\n"+std.getMyName()+" is studying:");
        for(i = 0; i<std.getMyIndexes().size(); i++){
            String courseName = std.getMyCourses().get(i).getCourseName();
            int courseInd = std.getMyIndexes().get(i).getCourseIndex();
            System.out.print((i+1)+". "+courseName+": ");
            System.out.println(courseInd);
        }
        System.out.println((i+1)+". Quit");
    }
}

