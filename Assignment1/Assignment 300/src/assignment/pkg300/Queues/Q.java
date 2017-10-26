package assignment.pkg300.Queues;

/**
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
    
    //Adds a node to the top of the list
    public void insert(Q node) {
        node.setNext(getNext());
        node.setBefore(this);
        if(next != null) next.setBefore(node);
        setNext(node);
    }
    
    //Removes the top of the list
    public Q remove() {
        Q node = getNext();
        setNext(getNext().getNext());
        return node;
    }
    
    public Q getNext() {return next;}
    public Q getBefore() {return before;}
    
    public void setNext(Q node) {next = node;}
    public void setBefore(Q node) {before = node;}
}
