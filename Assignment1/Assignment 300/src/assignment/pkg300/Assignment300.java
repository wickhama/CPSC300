package assignment.pkg300;

import assignment.pkg300.Events.Events;
import assignment.pkg300.Queues.Event;
import assignment.pkg300.Queues.EventQ;
import assignment.pkg300.Queues.PatientQ;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *CPSC 300 Assignment 1 - Event Simulation
 * Simulates an emergency room of patients entering via walk-in, emergency.
 * patients receive treatment and then are admitted to the hospital
 * or depart.
 * @author SamTheTurdBurgler
 */
public class Assignment300 {
    
    private final Scanner sc;
    private Event event; //Temporary variable to use in simulation
    
    public Assignment300() {
        sc = new Scanner(System.in);
        
        simulate();
    }
    
    /*Entry point of Simulation
        Asks for file name of data. - throws error if file is not found.
    */
    private void simulate() {
        System.out.print("Input file name: ");
        try {
            Reader reader = new Reader(sc.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("Error with file name : " + e);
            return;
        }
        
        System.out.println("Simulation begins...");
        
        //create first event
        int patient = PatientQ.create();
        EventQ.enQ(PatientQ.getArrival(patient), 1, patient);

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
                case 6:
                    Events.treatmentFinished(event);
                    break;
                case 7:
                    Events.admitted(event);
                    break;
                case 8:
                    Events.departure(event);
                    break;
            }
        }
        System.out.println("\n...All events complete. Final Summary: \n");
        System.out.printf("%-10s %s %10s %10s %10s %10s %10s\n", "Patient", "Priority", "Arrival", "Assesment", "Treatment", "Departure", "Waiting");
        System.out.printf("%10s %20s %10s %10s %10s %10s\n", "Number", "Time", "Time", "Required", "Time", "Time");
        PatientQ.print();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Assignment300();
    }
    
}
