package Nodes;

import Assignment1.Patient;

/**
 *
 * @author SamTheTurdBurgler
 */
public class WaitQ extends Node {
    
    private Patient patient;
    private static int rooms;
    
    public WaitQ() {
        next = null;
        before = null;
        rooms = 0;
    }
    
    public WaitQ(Patient patient) {
        next = null;
        before = null;
        this.patient = patient;
    }
    
    public Patient dequeue() {
        patient = ((WaitQ)next).getPatient();
        next = next.getNext();
        rooms--;
        return patient;
    }
    
    public void addPat(Patient patient) {
        WaitQ node = this;
        while(node.next != null && patient.getPriority() >= ((WaitQ)node.getNext()).getPatient().getPriority()) {
            node = (WaitQ)node.getNext();
        }
        node.insert(new WaitQ(patient));
        rooms++;
    }
    
    private Patient getPatient() {
        return patient;
    }
    
    public int getRooms() {
        return rooms;
    }
}
