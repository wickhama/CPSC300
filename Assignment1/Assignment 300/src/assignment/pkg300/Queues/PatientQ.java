package assignment.pkg300.Queues;

import assignment.pkg300.Reader;

/**
 *PatientQ holds a list of all patients that have attended the hospital in numerical order.
 * PatientQ acts as a mediator between users and the Patient class. 
 * This ensures that the patient will not be altered or accessed than defined here.
 * Ensures that future classes added will not alter data structure than how it is
 * defined here.
 * 
 * Methods are static as PatientQ only holds the head of the Q, and tracks ID  counter.
 * This allows for easier access to Q without altering it outside of the defined parameters.
 * 
 * @author SamTheTurdBurgler
 */
public class PatientQ {
    
    private static int id = 28064212;
    private static Patient head = new Patient();
    
    /*enQ(Patient) only called by createPatient()
     adds the patient to the end of the Q
    */
    private static void enQ(Patient patient) {
        Q node = head;
        while(node.getNext() != null) {
            node = node.getNext();
        }
        node.insert(patient);
    }
    
    /*find(ID)
    found : Patient
    not found : null
    */
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
    
    public static void setAssesment(int id, int time) {
        find(id).setAssesment(time);
    }
    
    //Sets the importance of the Patient(priority)
    public static void setVIP(int id, int vip) {
        find(id).setPriority(vip);
    }
    
    public static void setDeparture(int id, int time) {
        find(id).setDeparture(time);
    }
    
    public static int getArrival(int id) {
        return find(id).getArrivalTime();
    }
    
    public static int getVIP(int id) {
        return find(id).getPriority();
    }
    
    public static boolean isWalkin(int id) {
        return find(id).isWalkin();
    }
    
    public static int getWait(int id, int time) {
        return find(id).getWait(time);
    }
    
    public static int getTreatment(int id) {
        return find(id).getTreatmentTime();
    }
    
    public static void resetWait(int id, int time) {
        Patient node = find(id);
        node.reset(time);
    }
    
    public static void print() {
        Patient node = head;
        int i = 0;
        int wait = 0;
        while(node.getNext() != null) {
            node = (Patient)node.getNext();
            node.print();
            i++;
            wait += node.getTotalWait();
        }
        System.out.printf("Number of Patients: %d\n", i);
        System.out.printf("Average Wait: %.6f\n", (float)wait/i);
    }
    
    public static int create() {
        if(!Reader.hasNext()) return 0;
        int arrival, treatment;
        boolean walkin;
        try {
            arrival = Integer.parseInt(Reader.next());
            walkin = Reader.next().equals("W");
            treatment = Integer.parseInt(Reader.next());
        } catch(NumberFormatException e) {
            System.out.println("Error with data in file: "+e);
            return 0;
        }
        Patient patient = new Patient(id++, walkin);
        patient.setArrivalTime(arrival);
        patient.setTreatmentTime(treatment);
        enQ(patient);
        return patient.getID();
    }
}
