package Nodes;
import Assignment1.Patient;

/**
 *
 * @author SamTheTurdBurgler
 */
public class PatientQ extends Node {
    
    private Patient patient;
    
    public PatientQ() {
        next = null;
        before = null;
    }
    
    public PatientQ(Patient patient) {
        this.patient = patient;
        next = null;
        before = null;
    }
    
    private void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public Patient getPat() {
        return patient;
    }
    
    public void insertPat(Patient patient) {
        if (this.patient == null) {
            this.patient = patient;
        }
        else {
            PatientQ patientQ = this;
            while(patientQ.getNext() != null) {
                patientQ = (PatientQ)patientQ.getNext();
            }
            patientQ.insert(new PatientQ(patient));
        };
    }
    
    public void print() {
        PatientQ patientQ = this;
        
        while(patientQ != null){
            System.out.println(patientQ.getPat().getID());
            patientQ.getPat().printTimes();
            patientQ = (PatientQ)patientQ.getNext();      
        }
        
    }
}
