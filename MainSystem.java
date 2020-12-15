//This class overrides existing files and store new updates

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * MainSystem class to instantiate an object to write new/updated information into their respective files
 */
public class MainSystem implements java.io.Serializable{
    transient Scanner sc = new Scanner(System.in);

    /**
     * Add/Updates information of a student's access period into a file
     * @param window The AccessWindow object to be added into the file storing access periods of students
     */
    public void uptAccPrd(AccessWindow window){
        try {
            FileOutputStream fOut = new FileOutputStream("Access Period.ser");
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);
            obOut.writeObject(window);
            obOut.close();
            fOut.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds/Update information of a student into a file
     * @param ipList The list of students objects to be added into a file
     */
    public void uptStud(List<Student> ipList){ //updates the total number of students
        try {
            FileOutputStream fOut = new FileOutputStream("Student Personal Dets.ser");
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            for(Student s : ipList){
                obOut.writeObject(s);
            }
            obOut.close();
            fOut.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds/Updates information of courses into a file
     * @param ipList The list of courses objects to be added into a file
     */
    public void uptCourseFile(List<Courses> ipList){ //update course file
        try {
            FileOutputStream fOut = new FileOutputStream("Courses.ser"); //this file also contains all the students who are in the course
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            for(Courses cou : ipList){
                obOut.writeObject(cou);
            }
            obOut.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add/Updates information on indexes into a file
     * @param ipList The list if indexes objects to be written into a file
     */
    public void uptIndexFile(List<ArrayList<Indexes>> ipList){ //updates indexes file
        try {
            FileOutputStream fOut = new FileOutputStream("Indexes.ser"); //this file also contains all the students who are in the course
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            for(ArrayList arr : ipList){
                obOut.writeObject(arr);
            }
            obOut.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
