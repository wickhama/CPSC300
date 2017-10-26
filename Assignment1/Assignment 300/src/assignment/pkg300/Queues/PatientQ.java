package assignment.pkg300.Queues;

import assignment.pkg300.Reader;

/**
 *PatientQ holds a list of all patients that have attended the hospital in numerical order.
 * 
 * @author SamTheTurdBurgler
 */
public class PatientQ {
    
    private static int id = 28064212;
    private static Patient head = new Patient();
    
    private static void enQ(Patient patient) {
        Q node = head;
        while(node.getNext() != null) {
            node = node.getNext();
        }
        node.insert(patient);
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
    
    public static void setAssesment(int id, int time) {
        find(id).setAssesment(time);
    }
    
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
        while(node.getNext() != null) {
            node = (Patient)node.getNext();
            node.print();
        }
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
