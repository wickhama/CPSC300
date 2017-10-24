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
        if(next == null) insert(new EventQ(event));
//        else if(event.getTime() < ((EventQ)next).getEvent().getTime()) {
//            insert(new EventQ(event));
//            /*EventQ eventQ = new EventQ(event);
//            eventQ.setNext(this);
//            eventQ.setBefore(before);
//            if(before != null) getBefore().setNext(eventQ);
//            setBefore(eventQ);*/
//        }
//        else {
//            EventQ eventQ = (EventQ)next;
//            while(eventQ.getNext() != null) {
//                if(event.getTime() < eventQ.getEvent().getTime()) {
//                    insert(new EventQ(event));
//                }
//                else {
//                    eventQ = (EventQ) eventQ.getNext();
//                }
//            }
//            eventQ.insert(new EventQ(event));
//        }
        else { //Figure out why 212 is not going before 215
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
//                do {
//                    if(event.getTime() <= ((EventQ)eventQ.getNext()).getEvent().getTime() && event.getPatient().getID() < ((EventQ)eventQ.getNext()).getEvent().getPatient().getID() ) {
//                        eventQ.insert(new EventQ(event));
//                        return;
//                    }
//                    else {
//                        eventQ = (EventQ)eventQ.getNext();
//                    }
//                } while(eventQ.getNext() != null);
//                while(eventQ.getNext() != null) {
//                    if(event.getTime() < eventQ.getEvent().getTime()) {
//                        eventQ.getBefore().insert(new EventQ(event));
//                        break;
//                    }
//                }
                if(eventQ.getNext() == null) {eventQ.insert(new EventQ(event));}
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
