import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Admin class to instantiate an admin using STARS
 */
public class Admin extends User implements java.io.Serializable{
    /**
     * Instantiates a new admin after successful login into STARS
     * and displays the menu available for STARS navigation. The admin
     * will be able to add/update a course, check the vacancies in an index, edit the access period,
     * and print a student list by course or by an index
     * @param ipUserID The username of an admin account
     * @see #addCourse(List, List)
     * @see #addStudent(List)
     * @see #checkVacIndex(List, List)
     * @see #editAccessPrd(String, String)
     * @see #updateCourse(List, List)
     * @see #printStudByCourse(List, List)
     * @see #printStudByIndex(List, List, List)
     * @throws ParseException When there is an error in parsing
     * @throws NoSuchAlgorithmException When algorithm is unavailable in environment
     */
    public void newAdmin(String ipUserID) throws ParseException, NoSuchAlgorithmException {
        int menuChoice;
        ShowMenus adminMenu = new ShowMenus();
        Scanner sc = new Scanner(System.in);
        do{
            /*By reading in from the file every time the loop repeats, it ensures that all argument are up-to-date with the new file, should there be any changes*/
            CompileData com = new CompileData();
            com.getCourseData(); //to compile info of all courses
            com.getIndexData(); //to compile info of all indexes
            com.getStudentData(); //compile info on all students
            List<Courses> couList = com.getAllCourses();
            List<ArrayList<Indexes>> allIndList = com.getAllCourseIndexes();
            List<Student> stdList = com.getStudList();
            /*---------------------------------------------------------------------------------------------------------------------------------------------------*/

            adminMenu.showAdminMenu(ipUserID);
            menuChoice = sc.nextInt();
            sc.nextLine(); //eat up input buffer

            switch(menuChoice){
                case 1:
                    System.out.print("\nEnter start of access period (yyyy-MM-dd): ");
                    String stDate = sc.nextLine();
                    System.out.print("\nEnter end of access period (yyyy-MM-dd): ");
                    String endDate = sc.nextLine();
                    editAccessPrd(stDate, endDate);
                    break;
                case 2:
                    addCourse(couList, allIndList);
                    break;
                case 3:
                    updateCourse(couList, allIndList);
                    break;
                case 4:
                    addStudent(stdList);
                    break;
                case 5:
                    checkVacIndex(couList, allIndList);
                    break;
                case 6:
                    printStudByIndex(stdList, couList, allIndList);
                    break;
                case 7:
                    printStudByCourse(stdList, couList);
                    break;
                default:
                    System.out.println("Terminating session...");
            }
        }while(menuChoice<8);

    }

    /**
     * Updates the access period of a student
     * @param stDate The access period start date
     * @param endDate The access period end date
     * @return An AccessWindow object with the start and end dates of the access period
     * @throws ParseException When there is an error in parsing date
     */
    public AccessWindow editAccessPrd(String stDate, String endDate) throws ParseException {
        AccessWindow window = new AccessWindow();
        MainSystem ms = new MainSystem();
        Date accessStart, accessEnd;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        accessStart = sdf.parse(stDate);
        window.setStartDate(accessStart); //set start of access period
        accessEnd = sdf.parse(endDate);
        window.setEndDate(accessEnd); // set end of access period

        ms.uptAccPrd(window); //update access period file
        return window;
    }

    /**
     * Adds a new course into STARS for students to register
     * @param couList The list courses
     * @param allIndList The list of all indexes
     * @see #addInd(int)
     */
    public void addCourse(List<Courses> couList, List<ArrayList<Indexes>> allIndList){ //this method adds a course. with each addition, it makes sense to at least add 1 index
        MainSystem ms = new MainSystem();
        ArrayList<Indexes> IndxList;
        Scanner sc = new Scanner(System.in);
        CompileData com = new CompileData();
        com.getCourseData(); //reads in course file
        List<Courses> courses = com.getAllCourses();

        /*Adding course now*/
        Courses cou = new Courses();
        System.out.print("\nEnter course code: ");
        String courseCode;
        courseCode = sc.nextLine();
        //courseCode = courseCode.toUpperCase();
        for(int i=0; i<courses.size(); i++){
            if (courseCode.equals(courses.get(i).getCourseCode())){
                System.out.println("================================");
                System.out.println("|                              |");
                System.out.println("| There is an existing "+courseCode+". |");
                System.out.println("|                              |");
                System.out.println("================================");
                return;
            }
        }
        cou.setCourseCode(courseCode);
        System.out.print("Enter course name: ");
        cou.setCourseName(sc.nextLine());
        System.out.print("Enter school name: ");
        cou.setSchoolName(sc.nextLine());
        couList.add(cou); //to be used to update file using MainSystem

        /*Adding index now*/
        int indexToAdd = 0;
        boolean error = true;
        while(error){
            try{
                System.out.print("\nHow many indexes do you want to add to new course: ");
                indexToAdd = sc.nextInt();
                sc.nextLine();
                error = false;
            }catch(InputMismatchException e){
                System.out.println("\nPlease input valid index number for course.");
                sc.nextLine();

            }
        }
        IndxList = addInd(indexToAdd); //self call internal method addInd()
        allIndList.add(IndxList); //adds the new list of indexes to existing list of arraylists. to be used to update file using MainSystem

        /*update files*/
        ms.uptCourseFile(couList);
        ms.uptIndexFile(allIndList);

        //closure message
        System.out.println("\n==========================================================");
        System.out.println("| The course and index(es) have been added successfully. |");
        System.out.println("==========================================================");
        String cName;
        System.out.println("\nCurrent courses available:");
        for(int i=0; i<couList.size(); i++){
            cName = couList.get(i).getCourseName();
            System.out.println((i+1)+". "+cName);
        }
    }

