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
        while(node.getNext() != null) {
            if(patient.getPriority() < ((WaitQ)node.getNext()).getPatient().getPriority()) {
                node.insert(new WaitQ(patient));
                return;
            }
            else if(patient.getPriority() == ((WaitQ)node.getNext()).getPatient().getPriority() && patient.getID() < ((WaitQ)node.getNext()).getPatient().getID()) {
                node.insert(new WaitQ(patient));
                return;
            }
            else {
                node = (WaitQ)node.getNext();
            }
        }
        node.insert(new WaitQ(patient));
        rooms++;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public int getRooms() {
        return rooms;
    }
    
    public void print() {
        WaitQ node = this;
        while(node.getNext() != null) {
            node = (WaitQ)node.getNext();
            System.out.println(node.getPatient().getID());
        }
    }
}
