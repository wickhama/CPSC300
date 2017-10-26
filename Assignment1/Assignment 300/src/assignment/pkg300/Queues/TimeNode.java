package assignment.pkg300.Queues;

/**
 *TimeNode extends Q
 * Is used by Patient to track times throughout the hospital.
 * Mediated by Times
 * 
 * @author SamTheTurdBurgler
 */
public class TimeNode extends Q{
 
    private int time;
    
    protected TimeNode() {
        time = 0;
    }
    
    protected void setTime(int time) {
        this.time = time;
    }
    
    protected int getTime() {
        return time;
    }
}
