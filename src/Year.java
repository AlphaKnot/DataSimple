/***
 * this class represents an object of class year with the end and start years
 * @author Jenessa Lu
 */
public class Year {
    int DDstartYear;
    int DDendYear;

    /***
     * the constructor for the year object
     */
    public Year(int startYear, int endYear){
        DDstartYear = startYear;
        DDendYear = endYear;
    }

    /***
     * getter method to return the starting year
     * @return the starting year
     */
    public int getStart(){
        return DDstartYear;
    }

    /***
     * getter method to return the end year
     * @return the ending year
     */
    public int getEnd(){
        return DDendYear;
    }

    /***
     * setter method to set a new start year a user selected
     * @param newStart the new starting year
     */
    public void setStart(int newStart){
        DDstartYear= newStart;
    }

    /***
     * setter method to set a new end year a user selected
     * @param newEnd the new ending year
     */
    public void setEnd(int newEnd){
        DDendYear=newEnd;
    }


}
