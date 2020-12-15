//This class gets requested pre-existing data

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * CompileData class to instantiate an object which reads information from different files
 */
public class CompileData implements java.io.Serializable{
    private List<String> studID = new ArrayList<>();
    private List<String> studPwd = new ArrayList<>();
    private List<Student> studList = new ArrayList<>();
    private List<Admin> adminList = new ArrayList<>();
    private List<String> adminID = new ArrayList<>();
    private List<String> adminPwd = new ArrayList<>();
    private List<Courses> allCourses = new ArrayList<>();
    public List<ArrayList<Indexes>> allCourseIndexes = new ArrayList<>();
    private List<String> courseNames = new ArrayList<>();
    private AccessWindow period = new AccessWindow();

    /**
     * Reads student details from file and saves it into a list
     */
    public void getStudentData() {
        try {
            FileInputStream fileIn = new FileInputStream("Student Personal Dets.ser");
            ObjectInputStream objStreamIn = new ObjectInputStream(fileIn);

            while (true) {
                Student student = (Student) objStreamIn.readObject();
                if (student == null) {
                    break;
                }
                studList.add(student);
            }
            objStreamIn.close();
            fileIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}
    }

    /**
     * To get the list of all student objects
     * @return The list of all student objects
     */
    public List<Student> getStudList(){
        return studList;
    }

    /**
     * To get the list of all students' username
     * @return The list of all usernames of student accounts
     */
    public List<String> getStudIDList(){
        for(Student s : studList){
            studID.add(s.getMyId());
        }
        return studID;
    }

    /**
     * To get the list of all students' password
     * @return The list of all passwords of student accounts
     */
    public List<String> getStudPwdList(){
        for(Student s : studList){
            studPwd.add(s.getMyPwd());
        }
        return studPwd;
    }

    /**
     * Reads admin details from file and saves it into a list
     */
    public void getAdminData() {
        try {
            FileInputStream fileIn = new FileInputStream("Admin Personal Dets.ser");
            ObjectInputStream objStreamIn = new ObjectInputStream(fileIn);

            while (true) {
                Admin admin = (Admin) objStreamIn.readObject();
                if (admin == null) {
                    break;
                }
                adminList.add(admin);
            }
            objStreamIn.close();
            fileIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}
    }

    /**
     * To get the list of all admin objects
     * @return The list of all admin objects
     */
    public List<Admin> getAdminList(){
        return adminList;
    }

    /**
     * To get the list of all admins' username
     * @return The list of all usernames of admin accounts
     */
    public List<String> getAdminIDList(){
        for(Admin a : adminList){
            adminID.add(a.getMyId());
        }
        return adminID;
    }

    /**
     * To get the list of all admins' password
     * @return The list of all passwords of admin accounts
     */
    public List<String> getAdminPwdList(){
        for(Admin a : adminList){
            adminPwd.add(a.getMyPwd());
        }
        return adminPwd;
    }

    /**
     * Reads course details from file and saves it into a list
     */
    public void getCourseData() {
        try {
            FileInputStream fileIn = new FileInputStream("Courses.ser");
            ObjectInputStream objStreamIn = new ObjectInputStream(fileIn);

            while (true) {
                Courses course = (Courses) objStreamIn.readObject();
                if (course == null) {
                    break;
                }
                allCourses.add(course);
            }
            objStreamIn.close();
            fileIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}
    }

    /**
     * To get the list of all courses
     * @return The list of all courses
     */
    public List<Courses> getAllCourses(){
        return allCourses;
    }

    /**
     * To get the list of all the names of courses available in STARS
     * @return The list of all course names
     */
    public List<String> getCourseNames(){
        for(Courses cou : allCourses){
            courseNames.add(cou.getCourseName());
        }
        return courseNames;
    }

    /**
     * Reads index number of courses details from file and saves it into a list
     */
    public void getIndexData() {
        try {
            FileInputStream fileIn = new FileInputStream("Indexes.ser");
            ObjectInputStream objStreamIn = new ObjectInputStream(fileIn);

            while (true) {
                ArrayList eachCourseIndexList = (ArrayList) objStreamIn.readObject();
                if (eachCourseIndexList == null) {
                    break;
                }
                allCourseIndexes.add(eachCourseIndexList);
            }
            objStreamIn.close();
            fileIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}
    }

    /**
     * To get the list of all indexes objects from all the courses
     * @return The list of all indexes
     */
    public List<ArrayList<Indexes>> getAllCourseIndexes(){
        return allCourseIndexes;
    }

    /**
     * Reads access period details from file and saves it into a list
     */
    public void readAccPrd(){
        try {
            FileInputStream fileIn = new FileInputStream("Access Period.ser");
            ObjectInputStream objStreamIn = new ObjectInputStream(fileIn);

            while (true) {
                AccessWindow window = (AccessWindow) objStreamIn.readObject();
                if (window == null) {
                    break;
                }
                period = window;
            }
            objStreamIn.close();
            fileIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {}
    }

    /**
     * To get the list of all the students' access period
     * @return The AccessWindow object which stores the access period of a student
     */
    public AccessWindow getPeriod(){return period;}

}