    /**
     * Updates information of a course in STARS
     * @param couList The list courses
     * @param allIndList The list of all indexes
     * @see #addInd(int)
     */
    public void updateCourse(List<Courses> couList, List<ArrayList<Indexes>> allIndList) { //either change course code, change index numbers or add indexes
        ShowMenus menu = new ShowMenus();
        MainSystem ms = new MainSystem();
        int couChoice, maxCNum, indexChoice, maxINum, uptChoice;
        Courses chCou; //chosen course
        Indexes chInd; // chosen index
        String Cname; //course code of chosen course
        Scanner sc = new Scanner(System.in);

        maxCNum = couList.size(); //to get total number of all courses
        do {
            System.out.println("\nSelect the course you wish to update:");
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt(); //asks user to select the course he wants to add.

            if (couChoice < maxCNum + 1) {  // if inputChoice>=maxCNum+1, it's indicating that user wants to quit
                System.out.println("\nHow would you like to update its information?");
                menu.updateMenu();
                uptChoice = sc.nextInt();
                sc.nextLine(); //eat up input buffer
                switch (uptChoice) {
                    case 1:
                        chCou = couList.get(couChoice - 1); //get chosen course
                        System.out.print("\nEnter old course code "+chCou.getCourseCode()+" to: ");
                        couList.get(couChoice-1).setCourseCode(sc.nextLine());
                        System.out.println("\n==============================================");
                        System.out.println("| Course code has been updated successfully. |");
                        System.out.println("==============================================");
                        ms.uptCourseFile(couList);
                        break;
                    case 2:
                        List<Indexes> selIndL = allIndList.get(couChoice-1); //getting the list of indexes for selected course
                        maxINum = selIndL.size(); //need to get total number of indexes for chosen course
                        do {
                            System.out.println("\nWhich index number would you like to change?");
                            menu.showIndMenu(couChoice-1);
                            indexChoice = sc.nextInt();
                            if(indexChoice<maxINum+1){
                                chInd = selIndL.get(indexChoice-1); //returns an index object
                                System.out.print("\nChange index number from "+chInd.getCourseIndex()+" to: ");
                                allIndList.get(couChoice-1).get(indexChoice-1).setCourseIndex(sc.nextInt());
                                System.out.println("\n===================================================");
                                System.out.println("| The index number has been changed successfully. |");
                                System.out.println("===================================================");
                                /*the first get obtains the list of indexes for selected course. the second get obtains the index whose index is to be changed*/
                            }
                        }while(indexChoice<maxINum+1);//if indexChoice>=maxINum+1, it is indicating that user wants to quit
                        ms.uptIndexFile(allIndList);
                        break;
                    case 3:
                        ArrayList<Indexes> newIndxs;
                        System.out.print("\nHow many indexes do you want to add: ");
                        int addCount = sc.nextInt();
                        newIndxs = addInd(addCount);
                        for(Indexes i : newIndxs){
                            allIndList.get(couChoice-1).add(i); //adding new indexes to the list of existing indexes
                        }
                        ms.uptIndexFile(allIndList);
                        System.out.println("\n=================================================");
                        System.out.println("| All new indexes have been successfully added. |");
                        System.out.println("=================================================");
                        break;
                }
            }
        } while (couChoice < maxCNum + 1);
    }

