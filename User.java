import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User implements java.io.Serializable{
    private String myId, myPwd;

    /**
     * To get the username of a user
     * @return The username of a user account
     */
    public String getMyId() {
        return myId;
    }

    /**
     * To get the password of a user's account
     * @return The password of a user account
     */
    public String getMyPwd() {
        return myPwd;
    }

    /**
     * To set a user's account username
     * @param myId The username of a user account
     */
    public void setMyId(String myId) {
        this.myId = myId;
    }

    /**
     * To set a user's account password
     * @param myPwd The password of a user account
     */
    public void setMyPwd(String myPwd) {
        this.myPwd = myPwd;
    }

    /**
     * Adds a new course
     * @param couList The list courses
     * @param allIndList The list of all indexes
     */
    public abstract void addCourse(List<Courses> couList, List<ArrayList<Indexes>> allIndList);

    /**
     * Prints the student list of an index
     * @param p_stdList The list of students
     * @param p_couList The list of all courses
     * @param p_allIndList The list of all indexes
     */
    public void printStudByIndex(List<Student> p_stdList, List<Courses> p_couList, List<ArrayList<Indexes>> p_allIndList){
        ShowMenus menu = new ShowMenus();
        Scanner sc = new Scanner(System.in);
        int couChoice, maxCNum, maxINum, indexChoice;
        do{
            System.out.println("\nSelect a course:");
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt(); //asks user to select the course he wants to add. Arg for
            maxCNum = p_couList.size();
            if(couChoice<maxCNum+1){ // if inputChoice>=maxCNum+1, it's indicating that user wants to quit

                List<Indexes> selIndL = p_allIndList.get(couChoice-1); //getting the list of indexes for selected course
                maxINum = selIndL.size(); //need to get total number of indexes for chosen course

                do {
                    System.out.println("\nSelect an index:");
                    menu.showIndMenu(couChoice-1); //show index menu of chosen course
                    indexChoice = sc.nextInt();
                    if(indexChoice<maxINum+1){
                        Indexes chInd = selIndL.get(indexChoice-1); //the particular index has been chosen
                        chInd.printStds(p_stdList);
                    } //if indexChoice>=maxINum+1, it is indicating that user wants to quit

                }while(indexChoice<maxINum+1);
            }
        }while (couChoice<maxCNum+1);
    }

    /**
     * Prints the number of vacancies in an class
     * @param p_couList The list of courses
     * @param p_allIndList The list of all indexes
     */
    public void checkVacIndex(List<Courses> p_couList, List<ArrayList<Indexes>> p_allIndList){
        Scanner sc = new Scanner(System.in);
        int couChoice, maxCNum, indexChoice, maxINum;
        ShowMenus menu = new ShowMenus();

        maxCNum = p_couList.size(); //to get total number of all courses
        do{
            System.out.println("\nSelect a course:");
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt(); //asks user to select the course he wants to add. Arg for
            if(couChoice<maxCNum+1){ // if inputChoice>=maxCNum+1, it's indicating that user wants to quit

                List<Indexes> selIndL = p_allIndList.get(couChoice-1); //getting the list of indexes for selected course
                maxINum = selIndL.size(); //need to get total number of indexes for chosen course

                do {
                    System.out.println("\nSelect the index for which you want to check the vacancy:");
                    menu.showIndMenu(couChoice-1);
                    indexChoice = sc.nextInt();
                    if(indexChoice<maxINum+1){
                        int couInd = selIndL.get(indexChoice-1).getCourseIndex();
                        int vac = selIndL.get(indexChoice-1).getVacancy();
                        System.out.println("\nVacancy of "+couInd+": "+vac+" / "+Integer.toString(10));
                    } //if indexChoice>=maxINum+1, it is indicating that user wants to quit
                }while(indexChoice<maxINum+1);
            }
        }while (couChoice<maxCNum+1);
    }
}
