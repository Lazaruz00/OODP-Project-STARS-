
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student class to instantiate students who are using STARS
 */
public class Student extends User implements java.io.Serializable{
    //declare variables
    private String myName, myMatricNum, myGender, myNation;
    private List<Courses> myCourses = new ArrayList<>();
    private List<Indexes> myIndexes = new ArrayList<>();
    private List<Courses> coursesWL = new ArrayList<>();
    private List<Indexes> indexWL = new ArrayList<>();

    //getters

    /**
     * To get the name of a student registered in STARS
     * @return The name of a student
     */
    public String getMyName(){ return myName;}
    /**
     * To get the matriculation number of a student registered in STARS
     * @return The matriculation number of a student
     */
    public String getMyMatricNum(){ return myMatricNum;}
    /**
     * To get the gender of a student registered in STARS
     * @return The gender of a student
     */
    public String getMyGender(){ return myGender;}
    /**
     * To get the nationality of a student registered in STARS
     * @return The nationality of a student
     */
    public String getMyNation(){return myNation;}
    /**
     * To get the courses a student has registered for in STARS
     * @return The list of courses registered in STARS
     */
    public List<Courses> getMyCourses(){ return myCourses;}
    /**
     * To get the courses a student has registered for in STARS
     * @return The list of indexes of the courses the student registered in STARS
     */
    public List<Indexes> getMyIndexes(){ return myIndexes;}


    //setters

    /**
     * Sets the name of a student
     * @param name The name of a student
     */
    public void setName(String name){ //only needed for creatingExisting
        myName = name;
    }
    /**
     * Sets the matriculation number of a student
     * @param sMatNo The matriculation number of a student
     */
    public void setMatNum(String sMatNo){myMatricNum=sMatNo;}
    /**
     * Sets the gender of a student
     * @param sG The gender of a student
     */
    public void setGender(String sG){myGender=sG;}
    /**
     * Sets the nationality of a student
     * @param input The nationality of a student
     */
    public void setNat(String input){myNation = input;}
    /**
     * Sets a list of courses of the student
     * @param ipList A list of courses of a student is registered in
     */
    public void setMyCou(List<Courses> ipList){myCourses = ipList;}
    /**
     * Sets a list of indexes of a student
     * @param ipList A list of indexes of a student is registered in
     */
    public void setMyIndx(List<Indexes> ipList){myIndexes = ipList;}

    /**
     * Instantiates a new student after successful login into STARS
     * and displays the menu available for STARS navigation. The student will be able
     * to register/drop a course, change/swap a registered course, check the vacancy of an index
     * and print a list of his registered courses or index. The student will also be
     * able to view his timetable.
     * @param ipUserID The username of a student's account
     * @see #addCourse(List, List)
     * @see #swapWith(Student)
     * @see #printStudByIndex(List, List, List)
     * @see #checkVacIndex(List, List)
     * @see #chgCourseInd(List, List)
     * @see #dropCourse(List, List)
     * @see #printMyCourses()
     * @see #printMyIndexes()
     * @see #printMyTT()
     */
    public void newStud(String ipUserID){
        Scanner sc = new Scanner(System.in);
        ShowMenus studMenu = new ShowMenus();
        int menuChoice;
        do{
            /*By reading in from the file every time the loop repeats, it ensures that all argument are up-to-date with the new file, should there be any changes*/
            CompileData com = new CompileData();
            com.getCourseData(); //to compile info of all courses
            com.getIndexData(); //to compile info of all indexes
            List<Courses> couList = com.getAllCourses();
            List<ArrayList<Indexes>> allIndList = com.getAllCourseIndexes();
            com.getStudentData(); //read in data about students
            List<Student> stdList = com.getStudList();
            /*---------------------------------------------------------------------------------------------------------------------------------------------------*/
            studMenu.showStudentMenu(ipUserID); //shows the menu for students
            menuChoice = sc.nextInt();

            switch(menuChoice){
                case 1:
                    addCourse(couList, allIndList);
                    break;
                case 2:
                    dropCourse(couList, allIndList);
                    break;
                case 3:
                    printMyCourses();
                    break;
                case 4:
                    printMyIndexes();
                    break;
                case 5:
                    checkVacIndex(couList, allIndList);
                    break;
                case 6:
                    List<String> couNames = com.getCourseNames();
                    chgCourseInd(couNames, allIndList);
                    break;
                case 7:
                    printStudByIndex(stdList, couList, allIndList);
                    break;
                case 8:
                    int num = 0;
                    int chosenStd, totStdNum;
                    Student selStd; //selected student
                    System.out.println("\nWhom do you want to swap with?");
                    for(Student s : stdList){
                        System.out.println((num+1) + ". " + s.getMyName());
                        num += 1;
                    }
                    System.out.println((num+1)+". Quit");
                    totStdNum = num;
                    System.out.print("Choice: ");
                    chosenStd = sc.nextInt();
                    if(chosenStd<totStdNum+1) { //if chosenStd == totStdNum, user wants quit
                        selStd = stdList.get(chosenStd-1);
                        swapWith(selStd);
                    }
                    break;
                case 9:
                    printMyTT();
                    break;
                default:
                    System.out.println("Terminating session...");
            }
        }while(menuChoice<10);

    }

