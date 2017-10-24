package Nodes;

import Assignment1.Event;

/**
 *
 * @author SamTheTurdBurgler
 */
public class EventQ extends Node{
    
    private Event event;
    
    public EventQ() {
        next = null;
        before = null;
    }
    
    public EventQ(Event event) {
        this.event = event;
        next = null;
        before = null;
    }
    
    private void setEvent(Event event) {this.event = event;}
    public Event getEvent() {return event;}
    
    //Finds where the event should go then puts it into a node and adds it to the Q
    public void addEvent(Event event) {
        if(next == null) {
            insert(new EventQ(event));
        }
        else {
                EventQ eventQ = this;
                while(eventQ.getNext() != null) {
                    
                    if(event.getTime() < ((EventQ)eventQ.getNext()).getEvent().getTime() ) {
                        eventQ.insert(new EventQ(event));
                        return;
                    }
                    else if(event.getTime() == ((EventQ)eventQ.getNext()).getEvent().getTime() && event.getPatient().getID() < ((EventQ)eventQ.getNext()).getEvent().getPatient().getID()) {
                        eventQ.insert(new EventQ(event));
                        return;
                    }
                    else {
                        eventQ = (EventQ)eventQ.getNext();
                    }
                }
                eventQ.insert(new EventQ(event));
                //if(eventQ.getNext() == null) {eventQ.insert(new EventQ(event));}
        }
    }
    
    public Event dequeue() {
        if(next != null) {
            event = ((EventQ)next).getEvent();
            setNext(next.getNext());
        }
        return event;
    }
    
    public void print() {
        EventQ node = this;
        while(node.next != null) {
            node = (EventQ) node.getNext();
            node.getEvent().print();
        }
    }
}
