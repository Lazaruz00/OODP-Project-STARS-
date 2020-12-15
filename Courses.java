import java.util.ArrayList;
import java.util.List;

/**
 * Courses class to instantiate a course
 * which is used to store information on a course, including its
 * course code, name and respective indexes
 */
public class Courses implements SharedMethods, java.io.Serializable{
    private String courseName, courseCode, schoolName;
    public List<Student> studentsIn = new ArrayList<>();
    private CompileData compile = new CompileData();

    //setters

    /**
     * Sets the name of a course
     * @param sCourseName The course name
     */
    public void setCourseName(String sCourseName){
        courseName = sCourseName;
    }

    /**
     * Sets the course code of a course
     * @param cc The course code
     */
    public void setCourseCode(String cc){
        courseCode = cc;
    }

    /**
     * Sets the school name of a course
     * @param SN The school name
     */
    public void setSchoolName(String SN){
        schoolName = SN;
    }

    //getters

    /**
     * To get the course name
     * @return The course name
     */
    public String getCourseName(){
        return courseName;
    }

    /**
     * To get the course code
     * @return The course code
     */
    public String getCourseCode(){
        return courseCode;
    }

    /**
     * To get the school name
     * @return The name of the school of the course
     */
    public String getSchoolName(){
        return schoolName;
    }

    /**
     * Prints a list of stidents registered in a course
     * @param STD A list of all the stduents
     */
    public void printStds(List<Student> STD){
        List<Courses> couList;
        for(Student std : STD){
            couList = std.getMyCourses();
            for(Courses c : couList){
                if(c.getCourseName().equals(courseName)){
                    studentsIn.add(std); //if a student's list of courses contain that course, add that student object into studentsIn list.
                    break; //goes to the next student
                }
            }
        }

        if(!studentsIn.isEmpty()) {
            System.out.println("\nStudents studying " + courseName + " are:");
            for (Student s : studentsIn) {
                System.out.println("Name: "+s.getMyName());
                System.out.println("Gender: "+s.getMyGender());
                System.out.println("Nationality: "+s.getMyNation());
                System.out.println();
            }
        }
        else{
            System.out.println("\n========================================");
            System.out.println("| There are no students taking "+courseName+". |");
            System.out.println("========================================");
        }
    }
}
