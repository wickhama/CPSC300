package assignment.pkg300.Queues;

/**
 *EventQ handles the head of the eventQ(Event)
 * This class acts as a mediator between users and the Event class
 * It ensures that the main data structure(Event) won't be changed or access 
 * other than how it is described here. This also ensures that more classes added 
 * in the future will not alter Event than how it is described here.
 * 
 * Methods are static as EventQ only holds the head of the Q and no other data.
 * This allows for easier access to the Q without altering it outside of the 
 * defined parameters.
 * 
 * @author SamTheTurdBurgler
 */
public class EventQ {
    
    private static Event head = new Event();
    
    public static void enQ(int time, int type, int id) {head.enQ(new Event(time, type, id));}
    public static Event deQ() {return (Event)head.remove();}
    public static boolean isEmpty() {return (Event)head.getNext() == null;}
    public static void print() {
        Event node = head;
        while(node.getNext() != null) {
            System.out.println(node);
            node = (Event)node.getNext();
        }
    }
}
