package assignment.pkg300.Queues;

/**
 *Abstract class: Patient, TimeNode, Event, VIP extend 
 * Basic Queue, linked list class. 
 * Q.insert(node) inserts after
 * Q.remove(node) removes the node after
 * 
 * @author SamTheTurdBurgler
 */
public abstract class Q {
    
    private Q next;
    private Q before;
    
    public Q() {
        next = null;
        before = null;
    }
    
    //Adds a node after this node
    protected void insert(Q node) {
        node.setNext(getNext());
        node.setBefore(this);
        if(next != null) next.setBefore(node);
        setNext(node);
    }
    
    //Removes the top of the list
    protected Q remove() {
        Q node = null;
        if(getNext() != null) {
            node = next;
            next = next.getNext();
        }
        return node;
    }
    
    protected Q getNext() {return next;}
    protected Q getBefore() {return before;}
    
    protected void setNext(Q node) {next = node;}
    protected void setBefore(Q node) {before = node;}
}
