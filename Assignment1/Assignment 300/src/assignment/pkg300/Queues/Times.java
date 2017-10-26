package assignment.pkg300.Queues;

/**
 *List of times for patient that is to be printed out at the end of the simulation
 * Order: ArrivalTime, AssesmentTime, TimeRequiredForTreatment, DepartureTime, TotalTimeWaiting
 * Times acts as a mediator for TimeNode to ensure that TimeNode is not altered or accessed
 * outside of the defined parameters below.
 * 
 * only accessed by Patient
 * 
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
    
    protected int getArrival() {return ((TimeNode)getNode(1)).getTime();}
    protected void setArrival(int time) {getNode(1).setTime(time);}
    
    protected int getAssesment() {return getNode(2).getTime();}
    protected void setAssesment(int time) {getNode(2).setTime(time);}
    
    protected int getTreatmentTime() {return getNode(3).getTime();}
    protected void setTreatmentTime(int time) {getNode(3).setTime(time);}
    
    protected int getDeparture() {return getNode(4).getTime();}
    protected void setDeparture(int time) {getNode(4).setTime(time);}
    
    protected int getTotalWait() {return getNode(5).getTime();}
    protected void setTotalWait(int time) {
        node = (TimeNode)getNode(5);
        node.setTime(node.getTime()+time);
    }
    
    private TimeNode getNode(int position) {
        node = head;
        
        for(int i=0; i<position; i++) {
            node = (TimeNode)node.getNext();
        }
        return node;
    }
    
    protected void print() {
        node = head;
        while(node.getNext() != null) {
            node = (TimeNode)node.getNext();
            System.out.printf("%11d", node.getTime());
        }
    }
}
