package assignment.pkg300.Queues;

/**
 *List of times for patient that is to be printed out at the end of the simulation
 * There is in order ArrivalTime, AssesmentTime, TimeRequiredForTreatment, DepartureTime, TotalTimeWaiting
 * @author SamTheTurdBurgler
 */
public class Times {
    
    private TimeNode head;
    private TimeNode node;  //Temporary variable for use in methods.  
    
    public Times() {
        head = new TimeNode();
        create();
    }
    
    private void create() {
        for(int i=0; i<5; i++) {
            node = new TimeNode();
            head.insert(node);
        }
    }
    
    protected int getArrival() {
        node = (TimeNode)getNode(1);
        return node.getTime();
    }
    
    protected void setArrival(int time) {
        node = (TimeNode)getNode(1);
        node.setTime(time);
    }
    
    protected int getAssesment() {
        node = (TimeNode)getNode(2);
        return node.getTime();
    }
    
    protected void setAssesment(int time) {
        node = (TimeNode)getNode(2);
        node.setTime(time);
    }
    
    protected int getTreatmentTime() {
        node = (TimeNode)getNode(3);
        return node.getTime();
    }
    
    protected void setTreatmentTime(int time) {
        node = (TimeNode)getNode(3);
        node.setTime(time);
    }
    
    protected int getDeparture() {
        node = (TimeNode)getNode(4);
        return node.getTime();
    }
    
    protected void setDeparture(int time) {
        node = (TimeNode)getNode(4);
        node.setTime(time);
    }
    
    protected int getTotalWait() {
        node = (TimeNode)getNode(5);
        return node.getTime();
    }
    
    protected void setTotalWait(int time) {
        node = (TimeNode)getNode(5);
        node.setTime(node.getTime()+time);
    }
    
    private Q getNode(int position) {
        Q qNode = head;
        
        for(int i=0; i<position; i++) {
            qNode = qNode.getNext();
        }
        return qNode;
    }
    
    protected void print() {
        node = head;
        while(node.getNext() != null) {
            System.out.printf("%10d", node.getTime());
            node = (TimeNode)node.getNext();
        }
    }
}