    /**
     * Allows a student to add a course to their timetable
     * @param couList The list of courses a student is registered in
     * @param allIndList The list of all the indexes a student is registered in
     * @see #add(int, int, List, List, int)
     */
    public void addCourse(List<Courses> couList, List<ArrayList<Indexes>> allIndList){
        Scanner sc = new Scanner(System.in);
        int couChoice, maxCNum, indexChoice, maxINum;
        ShowMenus menu = new ShowMenus();

        maxCNum = couList.size(); //to get total number of all courses
        do{
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt(); //asks user to select the course he wants to add. Arg for add
            if(couChoice<maxCNum+1){ // if inputChoice>=maxCNum+1, it's indicating that user wants to quit

                List<Indexes> selIndL = allIndList.get(couChoice-1); //getting the list of indexes for selected course
                maxINum = selIndL.size(); //need to get total number of indexes for chosen course

                do {
                    menu.showIndMenu(couChoice-1);
                    indexChoice = sc.nextInt();
                    if(indexChoice<maxINum+1){
                        add(couChoice - 1, indexChoice - 1, couList, allIndList, 0);
                    } //if indexChoice>=maxINum+1, it is indicating that user wants to quit

                }while(indexChoice<maxINum+1);
            }
        }while (couChoice<maxCNum+1);

    }

    /**
     * Allows a student to drop a registered course or a course place on waitlist
     * @param couList The list of courses a student is registered in
     * @param allIndList The list of all the indexes a student is registered in
     * @see #drop(int, int, List)
     */
    public void dropCourse(List<Courses> couList, List<ArrayList<Indexes>> allIndList) {
        Scanner sc = new Scanner(System.in);
        int couChoice, indexChoice, maxCNum, maxINum;
        ShowMenus menu = new ShowMenus();

        List<Indexes> selIndL; //selected index list
        maxCNum = couList.size(); //to get total number of all courses
        do {
            menu.showCourseMenu(); //shows course menus
            couChoice = sc.nextInt(); //asks user to select the course he wants to add
            if (couChoice < maxCNum + 1) { // if inputChoice>=maxCNum+1, it's indicating that user wants to quit

                selIndL = allIndList.get(couChoice - 1); //getting the list of indexes for selected course
                maxINum = selIndL.size(); //need to get total number of indexes for chosen course

                do {
                    menu.showIndMenu(couChoice - 1);
                    indexChoice = sc.nextInt();
                    if (indexChoice < maxINum + 1) {
                        drop(couChoice - 1, indexChoice - 1, allIndList);
                    } //if indexChoice>=maxINum+1, it is indicating that user wants to quit

                } while (indexChoice < maxINum + 1);
            }
        } while (couChoice < maxCNum + 1);
    }

