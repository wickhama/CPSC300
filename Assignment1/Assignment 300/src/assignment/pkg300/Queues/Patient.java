package assignment.pkg300.Queues;


/**
 *Patients of the simulation
 * Mediated by PatientQ.
 * Ordered in numerical order
 * 
 * @author SamTheTurdBurgler
 */
public class Patient extends Q{
    
    private int id, priority, start;
    private boolean walkin;
    private Times times;    //List of times to be printed out at end of simulation {ArrivalTime, AssesmentTime, TreatmentRequired, Departure, TotalWaitTime}
    
    //Only to be used as a head for PatientQ
    protected Patient() {}
    
    public Patient(int id, boolean walkin) {
        this.id = id;
        this.walkin = walkin;
        priority = walkin ? 6 : 1;
        times = new Times();
    }
    
    protected void setPriority(int priority) {this.priority = priority;}
    protected int getPriority() {return priority;}
    
    protected boolean isWalkin() {return walkin;}
    protected int getID() {return id;}
    protected void reset(int time) {start = time;}
    
    /*getWait(int)
    calculates how long the patient has been waiting since 
    last reset. Adds calculated time to TotalWait, and 
    then resets the wait counter.
    */
    protected int getWait(int time) {
        int wait = time - start;
        setTotalWait(wait);
        reset(time);
        return wait;
    }
    
    protected void setArrivalTime(int time) {start = time; times.setArrival(time);}
    protected void setAssesment(int time) {times.setAssesment(time);}
    protected void setTreatmentTime(int time) {times.setTreatmentTime(time);}
    protected void setDeparture(int time) {times.setDeparture(time);}
    protected void setTotalWait(int time) {times.setTotalWait(time);}  
    
    protected int getArrivalTime() {return times.getArrival();}
    protected int getAssesment() {return times.getAssesment();}
    protected int getTreatmentTime() {return times.getTreatmentTime();}
    protected int getDeparture() {return times.getDeparture();}
    protected int getTotalWait() {return times.getTotalWait();}
    
    protected void print() {
        System.out.printf("%-10d %5d ", id, priority);
        times.print();
        System.out.println();
    } 
    
}
