package assignment.pkg300.Queues;

/**
 *Event is the event that will be executed. 
 * It holds a reference to the Patient 
 * @author SamTheTurdBurgler
 */
public class Event extends Q{
    
    private int type, time, id;
    //private final Patient patient;
    
    //Only to be used as a head for EventQ
    protected Event() {
        //patient = null;
    }
    
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
    
    protected void enQ(Event event) {
        Q node = this;
        while(node.getNext() != null) {
            if(event.getTime() < ((Event)node.getNext()).getTime()) {
                node.insert(event);
                return;
            }
            else if(event.getTime() == ((Event)node.getNext()).getTime()
                    && event.getID() < ((Event)node.getNext()).getID()) {
                node.insert(event);
                return;
            }
            else {
                node = node.getNext();
            }
        }
        node.insert(event);        
    }
    
    protected void print() {
        Q node = this;
        while(node.getNext() != null) {
            node = node.getNext();
            System.out.printf("Event %d, Time: %d, Patient: %d\n", type, time, id);
        }
    }
}
