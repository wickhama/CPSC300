package Nodes;

/**
 *
 * @author SamTheTurdBurgler
 */
public abstract class Node {
    
    protected Node next;
    protected Node before;
    
    public void remove() {
        before.setNext(next);
    }
    
    public void insert(Node node) {
        node.setNext(next);
        node.setBefore(this);
        if(next != null) {
            next.setBefore(node);
        }
        setNext(node);
    }
    
    public Node getNext() {
        return next;
    }
    
    public Node getBefore() {
        return before;
    }
    
    public void setNext(Node next) {
        this.next = next;
    }
    
    public void setBefore(Node before) {
        this.before = before;
    }
    
    public void print() {
        Node node = this;
        while(node != null) {
            System.out.print(node+", ");
            node = node.getNext();
        }
    }
}
