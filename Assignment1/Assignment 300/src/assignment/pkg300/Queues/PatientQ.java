package assignment.pkg300.Queues;

import assignment.pkg300.Reader;

/**
 *PatientQ holds a list of all patients that have attended the hospital in numerical order.
 * 
 * @author SamTheTurdBurgler
 */
public class PatientQ {
    
    private static int id;
    private static Patient head;
    
    public PatientQ() {
        id = 28064212;
        head = new Patient();
    }
    
    private static void enQ(Patient patient) {
        Q node = head;
        while(node.getNext() != null) {
            node = node.getNext();
        }
        node.insert(patient);
    }
    
    public static void resetWait(int id, int time) {
        Patient node = find(id);
        node.reset(time);
    }
    
    public static Patient find(int id) {
        Patient node = head;
        while(node.getNext() != null) {
            node = (Patient)node.getNext();
            if(node.getID() == id) {
                return node;
            }
        }
        return null;
    }
    
    public void print() {
        Patient node = head;
        while(node.getNext() != null) {
            node = (Patient)node.getNext();
            node.print();
        }
    }
    
    public static Patient create() {
        if(!Reader.hasNext()) return null;
        int arrival, treatment;
        boolean walkin;
        try {
            arrival = Integer.parseInt(Reader.next());
            walkin = Reader.next().equals("W");
            treatment = Integer.parseInt(Reader.next());
        } catch(NumberFormatException e) {
            System.out.println("Error with data in file: "+e);
            return null;
        }
        Patient patient = new Patient(id++, walkin);
        patient.setArrivalTime(arrival);
        patient.setTreatmentTime(treatment);
        enQ(patient);
        return patient;
    }
}
