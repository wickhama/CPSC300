package assignment.pkg300.Queues;

/**
 *Event is a linked list(extends Q)
 * Mediated by EventQ
 * Prioritizes events by time, priority of patient, patient id
 * It only holds the patient ID
 * 
 * Event Types: 
 * 1 : Arrival
 * 2 : Assessment
 * 3 : Assessment Complete
 * 4 : Enters Waiting room
 * 5 : Starts Treatment
 * 6 : Treatment Finished
 * 7 : Admit to hospital
 * 8 : Departs hospital
 * 
 * @author SamTheTurdBurgler
 */
public class Event extends Q{
    
    private int type, time, id;
    
    //Only to be used as a head for EventQ
    protected Event() {
        //patient = null;
    }
    
    //Event(int time, int type, int id)
    public Event(int time, int type, int id) {
        this.time = time;
        this.type = type;
        this.id = id;
    }
    
    public int getID() { return id;}
    protected void setEvent(int type, int time) {
        setType(type);
        setTime(time);
    }
    
    public int getType() {return type;}
    private void setType(int type) {this.type = type;}
    
    public int getTime() {return time;}
    private void setTime(int time) {this.time = time;}
    
    /*enQ(Event) Orders events in Q 
      by time then priority then ID.
    */
    protected void enQ(Event event) {
        Q node = this;
        while(node.getNext() != null) {
            if(event.getTime() < ((Event)node.getNext()).getTime()) {
                node.insert(event);
                return;
            }
            else if(event.getTime() == ((Event)node.getNext()).getTime()) {
                if(PatientQ.getVIP(event.getID()) < PatientQ.getVIP(((Event)node.getNext()).getID())) {
                    node.insert(event);
                    return;
                }
                else if(PatientQ.getVIP(event.getID()) == PatientQ.getVIP(((Event)node.getNext()).getID())
                        && event.getID() < ((Event)node.getNext()).getID()) {
                    node.insert(event);
                    return;
                }
            }
            node = node.getNext();
        }
        node.insert(event);        
    }
    
    //debugging purposes
    private void print() {
        Q node = this;
        while(node.getNext() != null) {
            node = node.getNext();
            System.out.printf("Event %d, Time: %d, Patient: %d\n", type, time, id);
        }
    }
}
