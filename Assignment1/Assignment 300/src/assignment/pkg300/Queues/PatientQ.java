package assignment.pkg300.Queues;

import assignment.pkg300.Reader;

/**
 *PatientQ holds a list of all patients that have attended the hospital in decreasing numerical order.
 * 
 * @author SamTheTurdBurgler
 */
public class PatientQ {
    
    private static int id;
    private static Patient head;
    private Patient node;   //Temporary variable to be use in methods
    
    public PatientQ() {
        id = 28064212;
        head = new Patient();
    }
    
    public static void addPatient(Patient patient) {
        head.insert(patient);
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
        node = head;
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
        addPatient(patient);
        return patient;
    }
}