    /**
     * Adds a new student into the STARS registration system
     * @param exstStd The list of existing students
     * @throws NoSuchAlgorithmException When algorithm is unavailable in environment
     */
    public void addStudent(List<Student> exstStd) throws NoSuchAlgorithmException {
        RequestInfo request = new RequestInfo();
        HashPassword hasher = new HashPassword();
        MainSystem ms = new MainSystem();
        boolean exist = false;

        /*Adding student now*/
        Student student = new Student();
        String newStdName = request.readName();

        for(Student s : exstStd){ //checks if the student already exist in the database
            if(s.getMyName().equals(newStdName)){
                exist = true;
                break;
            }
        }
        if(!exist) {
            student.setName(newStdName);
            student.setMatNum(request.readMatricNum());
            student.setMyId(request.readID());
            student.setMyPwd(request.readPwd());
            student.setGender(request.readGender());
            student.setNat(request.readNation());
            student.setMyPwd(hasher.HexString(hasher.SHA256(student.getMyPwd()))); //to hash the password
            exstStd.add(student); //add new student to list of existing students

            ms.uptStud(exstStd); //update student detail files
            System.out.println("\n==========================================");
            System.out.println("| New student has been added successfully. |");
            System.out.println("============================================");

            /*The list of all students should be displayed after the addition*/
            CompileData com = new CompileData();
            com.getStudentData();
            List<Student> updatedSList = com.getStudList();
            System.out.println("\nCurrently registered students: ");
            for(Student s : updatedSList){
                System.out.println(s.getMyName());
            }
        }
        else{
            System.out.println("\n=====================================================");
            System.out.println("| The student is already registered in the database |");
            System.out.println("=====================================================");
        }
    }

    /**
     * Prints the student list of a course
     * @param p_stdList The list of students
     * @param p_couList The list of all courses
     */
    public void printStudByCourse(List<Student> p_stdList, List<Courses> p_couList){
        ShowMenus menu = new ShowMenus();
        Scanner sc = new Scanner(System.in);
        int couChoice;
        int maxCNum = p_couList.size(); //to get total number of all courses
        do {
            System.out.println("\nSelect a course:");
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt();
            if (couChoice < maxCNum + 1){
                Courses chCou = p_couList.get(couChoice-1);
                chCou.printStds(p_stdList);
            }
        }while(couChoice<maxCNum+1);
    }

    /**
     * Adds new indexes to a list for updating or adding of courses,
     * including the information of an index such as lesson timings
     * @param numInd The index number of a course to be added
     * @return The list of indexes of a course
     */
    private ArrayList<Indexes> addInd(int numInd){ // add onto existing indexes
        Scanner sc = new Scanner(System.in);
        ArrayList<Indexes> indList = new ArrayList<Indexes>();
        List<String> lssType = Arrays.asList("lecture: ", "tutorial/lab: ");
        for(int i=0; i<numInd; i++){
            Indexes ind = new Indexes();
            int indexToAdd = 0;
            boolean error = true;
            while(error){
                try{
                    System.out.print("\nEnter index: ");
                    indexToAdd = sc.nextInt();
                    error = false;
                }catch(InputMismatchException e){
                    System.out.println("\n===============================================");
                    System.out.println("| Please input valid index number for course. |");
                    System.out.println("===============================================");
                    sc.nextLine();

                }
            }
            ind.setCourseIndex(indexToAdd);
            sc.nextLine();
            System.out.print("Enter lecture day: ");
            ind.setLecDay(sc.nextLine());
            System.out.print("Enter tutorial/lab day: ");
            ind.setTutLabDay(sc.nextLine());
            for(int j=0; j<2; j++){
                int startTime = 0;
                error = true;
                while(error){
                    try{
                        System.out.print("Start time of "+lssType.get(j));
                        startTime = sc.nextInt();
                        sc.nextLine();
                        error = false;
                    }catch(InputMismatchException e){
                        System.out.println("\n=============================================");
                        System.out.println("| Please input valid start time (eg: 0830). |");
                        System.out.println("=============================================\n");
                        sc.nextLine();

                    }
                }
                ind.addLssTimes(startTime);
                int endTime = 0;
                error = true;
                while(error){
                    try{
                        System.out.print("End time of "+lssType.get(j));
                        endTime = sc.nextInt();
                        sc.nextLine();
                        error = false;
                    }catch(InputMismatchException e){
                        System.out.println("\n===========================================");
                        System.out.println("| Pl    ease input valid end time (eg: 0930). |");
                        System.out.println("===========================================\n");
                        sc.nextLine();
                    }
                }
                ind.addLssTimes(endTime);
            }
            indList.add(ind);
        }
        return indList;
    }
}
