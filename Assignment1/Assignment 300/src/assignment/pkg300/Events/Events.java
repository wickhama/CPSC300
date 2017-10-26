
package assignment.pkg300.Events;

import assignment.pkg300.Queues.Event;
import assignment.pkg300.Queues.EventQ;
import assignment.pkg300.Queues.Patient;
import assignment.pkg300.Queues.PatientQ;
import assignment.pkg300.Queues.PriorityQ;
import java.util.Random;

/**
 *Abstract Events to encapsulate all events
 * @author SamTheTurdBurgler
 */
public class Events {
    
    private static int time;
    private static int assesmentTime;
    private static int admitTime;
    private static String msg;
    private static int rooms;
    private static final Random RAND = new Random(1000);
    
    public Events() {
        rooms = 0;
        assesmentTime = 0;
        admitTime = 0;
    }
    
    public static void arrival(Event event) {
        time = event.getTime();
        assesmentTime = assesmentTime < time ? time : assesmentTime;
        Patient patient = event.getPatient();
        patient.setAssesment(assesmentTime);
        if(patient.isWalkin()) {
            msg = "(Walk-in) arrives";
            event.setEvent(2, assesmentTime);
            EventQ.enQ(event);
            assesmentTime += 4;
        }
        else {
            msg = "(Emergency) arrives";
                event.setEvent(4, time);
                EventQ.enQ(event);
        }
        print(time, patient.getID(), msg);
        patient = PatientQ.create();
        if(patient == null) return;
        EventQ.enQ(patient.getArrivalTime(), 1, patient);
    }
    
    public static void assesment(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        msg = String.format("Starts Assesment (Waited %d)", patient.getWait(time));
        print(time, patient.getID(), msg);
        event.setEvent(3, time+4);
        EventQ.enQ(event);
    }
    
    public static void assesmentComplete(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        patient.setPriority(RAND.nextInt(5)+1);
        event.setEvent(4, time);
        EventQ.enQ(event);
        msg = String.format("Assesment Completed (Priority now %d)", patient.getPriority());
        print(time, patient.getID(), msg);
    }
    
    public static void enters(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        PatientQ.resetWait(patient.getID(), time);
        msg = String.format("(Priority %d) Enters Waiting Room", patient.getPriority());
        if(3 > rooms) {
            rooms++;
            event.setEvent(5, time);
            EventQ.enQ(event);
        }
        else {
            PriorityQ.enQ(patient);
        }
        
        print(time, patient.getID(), msg);
    }
    
    public static void treatmentStarted(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        msg = String.format("(Priority %d) Starts Treatment (Waited %d, %d rm(s) remain)",patient.getPriority(), patient.getWait(time), 3-rooms);
        event.setEvent(6, time+patient.getTreatmentTime());
        EventQ.enQ(event);
        print(time, patient.getID(), msg);
    }
    
    public static void treatmentFinished(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        PatientQ.resetWait(patient.getID(), time);
        msg = String.format("(Priority %d) Finishes Treatment", patient.getPriority());
        if(patient.getPriority() == 1) {
            admitTime = admitTime < time ? time : admitTime;
            admitTime += 3;
            event.setEvent(7, admitTime);
            EventQ.enQ(event);
        }
        else {
            event.setEvent(8, time+1);
            EventQ.enQ(event);
        }
        print(time, patient.getID(), msg);
    }
    
    public static void admitted(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        msg = String.format("(Priority %d, Waited %d) Admitted to the hospital",patient.getPriority(), patient.getWait(time-3));
        print(time, patient.getID(), msg);
        event.setEvent(8, time);
        EventQ.enQ(event);
    }
    
    public static void departure(Event event) {
        time = event.getTime();
        Patient patient = event.getPatient();
        rooms--;
        msg = String.format("(Priority %d) Departs, %d rm(s) remain", patient.getPriority(), 3-rooms);
        print(time, patient.getID(), msg);
        if(rooms < 3 && !PriorityQ.isEmpty()) {
            rooms++;
            EventQ.enQ(time, 5, PriorityQ.deQ());
        }
    }
    
    public static void print(int time, int id, String msg) {
        System.out.printf("Time %3d: %-10d %s\n", time, id, msg);
    }
}
