package assignment.pkg300.Queues;

/**
 *EventQ will handle the future events to be executed. 
 * @author SamTheTurdBurgler
 */
public class EventQ {
    
    private static Event head = new Event();
    
    public EventQ() {
        head = new Event();
    }
    
    public static void enQ(Event event) {head.enQ(event);}
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
