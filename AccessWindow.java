//This class stores info of access period

import java.io.Serializable;
import java.util.Date;

/**
 * AccessWindow class instantiates an object which is used to
 * store the start and end date of the access period
 */
public class AccessWindow implements Serializable {
    private Date startDate, endDate;

    //methods

    /**
     * Sets the start date of a student's access period
     * @param startDate The start date of access period
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    /**
     * Sets the end date of a student's access period
     * @param endDate The end date of access period
     */
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    /**
     * To get the start date of a student's access period
     * @return The start date of an access period
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date of a student's access period
     * @return The end date of an access period
     */
    public Date getEndDate() {
        return endDate;
    }
}
