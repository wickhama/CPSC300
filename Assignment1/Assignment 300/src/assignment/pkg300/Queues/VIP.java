package assignment.pkg300.Queues;

/**
 *
 * @author SamTheTurdBurgler
 */
public class VIP extends Q {
    
    private int id, priority;
    
    //To be only used as head
    protected VIP() {
    }
    
    public VIP(int id, int priority) {
        this.id = id;
        this.priority = priority;
    }
    
    protected int getID() {return id;}
    protected int getVIP() {return priority;}
    
    protected void enQ(VIP patient) {
        Q node = this;
        while(node.getNext() != null) {
            if(patient.getVIP() < ((VIP)node.getNext()).getVIP()) {
                node.insert(patient);
                return;
            }
            else if(patient.getVIP() == ((VIP)node.getNext()).getVIP()
                    && patient.getID() < ((VIP)node.getNext()).getID()) {
                node.insert(patient);
                return;
            }
            else {
                node = node.getNext();
            }
        }
        node.insert(patient);
    }
}