    /**
     * Allows a student to change the index of a registered course,
     * subject to vacancies and clashes in timetable
     * @param couNames The list of courses a student is registered in
     * @param p_allIndList The list of all the indexes a student is registered in
     * @see #change(int, Indexes, Courses, Indexes, int, List)
     */
    public void chgCourseInd(List<String> couNames, List<ArrayList<Indexes>> p_allIndList) {
        Scanner sc = new Scanner(System.in);
        Indexes chosenIndex, toChangeIndex;
        Courses chosenCourse;
        ShowMenus menu = new ShowMenus();

        List<Indexes> selIndL; //selected index list
        int couChoice, indexChoice, i, couPos, maxINum;
        do {
            System.out.println("\nThese are your current registered indexes: ");
            for (i = 0; i < myCourses.size(); i++) {
                System.out.print("" + (i + 1) + ". " + myCourses.get(i).getCourseName() + ": ");
                System.out.println(myIndexes.get(i).getCourseIndex());
            }
            System.out.println("" + (i + 1) + ". Quit");
            System.out.print("Choice: ");
            couChoice = sc.nextInt(); //asks user to select the course he wants to change
            System.out.println();
            if (couChoice < myCourses.size() + 1) {
                chosenCourse = myCourses.get(couChoice - 1);
                toChangeIndex = myIndexes.get(couChoice - 1); //the index that the student wants to remove

                couPos = couNames.indexOf(chosenCourse.getCourseName()); //because reading in the file keeps returning objects of different reference names,
                                                                         // we use course names as an anchor to get the index position of a course in the written file

                selIndL = p_allIndList.get(couPos); //to get the list of indexes for the selected course
                maxINum = selIndL.size();
                do {
                    System.out.println("\nSelect the index you want to change to:");
                    menu.showIndMenu(couPos);
                    indexChoice = sc.nextInt();
                    if (indexChoice < maxINum + 1) {
                        chosenIndex = p_allIndList.get(couPos).get(indexChoice-1); //the index that the student wants take
                        if (toChangeIndex.getCourseIndex() != chosenIndex.getCourseIndex()) { //makes sure the student doesn't exchange with the same index number
                            change(couChoice-1, toChangeIndex, chosenCourse, chosenIndex, couPos, p_allIndList);
                        } else {
                            System.out.println("\n============================================================================");
                            System.out.println("| You have already registered for that index. Please choose another index. |");
                            System.out.println("============================================================================");
                        }
                    } //if indexChoice>=maxINum+1, it is indicating that user wants to quit
                } while (indexChoice < maxINum + 1);
            }
        } while (couChoice < myCourses.size() + 1);
    }

    /**
     * Allows 2 students registered in the same course to swap index,
     * subject to no clashes in both students' timetable
     * @param p_std The username of student whose index is to be swapped with
     * @see #swap(Student, int)
     */
    public void swapWith(Student p_std){
        ShowMenus menu = new ShowMenus();

        Scanner sc = new Scanner(System.in);
        int selcInd;
        int maxINum = p_std.getMyIndexes().size();
        menu.swapMenu(p_std);
        System.out.print("Choice: ");
        selcInd = sc.nextInt(); //selected index choice number also corresponds to the course position in swapee's courseList
        if(selcInd<maxINum+1) { //if selcInd >= maxINum+1, the user wants to quit
            swap(p_std, selcInd);
        }
    }

    /**
     * Prints a student's registered indexes
     */
    public void printMyIndexes(){
        if(!myIndexes.isEmpty()){
            System.out.println("\nMy registered indexes are: \n");
            int index, listSize = myIndexes.size();
            String courseCode, courseName;
            for (int i = 0; i < listSize; i++){
                index = myIndexes.get(i).getCourseIndex();
                courseCode = myCourses.get(i).getCourseCode();
                courseName = myCourses.get(i).getCourseName();
                System.out.println(i+1 + ") " + courseCode + " " + courseName + " " + index);
            }
            System.out.println();
        }else{
            System.out.println("\n============================================");
            System.out.println("| You have not registered for any indexes. |");
            System.out.println("============================================");
        }
    }

    /**
     * Prints a student's registered courses
     */
    public void printMyCourses(){
        if(!myCourses.isEmpty()) {
            System.out.println("\nMy registered course are: ");
            for (Courses cou : myCourses) {
                System.out.println(cou.getCourseName());
            }
            System.out.println();
        }else{
            System.out.println("\n============================================");
            System.out.println("| You have not registered for any courses. |");
            System.out.println("============================================");
        }
    }

