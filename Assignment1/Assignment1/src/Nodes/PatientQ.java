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
        if (next == null) {
            insert(new PatientQ(patient));
        }
        else {
            PatientQ patientQ = (PatientQ) next;
            while(patientQ.getNext() != null) {
                patientQ = (PatientQ)patientQ.getNext();
            }
            patientQ.insert(new PatientQ(patient));
        };
    }
    
    public void print() {
        PatientQ patientQ = (PatientQ)next;
        int numbOfPat = 0;
        int averWait = 0;
        while(patientQ != null){
            System.out.printf("%-10d %5d   ", patientQ.getPat().getID(), patientQ.getPat().getPriority());
            patientQ.getPat().printTimes();
            System.out.println();  
            numbOfPat++;
            averWait += patientQ.getPat().getTotWait();
            patientQ = (PatientQ)patientQ.getNext();
        }
        
        System.out.printf("\nNumber of Patient: %d\n", numbOfPat);
        System.out.printf("Average Wait Time: %.6f\n", (float)averWait/numbOfPat);
    }
}
