
package assignment.pkg300.Events;

import assignment.pkg300.Queues.Event;
import assignment.pkg300.Queues.EventQ;
import assignment.pkg300.Queues.PatientQ;
import assignment.pkg300.Queues.PriorityQ;
import java.util.Random;

/**
 *Events handles all of the events in the simulation.
 * Methods are static and to be called from the main class(Assignment300), switch statement.
 * 
 * @author SamTheTurdBurgler
 */
public class Events {
    
    //Used by every method. 
    private static int time;
    private static String msg;
    
    private static int assesmentTime;
    private static int admitTime;
    private static int rooms;
    private static final Random RAND = new Random(1000);
    
    public Events() {
        rooms = 0;
        assesmentTime = 0;
        admitTime = 0;
    }
    
    /*Arrival: Patient arrives
    walk-in : event is created for them to be assesed.
    Emergency : event is created for them to enter the waiting room
    then next line of data is read and processed
    */
    public static void arrival(Event event) {
        time = event.getTime();
        assesmentTime = assesmentTime < time ? time : assesmentTime;
        int patient = event.getID();
        if(PatientQ.isWalkin(patient)) {
            msg = "(Walk-in) arrives";
            PatientQ.setAssesment(patient, assesmentTime);
            EventQ.enQ(assesmentTime, 2, patient);
            assesmentTime += 4;
        }
        else {
            msg = "(Emergency) arrives";
            PatientQ.setAssesment(patient, time);
            EventQ.enQ(time, 4, patient);
        }
        print(time, patient, msg);
        patient = PatientQ.create();
        if(patient == 0) return;
        EventQ.enQ(PatientQ.getArrival(patient), 1, patient);
    }
    
    /*Assesment: Patient is assesed
    event is created for when assesment is done. 
    assesmentTime is increased to when patient will be done.
    */
    public static void assesment(Event event) {
        time = event.getTime();
        int patient = event.getID();
        msg = String.format("Starts Assesment (Waited %d)", PatientQ.getWait(patient, time));
        print(time, patient, msg);
        EventQ.enQ(time+4, 3, patient);
    }
    
    /*AssesmentComplete: Patient is finished with assesment
    patient gets a priority number
    event is created to enter waiting room
    */
    public static void assesmentComplete(Event event) {
        time = event.getTime();
        int patient = event.getID();
        PatientQ.setVIP(patient, RAND.nextInt(5)+1);
        EventQ.enQ(time, 4, patient);
        msg = String.format("Assesment Completed (Priority now %d)", PatientQ.getVIP(patient));
        print(time, patient, msg);
    }
    
    /*Enters: Patient enters waiting room
    room available: event is created to start treatment
    room unavailable: patient is added to priorityQ
    */
    public static void enters(Event event) {
        time = event.getTime();
        int patient = event.getID();
        PatientQ.resetWait(patient, time);
        msg = String.format("(Priority %d) Enters Waiting Room", PatientQ.getVIP(patient));
        if(3 > rooms) {
            rooms++;
            EventQ.enQ(time, 5, patient);
        }
        else {
            PriorityQ.enQ(PatientQ.find(patient));
        }
        
        print(time, patient, msg);
    }
    
    /*TreatmentStarted: Patient starts Treatment
    event is created for when patient is finished treatment
    */
    public static void treatmentStarted(Event event) {
        time = event.getTime();
        int patient = event.getID();
        msg = String.format("(Priority %d) Starts Treatment (Waited %d, %d rm(s) remain)", PatientQ.getVIP(patient), PatientQ.getWait(patient, time), 3-rooms);
        EventQ.enQ(time+PatientQ.getTreatment(patient), 6, patient);
        print(time, patient, msg);
    }
    
    /*TreatmentFinished(Event): patient is finished treatement
    priority 1: event is created for patient to be admitted
                addmitTime is increased to when patient will be finished admittance
    priority !1: event is created for patient to depart
    */ 
    public static void treatmentFinished(Event event) {
        time = event.getTime();
        int patient = event.getID();
        PatientQ.resetWait(patient, time);
        msg = String.format("(Priority %d) Finishes Treatment", PatientQ.getVIP(patient));
        if(PatientQ.getVIP(patient) == 1) {
            admitTime = admitTime < time ? time : admitTime;
            admitTime += 3;
            EventQ.enQ(admitTime, 7, patient);
        }
        else {
            EventQ.enQ(time+1, 8, patient);
        }
        print(time, patient, msg);
    }
    
    /*Admitted(Event): Patient is admitted to hospital
    event is created for patient to depart
    */
    public static void admitted(Event event) {
        time = event.getTime();
        int patient = event.getID();
        msg = String.format("(Priority %d, Waited %d) Admitted to the hospital", PatientQ.getVIP(patient), PatientQ.getWait(patient, time-3));
        print(time, patient, msg);
        EventQ.enQ(time, 8, patient);
    }
    
    /*Departure(Event): Patient departs
    people still in waiting room(PriorityQ): create an event for next patient to start treatment
    */
    public static void departure(Event event) {
        time = event.getTime();
        int patient = event.getID();
        PatientQ.setDeparture(patient, time);
        rooms--;
        msg = String.format("(Priority %d) Departs, %d rm(s) remain", PatientQ.getVIP(patient), 3-rooms);
        print(time, patient, msg);
        if(rooms < 3 && !PriorityQ.isEmpty()) {
            rooms++;
            EventQ.enQ(time, 5, PriorityQ.deQ());
        }
    }
    
    //Print(x, id, msg): "Time x: id msg"
    public static void print(int time, int id, String msg) {
        System.out.printf("Time %3d: %-10d %s\n", time, id, msg);
    }
}
