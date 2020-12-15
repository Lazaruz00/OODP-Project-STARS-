import java.util.ArrayList;
import java.util.List;

/**
 * Indexes class to instantiate an index of a course which stores the details
 * and information pertaining to an index
 */
public class Indexes implements SharedMethods, java.io.Serializable{
    private int courseIndex, startTime, endTime;
    private int vacancy = 10;
    private String LecDay, TutLabDay;
    public List<Student> studentsIn = new ArrayList<>();
    public List<Integer> lessonTimes = new ArrayList<>();
    public List<Student> waitList = new ArrayList<>();

    //setters
    /**
     * Sets the index for a course
     * @param input The index number of a course
     */
    public void setCourseIndex(int input){
        courseIndex = input;
    }

    /**
     * Sets the lesson start time for an index
     * @param input The start time of the lesson in 24hr format
     */
    public void setStartTime(int input){
        startTime = input;
    }

    /**
     * Sets the lesson end time for an index
     * @param input The end time of the lesson in 24hr format
     */
    public void setEndTime(int input){
        endTime = input;
    }

    /**
     * Sets the lecture day of an index
     * @param input The day of the lesson (eg: Monday)
     */
    public void setLecDay(String input){
        LecDay = input;
    }

    /**
     * Sets the tutorial/lab day for an index
     * @param input The day of the index's tutorial or lab session (eg: Tuesday)
     */
    public void setTutLabDay(String input){
        TutLabDay = input;
    }

    /**
     * Sets the number of vacancies in an index
     * @param input The number of vacancies available in the index
     */
    public void setVacancy(int input) {
        vacancy = input;
    }

    /**
     * Adds a student to this index's waitlist
     * @param stud A Student object to be queued into a witlist for an oversubscribed course/index
     */
    public void addWaitL(Student stud){
        waitList.add(stud);
    }

    /**
     * Sets the lesson timings
     * @param time The lesson timing in 24hr format
     */
    public void addLssTimes(int time){
        lessonTimes.add(time);
    }

    //getters

    /**
     * To get the start time of a lesson
     * @return The start time of a lesson
     */
    public int getStartTime(){
        return startTime;
    }

    /**
     * To get the end time of a lesson
     * @return The end time of a lesson
     */
    public int getEndTime(){
        return endTime;
    }

    /**
     * To get the number of vacancies of an index
     * @return The number of vacancies of an index
     */
    public int getVacancy() {
        return vacancy;
    }

    /**
     * To get the index number of a course
     * @return The index number of a course
     */
    public int getCourseIndex(){
        return courseIndex;
    }

    /**
     * To get the day of lecture of an index
     * @return The lecture day
     */
    public String getLecDay(){return LecDay;}

    /**
     * To get the day of tutorial or lab of an index
     * @return The tutorial/lab day
     */
    public String getTutLabDay(){return TutLabDay;}

    /**
     * To get the lesson timings of an index
     * @return A list of all the lesson timings (start time - end time)
     */
    public List<Integer> getLessonTimes(){return lessonTimes;}

    /**
     * To get the list of students in a waitlist for the index
     * @return The list of student objects in waitlist
     */
    public List<Student> getWaitL(){
        return waitList;
    }

    /**
     * To get the list of students in the index
     * @return The list of students in the index
     */
    public List<Student> getStudentsIn() {
        return studentsIn;
    }

    /**
     * Prints a list of students registered for this index
     * @param STD The list of student objects in the index
     */
    public void printStds(List<Student> STD){
        List<Indexes> sIdxList;
        for(Student s : STD){
            sIdxList = s.getMyIndexes();
            for(Indexes idx : sIdxList){
                if(idx.getCourseIndex()==courseIndex){
                    studentsIn.add(s); //add the student studying this index into the list studentsIn.
                    break; //go to the next student
                }
            }
        }
        if(!studentsIn.isEmpty()) {
            System.out.println("\nStudents studying index " + courseIndex + " are: ");
            for (Student s : studentsIn) {
                System.out.println("Name: "+s.getMyName());
                System.out.println("Gender: "+s.getMyGender());
                System.out.println("Nationality: "+s.getMyNation());
                System.out.println();
            }
        }
        else{
            System.out.println("\n=========================================");
            System.out.println("| There are no students studying "+courseIndex+". |");
            System.out.println("=========================================");
        }
    }

    /**
     * Prints a list of students in a waitlist
     */
    public void printWL(){
        System.out.println("\nThe students in the wait list are: ");
        for(Student s : waitList){
            System.out.println(s.getMyName());
        }
    }

}
