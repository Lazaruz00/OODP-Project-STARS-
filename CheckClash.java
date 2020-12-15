//This class ensures there are no clashes between classes when students add/swap indexes

import java.util.List;

/**
 * This class will instantiate an object to check for clashes in a student's timetable whenever
 * there is an update/addition of indexes or courses of a student
 */
public class CheckClash {
    /**
     * Taking into account of the lesson days and timings,
     * checks if there will be any clashes in the timetable of a student
     * when the student is attempting to add/swap/change the indexes of registered courses
     * @param studIndexes The list of registered indexes of a student
     * @param indexOfInterest The index number the student is trying to add
     * @see #gotClash(int, int, int, int) 
     * @return A boolean value depending on whether there is a clash in the timetable
     */
    public boolean checkOwnClash(List<Indexes> studIndexes, Indexes indexOfInterest){
        boolean success;
        int Lr_s, Lr_e, Tr_s, Tr_e, Li_s, Li_e, Ti_s, Ti_e; //for starting and ending time of lectures and tutorials
        for(Indexes regIndex : studIndexes){
            if(regIndex.getLecDay().equals(indexOfInterest.getLecDay())){ //if there are lessons on the same day, then have to check if got overlap
                Lr_s = regIndex.getLessonTimes().get(0);
                Lr_e = regIndex.getLessonTimes().get(1);// this chunk is checking for lectures
                Li_s = indexOfInterest.getLessonTimes().get(0);
                Li_e = indexOfInterest.getLessonTimes().get(1);
                success = gotClash(Lr_s, Lr_e, Li_s, Li_e);
                if (!success){
                    System.out.println("\n=============================================================");
                    System.out.println("| There is a clash between "+regIndex.getCourseIndex()+" lecture and "+indexOfInterest.getCourseIndex()+" lecture! |");
                    System.out.println("=============================================================\n");
                    return false;}
            }
            else if(regIndex.getTutLabDay().equals(indexOfInterest.getTutLabDay())){ //this chunk is checking for tutorials/labs
                Tr_s = regIndex.getLessonTimes().get(2);
                Tr_e = regIndex.getLessonTimes().get(3);
                Ti_s = indexOfInterest.getLessonTimes().get(2);
                Ti_e = indexOfInterest.getLessonTimes().get(3);
                success = gotClash(Tr_s, Tr_e, Ti_s, Ti_e);
                if (!success){
                    System.out.println("\n=======================================================================");
                    System.out.println("| There is a clash between "+regIndex.getCourseIndex()+" tutorial/lab and "+indexOfInterest.getCourseIndex()+" tutorial/lab! |");
                    System.out.println("=======================================================================\n");
                    return false;}
            }
            else if(regIndex.getTutLabDay().equals(indexOfInterest.getLecDay())){ //this chunk is checking for tutorials/labs
                Tr_s = regIndex.getLessonTimes().get(2);
                Tr_e = regIndex.getLessonTimes().get(3);
                Li_s = indexOfInterest.getLessonTimes().get(0);
                Li_e = indexOfInterest.getLessonTimes().get(1);
                success = gotClash(Tr_s, Tr_e, Li_s, Li_e);
                if (!success){
                    System.out.println("\n==============================================================");
                    System.out.println("| There is a clash between "+regIndex.getCourseIndex()+" tutorial and "+indexOfInterest.getCourseIndex()+" lecture! |");
                    System.out.println("==============================================================\n");
                    return false;}
            }
            else if(regIndex.getLecDay().equals(indexOfInterest.getTutLabDay())){ //this chunk is checking for tutorials/labs
                Lr_s = regIndex.getLessonTimes().get(0);
                Lr_e = regIndex.getLessonTimes().get(1);
                Ti_s = indexOfInterest.getLessonTimes().get(2);
                Ti_e = indexOfInterest.getLessonTimes().get(3);
                success = gotClash(Lr_s, Lr_e, Ti_s, Ti_e);
                if (!success){
                    System.out.println("\n==============================================================");
                    System.out.println("| There is a clash between "+indexOfInterest.getCourseIndex()+" tutorial and "+regIndex.getCourseIndex()+" lecture! |");
                    System.out.println("==============================================================\n");
                    return false;}
            }
        }
        return true;
    }

    /**
     * Compares the lesson timings and reports if there is a clash in timetable
     * @param regStime Registered lessons' start time
     * @param regEtime Registered lessons' end time
     * @param intStime Lesson start time for added index
     * @param intEtime Lesson end time for added index
     * @return A boolean value depending on if there is a class in lesson timings
     */
    private boolean gotClash(int regStime, int regEtime, int intStime, int intEtime){
        if(regStime>=intEtime || regEtime<=intStime){
            return true;
        }
        return false;
    }
}
