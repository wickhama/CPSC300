package Nodes;

import Assignment1.Patient;

/**
 *
 * @author SamTheTurdBurgler
 */
public class Q extends Node{
    
    private Patient patient;
    
    public Q() {
        next = null;
        before = null;
        patient = null;
    }
    
    public Q(Patient patient) {
        next = null;
        before = null;
        this.patient = patient;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public Patient dequeue() {
        if(next != null) {
            patient = ((Q)next).getPatient();
            next = next.getNext();
        }
        else System.out.println("Error with Dequeue");
        return patient;
    }
    
    public void addPat(Patient patient) {
        Q node = this;
        while(node.getNext() != null) {node = (Q) node.getNext();}
        node.insert(new Q(patient));
    } 
    
    public void print() {
        Q node = this;
        while(node.getNext() != null) {
            node = (Q)node.getNext();
            System.out.println(node.getPatient().getID());
        }
    }
}
