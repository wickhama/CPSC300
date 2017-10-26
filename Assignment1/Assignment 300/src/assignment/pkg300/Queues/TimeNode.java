/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.pkg300.Queues;

/**
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