    /**
     * Checks a stydent's timetable for clashes before registering for a course
     * @param p_CoursePos The index position of the courses stored in file
     * @param p_IndexPos The index position of the indexes stored in file
     * @param p_couList The list of all courses
     * @param p_allIndList The list of all indexes
     * @param typeIncoming An integer which tracks where this method is called from
     * @see #add_Add2Waitlist(List, int, int, Indexes)
     */
    private void add(int p_CoursePos, int p_IndexPos, List<Courses> p_couList, List<ArrayList<Indexes>> p_allIndList, int typeIncoming){ //this functions checks if there are any clashes, and adds if there is none
        Scanner sc = new Scanner(System.in);

        int choice=-1;
        Indexes chosenIndex;
        Courses chosenCourse;
        boolean success;
        chosenCourse = p_couList.get(p_CoursePos);
        chosenIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos); //gets the index object of chosen course

        if(typeIncoming!=1) {// for addCourse, check if the course to be added has already been registered by the student
            String courseCode;
            courseCode = chosenCourse.getCourseCode();
            //courseCode = courseCode.toUpperCase();
            for (int i = 0; i < myCourses.size(); i++) {
                if (courseCode.equals(myCourses.get(i).getCourseCode())) {
                    System.out.println("\n===========================================");
                    System.out.println("| You have already registered for " + courseCode + ". |");
                    System.out.println("===========================================");
                    return;
                }
            }
        }

