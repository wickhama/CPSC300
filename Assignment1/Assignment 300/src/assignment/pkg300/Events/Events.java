
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
        int patient = event.getID();
        PatientQ.setAssesment(patient, assesmentTime);
        //Patient patient = PatientQ.find(event.getID());
        //patient.setAssesment(assesmentTime);
        if(PatientQ.isWalkin(patient)) {
            msg = "(Walk-in) arrives";
            //event.setEvent(2, assesmentTime);
            EventQ.enQ(assesmentTime, 2, patient);
            assesmentTime += 4;
        }
        else {
            msg = "(Emergency) arrives";
                //event.setEvent(4, time);
                EventQ.enQ(time, 4, patient);
        }
        print(time, patient, msg);
        patient = PatientQ.create();
        if(patient == 0) return;
        EventQ.enQ(PatientQ.getArrival(patient), 1, patient);
    }
    
    public static void assesment(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        msg = String.format("Starts Assesment (Waited %d)", PatientQ.getWait(patient, time));
        print(time, patient, msg);
        //event.setEvent(3, time+4);
        EventQ.enQ(time+4, 3, patient);
    }
    
    public static void assesmentComplete(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        PatientQ.setVIP(patient, RAND.nextInt(5)+1);
        //patient.setPriority(RAND.nextInt(5)+1);
        //event.setEvent(4, time);
        EventQ.enQ(time, 4, patient);
        msg = String.format("Assesment Completed (Priority now %d)", PatientQ.getVIP(patient));
        print(time, patient, msg);
    }
    
    public static void enters(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        PatientQ.resetWait(patient, time);
        msg = String.format("(Priority %d) Enters Waiting Room", PatientQ.getVIP(patient));
        if(3 > rooms) {
            rooms++;
            //event.setEvent(5, time);
            EventQ.enQ(time, 5, patient);
        }
        else {
            PriorityQ.enQ(PatientQ.find(patient));
        }
        
        print(time, patient, msg);
    }
    
    public static void treatmentStarted(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        msg = String.format("(Priority %d) Starts Treatment (Waited %d, %d rm(s) remain)", PatientQ.getVIP(patient), PatientQ.getWait(patient, time), 3-rooms);
        //event.setEvent(6, time+patient.getTreatmentTime());
        EventQ.enQ(time+PatientQ.getTreatment(patient), 6, patient);
        print(time, patient, msg);
    }
    
    public static void treatmentFinished(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        PatientQ.resetWait(patient, time);
        msg = String.format("(Priority %d) Finishes Treatment", PatientQ.getVIP(patient));
        if(PatientQ.getVIP(patient) == 1) {
            admitTime = admitTime < time ? time : admitTime;
            admitTime += 3;
            //event.setEvent(7, admitTime);
            EventQ.enQ(admitTime, 7, patient);
        }
        else {
            //event.setEvent(8, time+1);
            EventQ.enQ(time+1, 8, patient);
        }
        print(time, patient, msg);
    }
    
    public static void admitted(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        msg = String.format("(Priority %d, Waited %d) Admitted to the hospital", PatientQ.getVIP(patient), PatientQ.getWait(patient, time-3));
        print(time, patient, msg);
        //event.setEvent(8, time);
        EventQ.enQ(time, 8, patient);
    }
    
    public static void departure(Event event) {
        time = event.getTime();
        int patient = event.getID();
        //Patient patient = event.getPatient();
        rooms--;
        msg = String.format("(Priority %d) Departs, %d rm(s) remain", PatientQ.getVIP(patient), 3-rooms);
        print(time, patient, msg);
        if(rooms < 3 && !PriorityQ.isEmpty()) {
            rooms++;
            EventQ.enQ(time, 5, PriorityQ.deQ());
        }
    }
    
    public static void print(int time, int id, String msg) {
        System.out.printf("Time %3d: %-10d %s\n", time, id, msg);
    }
}
