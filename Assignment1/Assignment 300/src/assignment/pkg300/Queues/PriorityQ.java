package assignment.pkg300.Queues;

/**
 *PriorityQ holds the head of the Q for all patients waiting
 * for a treatment room. PriorityQ acts as a mediator between VIP(priority patients)
 * and users to ensure VIP is not altered outside of the defined parameters.
 * 
 * Methods are static to allow for easier access without altering the Q outside
 * of the defined parameters.
 * 
 * @author SamTheTurdBurgler
 */
public class PriorityQ {
    
    private static VIP head = new VIP();
    
    public static void enQ(Patient patient) {head.enQ(new VIP(patient.getID(), patient.getPriority()));}
    
    public static int deQ() {
        return ((VIP)head.remove()).getID();
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
