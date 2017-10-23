package Assignment1;

/**
 *
 * @author SamTheTurdBurgler
 */
public class Event {
    private Patient patient;
    private int type;
    private int time;
    
    public Event(Patient patient, int type, int time) {
        this.patient = patient;
        this.type = type;
        this.time = time;
    }
    
    public void setEvent(int type, int time) {
        this.type = type;
        this.time = time;
    }
    
    public int getType() {
        return type;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public int getTime() {
        return time;
    }
    
    public void print() {
        System.out.printf("Event Type: %d, Time: %d, Patient: %d\n", type, time, patient.getID());
    }
}
