//this class is to create existing 15 students and 3 admins

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a class that is used for initializing a STARS database of students, admin, courses and indexes
 */
public class CreateExisting implements java.io.Serializable{
    private HashPassword hasher = new HashPassword();
    transient Scanner sc = new Scanner(System.in);
    private List<Indexes> eachCourseIndexArr;
    RequestInfo request = new RequestInfo();

    /**
     * Add students into database
     * @param initialize The choice to initialize a default list of students for testing
     * @throws IOException When there is an error in reading or writing into file
     */
    public void createForStudents(boolean initialize) throws IOException {
        try {
            FileOutputStream fOut = new FileOutputStream("Student Personal Dets.ser");
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            if (initialize){
                for (int i =0; i<15; i++){
                    Student student = new Student();
                    student.setName("s"+Integer.toString(i));
                    student.setMatNum("s"+Integer.toString(i));
                    student.setMyId("s"+Integer.toString(i));
                    student.setMyPwd("s"+Integer.toString(i));
                    student.setGender("M");
                    student.setNat("SG");
                    student.setMyPwd(hasher.HexString(hasher.SHA256(student.getMyPwd()))); //to hash the password
                    obOut.writeObject(student);
                }
                obOut.close();
                fOut.close();
            }else {

                for (int i = 0; i < 15; i++) {
                    Student student = new Student();
                    student.setName(request.readName());
                    student.setMatNum(request.readMatricNum());
                    student.setMyId(request.readID());
                    student.setMyPwd(request.readPwd());
                    student.setGender(request.readGender());
                    student.setNat(request.readNation());
                    student.setMyPwd(hasher.HexString(hasher.SHA256(student.getMyPwd()))); //to hash the password
                    obOut.writeObject(student);
                }
                obOut.close();
                fOut.close();
            }
        } catch(NoSuchAlgorithmException | IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Add admins into database
     * @throws IOException When there is an error in reading or writing into file
     */
    public void createForAdmins() throws IOException {
        try {
            FileOutputStream fOut = new FileOutputStream("Admin Personal Dets.ser");
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            for(int i =0; i<2; i++){
                Admin admin = new Admin();
                admin.setMyId(request.readID());
                admin.setMyPwd(request.readPwd());
                admin.setMyPwd(hasher.HexString(hasher.SHA256(admin.getMyPwd())));// to hash password
                obOut.writeObject(admin);
            }
            obOut.close();
            fOut.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * Add courses into database
     * @param initialize The choice to initialize a default list of courses for testing
     * @throws IOException When there is an error in reading or writing into file
     */
    public void createCourses(boolean initialize) throws IOException {
        try {
            FileOutputStream fOut = new FileOutputStream("Courses.ser"); //this file also contains all the students who are in the course
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);

            if (initialize){
                for (int i = 0; i < 4; i++) {
                    Courses course = new Courses();
                    course.setCourseName("C"+Integer.toString(i));
                    course.setCourseCode("C"+Integer.toString(i));
                    course.setSchoolName("C"+Integer.toString(i));
                    obOut.writeObject(course);
                }
                obOut.close();
                fOut.close();
            }else {
                for (int i = 0; i < 4; i++) {
                    Courses course = new Courses();
                    System.out.print("Enter course name: ");
                    course.setCourseName(sc.nextLine());
                    System.out.print("Enter course code: ");
                    course.setCourseCode(sc.nextLine());
                    System.out.print("Enter course's school: ");
                    course.setSchoolName(sc.nextLine());
                    obOut.writeObject(course);
                }
                obOut.close();
                fOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add indexes into database
     * @throws IOException When there is an error in reading or writing into file
     */
    public void createIndexes() throws IOException{
        try {
            FileOutputStream fOut = new FileOutputStream("Indexes.ser"); //this file also contains all the students who are in the index
            ObjectOutputStream obOut = new ObjectOutputStream(fOut);
            for(int course = 0; course < 4; course++) {
                eachCourseIndexArr = new ArrayList<Indexes>();
                for (int i = 0; i < 2; i++) {
                    Indexes index = new Indexes();
                    System.out.print("Enter course index: ");
                    index.setCourseIndex(sc.nextInt());
                    sc.nextLine(); //eat up input buffer
                    System.out.print("Enter day of lecture: ");
                    index.setLecDay(sc.nextLine());
                    System.out.print("Enter start time of lecture: ");
                    index.setStartTime(sc.nextInt());
                    System.out.print("Enter end time of lecture: ");
                    index.setEndTime(sc.nextInt());
                    index.lessonTimes.add(index.getStartTime());
                    index.lessonTimes.add(index.getEndTime());
                    sc.nextLine(); //eat up input buffer
                    System.out.print("Enter day of tutorial: ");
                    index.setTutLabDay(sc.nextLine());
                    System.out.print("Enter start time of tutorial: ");
                    index.setStartTime(sc.nextInt());
                    System.out.print("Enter end time of tutorial: ");
                    index.setEndTime(sc.nextInt());
                    index.lessonTimes.add(index.getStartTime());
                    index.lessonTimes.add(index.getEndTime());
                    eachCourseIndexArr.add(index); //add current index into a list corresponding to each course
                }
                obOut.writeObject(eachCourseIndexArr);
            }
            obOut.close();
            fOut.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
