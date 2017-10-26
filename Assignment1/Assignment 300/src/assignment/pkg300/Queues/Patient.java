package assignment.pkg300.Queues;


/**
 *Patients of the simulation
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
    
    public void setPriority(int priority) {this.priority = priority;}
    public int getPriority() {return priority;}
    
    public boolean isWalkin() {return walkin;}
    public int getID() {return id;}
    protected void reset(int time) {start = time;}
    public int getWait(int time) {
        int wait = time - start;
        setTotalWait(wait);
        reset(time);
        return wait;
    }
    
    public void setArrivalTime(int time) {start = time; times.setArrival(time);}
    public void setAssesment(int time) {times.setAssesment(time);}
    public void setTreatmentTime(int time) {times.setTreatmentTime(time);}
    public void setDeparture(int time) {times.setDeparture(time);}
    public void setTotalWait(int time) {times.setTotalWait(time);}  //Adds the time passed to the time already in the list
    
    public int getArrivalTime() {return times.getArrival();}
    public int getAssesment() {return times.getAssesment();}
    public int getTreatmentTime() {return times.getTreatmentTime();}
    public int getDeparture() {return times.getDeparture();}
    public int getTotalWait() {return times.getTotalWait();}
    
    public void print() {
        System.out.printf("%-10d %5d ", id, priority);
        times.print();
        System.out.println();
    } 
    
}