        CheckClash checkClash = new CheckClash();
        if (myIndexes.isEmpty()) {
            if(chosenIndex.getVacancy()>0) {    //check if chosen index got vacancy or not
                myIndexes.add(chosenIndex);
                myCourses.add(chosenCourse);

                /*update file*/
                addUpdate(p_CoursePos, p_IndexPos, p_allIndList);
                System.out.println("\n===================================================");
                System.out.println("| Your chosen course has been added successfully! |");
                System.out.println("===================================================\n");
            }else{
                if(typeIncoming!=1) { //if add is coming from addCourse, print the message below. if add is being called from change(), don't print the message below as it will be repeated
                                      //typeIncoming determines which action is taken
                    System.out.println("\n========================================================================");
                    System.out.println("| " + chosenIndex.getCourseIndex() + " is fully subscribed. Do you wish to be added to the wait list? |");
                    System.out.println("=========================================================================");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.print("Choice: ");
                    choice = sc.nextInt();
                }
                else{
                    choice=1;
                }
                if(choice==1){add_Add2Waitlist(p_allIndList, p_CoursePos, p_IndexPos, chosenIndex);}
            }
        } else {
            success = checkClash.checkOwnClash(myIndexes, chosenIndex);
            if (success) {
                if(chosenIndex.getVacancy()>0) {
                    myIndexes.add(chosenIndex);
                    myCourses.add(chosenCourse);
                    /*update file*/
                    addUpdate(p_CoursePos, p_IndexPos, p_allIndList);
                    System.out.println("===================================================");
                    System.out.println("| Your chosen course has been added successfully! |");
                    System.out.println("===================================================");
                }
                else{
                    if(typeIncoming!=1) {
                        System.out.println("\n========================================================================");
                        System.out.println("| " + chosenIndex.getCourseIndex() + " is fully subscribed. Do you wish to be added to the wait list? |");
                        System.out.println("========================================================================");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Choice: ");
                        choice = sc.nextInt();
                    }
                    else{
                        choice=1;
                    }
                    if(choice==1){add_Add2Waitlist(p_allIndList, p_CoursePos, p_IndexPos, chosenIndex);}
                }
            }
        }
    }

    /**
     * Adds a course to waitlist depending on vacancy of course a student is trying to register for
     * @param p_allIndList The list of all indexes
     * @param p_CouPos The index position of course in file
     * @param p_IndPos The index position of indexes in file
     * @param choIndex The index number of course where student is to be added into waitlist
     */
    private void add_Add2Waitlist(List<ArrayList<Indexes>> p_allIndList, int p_CouPos,
                                  int p_IndPos, Indexes choIndex){
        MainSystem ms = new MainSystem();
        CompileData com = new CompileData();
        SendMailTLS mail = new SendMailTLS();
        com.getStudentData();
        com.getCourseData();
        List<Student> stdList = com.getStudList();
        List<Courses> coursesList = com.getAllCourses();

        for(Student s:stdList) { //need to add student to index wait list
            if(s.getMyName().equals(myName)){
                p_allIndList.get(p_CouPos).remove(p_IndPos);
                choIndex.addWaitL(s);
                p_allIndList.get(p_CouPos).add(p_IndPos, choIndex);
                break;
            }
        }

        //Add the courses and indexes this student is waiting for on to the list
        Courses chosenCou = coursesList.get(p_CouPos);
        coursesWL.add(chosenCou);
        indexWL.add(choIndex);

        System.out.println("\n========================================");
        System.out.println("| You have been added to the wait list |");
        System.out.println("========================================");
        /*Notify student*/
        int indxNum = choIndex.getCourseIndex();
        String courseName = coursesList.get(p_CouPos).getCourseName();
        mail.addWaitListEmail(myName, indxNum, courseName);

        ms.uptIndexFile(p_allIndList);//update index file
    }

    /**
     * Checks if a student has registered for a course he is trying to drop
     * @param p_CoursePos The index position of course in file
     * @param p_IndexPos The index position of indexes in file
     * @param p_allIndList The list of all indexes
     */
    private void drop(int p_CoursePos, int p_IndexPos, List<ArrayList<Indexes>> p_allIndList){ //this function drops the user's chosen course. Returns error message if chosen course is not in user's list of courses
        /*p_CoursePos is the index of the course in the courseList in the file
        p_IndexPos is the index of the index/class in the ArrayList of the indexes in the file*/

        Indexes chosenIndex;
        boolean match;
        chosenIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos); //returns an index object to be dropped from the student's list of indexes
        int count = 0;
        match = false;
        for(Indexes ind : myIndexes){
            if(ind.getCourseIndex() == chosenIndex.getCourseIndex()){
                match=true;
                break;
            }
            count+=1;
        }
        if(match){
            myIndexes.remove(count);
            myCourses.remove(count);
            dropUpdate(p_CoursePos, p_IndexPos, p_allIndList);
            System.out.println("\n=============================================");
            System.out.println("| You have successfully dropped the course. |");
            System.out.println("=============================================");
        }else{
            System.out.println("\n=========================================================================");
            System.out.println("| You don't have the selected course and index in your registered list. |");
            System.out.println("=========================================================================");
        }
    }

    /**
     * Checks for clash in a student's timetable when trying to change an index of a registered course
     * @param p_couChoice The index position of course to be changed from student's registered courses
     * @param p_toChangeIndex The index position of chosen index to change
     * @param p_chosenCourse The Courses object with index number to be swapped
     * @param p_chosenIndex The Indexes object
     * @param p_CouPos The index position of chosen course in file
     * @param p_allIndList The list of all indexes objects
     */
    private void change(int p_couChoice, Indexes p_toChangeIndex, Courses p_chosenCourse, Indexes p_chosenIndex,
                        int p_CouPos, List<ArrayList<Indexes>> p_allIndList){ //this function checks if there will be any clashes with to-be-changed index with registered classes
        /*The reason why i need to attach this method to drop and add is because i need to shift any student in the waiting list into the next available slot and wait list respectively*/
        Scanner sc = new Scanner(System.in);
        CheckClash checkClash = new CheckClash();
        CompileData com = new CompileData();
        SendMailTLS mail = new SendMailTLS();
        com.getCourseData();
        List<Courses> couList = com.getAllCourses();

        String courseName = p_chosenCourse.getCourseName();
        int old_courseIndex = p_toChangeIndex.getCourseIndex();
        int new_courseIndex = p_chosenIndex.getCourseIndex();

        boolean success;
        int choice, oldIndxPos, newIndxPos;
        //Check if there are any clashes if there is a change
        myIndexes.remove(p_couChoice); //remove the index to be changed so that it's less troublesome to check clash with remaining indexes
        myCourses.remove(p_couChoice);
        success = checkClash.checkOwnClash(myIndexes, p_chosenIndex);
        if (success) {
            if(p_chosenIndex.getVacancy()>0) {
                myIndexes.add(p_chosenIndex);
                myCourses.add(p_chosenCourse);
                changeUpdate(p_couChoice, p_toChangeIndex, p_chosenIndex);
                System.out.println("=====================================================");
                System.out.println("| Your chosen course has been changed successfully! |");
                System.out.println("=====================================================");
                mail.changeEmail(myName, old_courseIndex, new_courseIndex, courseName);
            }
            else{
                System.out.println("\n========================================================================");
                System.out.println("| "+p_chosenIndex.getCourseIndex()+" is fully subscribed. Do you wish to be added to the wait list? |");
                System.out.println("========================================================================");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Choice:");
                choice = sc.nextInt();
                if(choice==1) {
                    List<Integer> indexNumList = new ArrayList<>();
                    List<Indexes> indxList = p_allIndList.get(p_CouPos);
                    for(Indexes ind : indxList){
                        indexNumList.add(ind.getCourseIndex());
                    }
                    oldIndxPos = indexNumList.indexOf(p_toChangeIndex.getCourseIndex());
                    newIndxPos = indexNumList.indexOf(p_chosenIndex.getCourseIndex());
                    myIndexes.add(p_toChangeIndex); //revert back to old registered list to avoid errors in drop() method
                    myCourses.add(p_chosenCourse);
                    drop(p_CouPos, oldIndxPos,p_allIndList); //by this step, student has dropped the index he wanted to change
                    add(p_CouPos, newIndxPos, couList, p_allIndList,1);
                }
            }
        } else {
            System.out.println("=========================================================================================================================");
            System.out.println("As there are clashes between registered classes with the index to be changed, changing to this new index is unsuccessful.");
            System.out.println("=========================================================================================================================");
            myIndexes.add(p_toChangeIndex);
            myCourses.add(p_chosenCourse); //add back the removed course and index
        }
    }

    /**
     * Swaps the indexes of 2 students
     * @param p_std The student to swap with
     * @param selcInd The selected index number to be swappws
     * @see #swapWith(Student)
     */
    private void swap(Student p_std, int selcInd){
        MainSystem ms = new MainSystem();
        SendMailTLS mail = new SendMailTLS();
        CompileData com = new CompileData();
        com.getStudentData();
        int count;

        //get the swapper's course/index position
        Courses chCou = p_std.getMyCourses().get(selcInd-1);
        String couName = chCou.getCourseName();
        count = 0;
        for(Courses c : myCourses){
            if((c.getCourseName().equals(couName))){
                break;
            }
            count+=1;
        }

        Indexes perInd = myIndexes.get(count); //get swapper's index
        Indexes peeInd = p_std.getMyIndexes().get(selcInd-1); //choosing the swapee's index
        //remove the swapper's index-to-swap
        myIndexes.remove(count);
        //check clash for swapper
        CheckClash cc = new CheckClash();
        boolean result1 = cc.checkOwnClash(myIndexes, peeInd);

        //remove swapee's index-to-swap
        List<Indexes> oldPeeList = p_std.getMyIndexes();
        oldPeeList.remove(selcInd-1);
        p_std.setMyIndx(oldPeeList); //updated swapee's list of indexes prior to checking

        //check clash for swapee
        boolean result2 = cc.checkOwnClash(p_std.getMyIndexes(), peeInd);

        //if false, add back the old index into swapper's myIndexes
        if(result1&result2){
            myIndexes.add(count, peeInd);
            oldPeeList.add(selcInd-1, perInd);
            p_std.setMyIndx(oldPeeList);
            List<Indexes> newPeeList = p_std.getMyIndexes();

            //update the files
            List<Student> stdList = com.getStudList();
            for(Student s : stdList){
                if(s.getMyName().equals(myName)){
                    s.setMyIndx(myIndexes);
                }
                else if(s.getMyName().equals(p_std.getMyName())){
                    s.setMyIndx(newPeeList);
                }
            }
            System.out.println("\n==========================================");
            System.out.println("| You have successfully swapped indexes. |");
            System.out.println("==========================================");
            ms.uptStud(stdList); //no need to update vacancy becase it will still remain the same

            /*notify student*/
            String swapeeName = p_std.myName;
            int oldInx = perInd.getCourseIndex();
            int newInx = peeInd.getCourseIndex();
            mail.swapEmail(myName, swapeeName, oldInx, newInx);
        }
        else{
            myIndexes.add(count, perInd); //convert back to old list of indexes
            List<Indexes> newPeeList = p_std.getMyIndexes();
            newPeeList.add(selcInd-1, peeInd);
            p_std.setMyIndx(newPeeList);
            //no need to update file because everything remains the same as the previous data records
        }
    }

    /**
     * Prints the timetable of all registered courses
     */
    public void printMyTT(){
        /*First, need to print courses registered for.
        Second, we print the index registered for each course
        Third, we print Lecture Day: <day> \t\t Time: <period>
        Fourth, we print Tut/Lab Day: <day> \t\t Time: <period>
        Lastly, we print the courses and indexes on wait list
         */
        int maxLessons1 = myCourses.size();
        if(maxLessons1>0) {
            System.out.println("\nYour current timetable looks like this: ");
            for (int i = 0; i < maxLessons1; i++) {
                Courses currCou = myCourses.get(i);
                String courseName = currCou.getCourseName();
                Indexes currIdx = myIndexes.get(i);
                String lectDay = currIdx.getLecDay();
                String TLDay = currIdx.getTutLabDay();
                int L_st = currIdx.getLessonTimes().get(0);
                int L_et = currIdx.getLessonTimes().get(1);
                int T_st = currIdx.getLessonTimes().get(2);
                int T_et = currIdx.getLessonTimes().get(3);

                System.out.println("\n" + (i + 1) + ". " + courseName + ": ");
                System.out.println("Lecture day: " + lectDay + "\t\tTime:" + L_st + "-" + L_et);
                System.out.println("Tutorial/Lab day: " + TLDay + "\t\tTime:" + T_st + "-" + T_et);
            }
        }
        else{
            System.out.println("\n===============================================");
            System.out.println("| You have no courses and indexes registered. |");
            System.out.println("===============================================");
        }

        int maxLessons2 = coursesWL.size();
        if(maxLessons2>0) {
            System.out.println("\nThese are your courses and indexes on wait list:");
            for (int j = 0; j < maxLessons2; j++) {
                Courses currCou = coursesWL.get(j);
                String courseName = currCou.getCourseName();
                Indexes currIdx = indexWL.get(j);
                int indexNum = currIdx.getCourseIndex();
                System.out.println((j + 1) + ". " + courseName + ": " + indexNum);
            }
        }
        else{
            System.out.println("\n============================================================");
            System.out.println("| You are not on the wait list for any courses or indexes. |");
            System.out.println("============================================================");
        }
    }

    /**
     * Updates the files after registering for a course
     * @param p_CoursePos The index position of course in file
     * @param p_IndexPos The index position of indexes in file
     * @param p_allIndList The list of all indexes
     */
    /*To update files*/
    private void addUpdate( int p_CoursePos, int p_IndexPos, List<ArrayList<Indexes>> p_allIndList){
        MainSystem MS = new MainSystem();
        CompileData com = new CompileData();
        SendMailTLS mail = new SendMailTLS();
        com.getStudentData();
        com.getCourseData();
        List<Student> stdList = com.getStudList();
        List<Courses> courses = com.getAllCourses();

        //to update vacancy
        int orgVac = p_allIndList.get(p_CoursePos).get(p_IndexPos).getVacancy(); //get the original vacancy of index
        p_allIndList.get(p_CoursePos).get(p_IndexPos).setVacancy(orgVac-1); //reduce 1 from vacancy once the student has added the course
        MS.uptIndexFile(p_allIndList);

        //to update student
        for(Student std : stdList){
            if(std.getMyName().equals(myName)){
                std.myCourses = myCourses;
                std.myIndexes = myIndexes;
                break;
            }
        }
        MS.uptStud(stdList);

        /*Send email to notify student*/
        String courseName = courses.get(p_CoursePos).getCourseName();
        int courseIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos).getCourseIndex();
        mail.sendAddEmail(myName, courseName, courseIndex);
    }

    /**
     * Updates the files after dropping a registered course
     * @param p_CoursePos The index position of course in file
     * @param p_IndexPos The index position of indexes in file
     * @param p_allIndList The list of all indexes
     */
    private void dropUpdate(int p_CoursePos, int p_IndexPos, List<ArrayList<Indexes>> p_allIndList){
        MainSystem MS = new MainSystem();
        CompileData com = new CompileData();
        SendMailTLS mail = new SendMailTLS();
        com.getStudentData();
        com.getCourseData();
        List<Courses> courseList = com.getAllCourses();
        List<Student> stdList = com.getStudList();
        Indexes chosenIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos);
        Courses chosenCourse = courseList.get(p_CoursePos);
        int count=0;

        //to update vacancy
        //have to check if wait list is empty. no point updating vacancy when wait list !empty
        if(chosenIndex.getWaitL().isEmpty()) {
            int orgVac = p_allIndList.get(p_CoursePos).get(p_IndexPos).getVacancy(); //get the original vacancy of index
            p_allIndList.get(p_CoursePos).get(p_IndexPos).setVacancy(orgVac + 1); //adds 1 to vacancy once the student has dropped the course
            MS.uptIndexFile(p_allIndList);
        }
        else{
            Student std_WL = chosenIndex.getWaitL().get(0); //takes the first student in the wait list
            List<Courses> newC = std_WL.getMyCourses();
            List<Indexes> newI = std_WL.getMyIndexes();
            newC.add(chosenCourse);
            newI.add(chosenIndex);
            std_WL.setMyCou(newC);
            std_WL.setMyIndx(newI);
            /*remove course and index info from personal wait list*/
            for(Courses c : std_WL.coursesWL){
                if(c.getCourseName().equals(chosenCourse.getCourseName())){
                    std_WL.coursesWL.remove(c);
                    break;
                }
            }
            for(Indexes i : std_WL.indexWL){
                if(i.getCourseIndex()==chosenIndex.getCourseIndex()){
                    std_WL.indexWL.remove(i);
                    break;
                }
            }
            chosenIndex.getWaitL().remove(0);//remove student from waitlist

            /*Send email to notify student*/
            String courseName = courseList.get(p_CoursePos).getCourseName();
            int courseIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos).getCourseIndex();
            mail.sendAddEmail(std_WL.myName, courseName, courseIndex); //This is the student in the wait list. during demonstration, all email will be sent to the same email add but will still show that it works

            //update the list of courses to be written in file
            p_allIndList.get(p_CoursePos).remove(p_IndexPos);
            p_allIndList.get(p_CoursePos).add(p_IndexPos, chosenIndex);
            MS.uptIndexFile(p_allIndList);

            //update student list
            for(Student s : stdList){
                if(s.getMyName().equals(std_WL.getMyName())){
                    stdList.remove(count);
                    stdList.add(count, std_WL); //adds the updated student into the original file
                    break;
                }
                count+=1;
            }
        }

        //to update student
        for(Student std : stdList){
            if(std.getMyName().equals(myName)){
                std.myCourses = myCourses;
                std.myIndexes = myIndexes;
                break;
            }
        }
        MS.uptStud(stdList);

        /*notify the student that he has successfully dropped the course*/
        String courseName = courseList.get(p_CoursePos).getCourseName();
        int courseIndex = p_allIndList.get(p_CoursePos).get(p_IndexPos).getCourseIndex();
        mail.sendDropEmail(myName, courseName, courseIndex);
    }

    /**
     * Updates the files after swapping index of a registered course
     * @param couPos The index position of course in file
     * @param p_toChIndx The old indexes object which has been swapped
     * @param p_choIndx The new indexes object which has been selected
     */
    private void changeUpdate(int couPos, Indexes p_toChIndx, Indexes p_choIndx){
        CompileData com = new CompileData();
        MainSystem MS = new MainSystem();
        com.getIndexData();
        com.getStudentData();
        List<Student> stdList = com.getStudList();
        List<ArrayList<Indexes>> indArr = com.getAllCourseIndexes();
        List<Indexes> idxList = indArr.get(couPos);
        int pos1 = -1; //put a ridiculous number so that if there are any problems, an error will be raised.
        int pos2 = -1; //also, it's because we know pos1 and pos2 will surely change since the indexes can be found
        int idxPos = 0;
        for(Indexes ind : idxList){
            if(ind.getCourseIndex()==p_toChIndx.getCourseIndex()){
                pos1 = idxPos;
            }
            else if(ind.getCourseIndex()==p_choIndx.getCourseIndex()){
                pos2 = idxPos;
            }
            idxPos+=1;
        }

        //update vacancy
        int orgVac;
        orgVac = indArr.get(couPos).get(pos1).getVacancy(); //get the original vacancy of index
        indArr.get(couPos).get(pos1).setVacancy(orgVac+1); //adds 1 to vacancy once the student has dropped the course
        orgVac = indArr.get(couPos).get(pos2).getVacancy();
        indArr.get(couPos).get(pos2).setVacancy(orgVac-1);
        MS.uptIndexFile(indArr);

        //to update student
        for(Student std : stdList){
            if(std.getMyName().equals(myName)){
                std.myCourses = myCourses;
                std.myIndexes = myIndexes;
                break;
            }
        }
        MS.uptStud(stdList);
    }

}
