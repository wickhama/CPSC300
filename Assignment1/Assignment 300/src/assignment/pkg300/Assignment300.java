package assignment.pkg300;

import assignment.pkg300.Events.Events;
import assignment.pkg300.Queues.Event;
import assignment.pkg300.Queues.EventQ;
import assignment.pkg300.Queues.Patient;
import assignment.pkg300.Queues.PatientQ;
import assignment.pkg300.Queues.PriorityQ;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *
 * @author SamTheTurdBurgler
 */
public class Assignment300 {
    
    private Scanner sc = new Scanner(System.in);
    private Reader read;
    private EventQ eventQ;
    private PatientQ patientQ;
    private PriorityQ vipQ;
    private Patient patient; //Temporary variable to be use in simulation
    private Event event; //Temporary variable to use in simulation
    
    public Assignment300() {
        eventQ = new EventQ();
        patientQ = new PatientQ();
        vipQ = new PriorityQ();
        
        simulate();
    }
    
    private void simulate() {
        System.out.print("Input file name: ");
        try {
            read = new Reader(sc.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("Error with file name : " + e);
            return;
        }
        //create first event
        patient = PatientQ.create();
        EventQ.enQ(patient.getArrivalTime(), 1, patient);
        EventQ.print();
        while(!EventQ.isEmpty()) {
            event = EventQ.deQ();
            switch(event.getType()) {
                case 1:
                    Events.arrival(event);
                    break; 
                case 2:
                    Events.assesment(event);
                    break;
                case 3:
                    Events.assesmentComplete(event);
                    break;
                case 4:
                    Events.enters(event);
                    break;
                case 5:
                    Events.treatmentStarted(event);
                    break;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Assignment300();
    }
    
}
