package assignment.pkg300.Queues;

/**
 *PriorityQ holds VIP's and orders them in priority for the waiting rooms
 * @author SamTheTurdBurgler
 */
public class PriorityQ {
    
    private static VIP head;
    
    public PriorityQ() {
        head = new VIP();
    }
    
    public static void enQ(Patient patient) {head.enQ(new VIP(patient.getID(), patient.getPriority()));}
    
    public static Patient deQ() {
        Patient patient = PatientQ.find(((VIP)head.remove()).getID());
        return patient;
    }
    
    public static boolean isEmpty() {return head.getNext() == null;}
    
    public static void print() {
        Q node = head;
        while(node.getNext() != null) {
            System.out.println(node);
            node = node.getNext();
        }
    }
}
