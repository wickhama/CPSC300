package Nodes;

/**
 * TimeNodes holds the list of times that is to be printed out
 * on a table at the end of the simulation.
 * @author SamTheTurdBurgler
 */
public class TimeNodes extends Node{
    
    private int time;
    
    public TimeNodes() {
        this.time = 0;
        next = null;
        before = null;
    }
    
    //Creates the list of times for patient
    public void create() {
        TimeNodes node;
        
        for(int i=0; i<4; i++) {
            node = new TimeNodes();
            insert(node);
        }
    }
    
    public void setTime(int time) {this.time = time;}
    public int getTime() {return time;}
    
}
