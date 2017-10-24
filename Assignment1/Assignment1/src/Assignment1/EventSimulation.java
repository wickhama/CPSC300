package Assignment1;

import FileReader.Reader;
import Nodes.Q;
import Nodes.EventQ;
import Nodes.PatientQ;
import Nodes.WaitQ;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author SamTheTurdBurgler
 */
public class EventSimulation {
    
    private final Scanner sc = new Scanner(System.in);
    private final Random rand = new Random(1000);
    private Reader reader;
    
    private int id, rooms;
    private PatientQ patientQ;
    private EventQ eventQ;
    private Q assesQ;
    private WaitQ waitQ;
    private Event event;
    private Q admitQ;
    
    
    public EventSimulation() {
        System.out.println("Please input file name: ");
        try {
            reader = new Reader(sc.nextLine());
        } catch(FileNotFoundException e) {
            System.out.println("Can't open file Error: "+e);
            return;
        }
        id=28064212;
        rooms = 0;
        patientQ = new PatientQ();
        eventQ = new EventQ();
        assesQ = new Q();
        waitQ = new WaitQ();
        admitQ = new Q();
        
        if(!createPatient()) {
            System.out.println("Error with input of file");
            return;
        }
        
        System.out.println("Simulation begins...\n");
        
        while(eventQ.getNext() != null) {
            //eventQ.print();
            //System.out.println();
            event = eventQ.dequeue();
            //System.out.println(event.getType());
            switch(event.getType()) {
                case 1: arrive(event);
                break;
                case 2: asseses(event);
                break;
                case 3: assComplete(event);
                break;
                case 4: treatment(event);
                break;
                case 5: treatmentComplete(event);
                break;
                case 6: admitted(event);
                break;
                case 7: depart(event);
                break;
            }
        }
        
        System.out.println("\n...All events Finished. Final Summary:\n");
        System.out.printf("%-10s %s %10s %10s %10s %10s %10s\n","Patient", "Priority", "Arrival", "Assesment", "Treatment", "Departure", "Waiting");
        System.out.printf("%-10s %20s %10s %10s %10s %10s\n", "Number", "Time", "Time", "Required", "Time", "Time");
        patientQ.print();
    }    
    
    /*Adds Patient to appropriate Q based on Priority*/
    private void arrive(Event event) {
        Patient patient = event.getPatient();
        patient.reset(event.getTime());
        if(patient.getWalkin()) {
            System.out.printf("Time %d: %d (walk-in) arrives\n", event.getTime(), patient.getID());
            if(assesQ.getNext() == null) {
                event.setEvent(2, event.getTime());
                eventQ.addEvent(event);
            }
            assesQ.addPat(patient);
        }
        else {
            System.out.printf("Time %d: %d (emergency) arrives\n", event.getTime(), patient.getID());
            patient.setAssesment(event.getTime());
            if(rooms < 3) { 
                rooms++;
                event.setEvent(4, event.getTime());
                eventQ.addEvent(event);
            }
            else waitQ.addPat(patient);
            System.out.printf("Time %d: %d (Priority %d) Enters the waiting room\n",event.getTime(), patient.getID(), patient.getPriority());
        }
        
        if(!createPatient()) {
            System.out.println("Error with input of file");
            eventQ.setNext(null);
        }
        
    }
    
    //Assesment is done, and signals the AssessQ for another patient
    private void asseses(Event event) {
        Patient patient = event.getPatient();
        patient.setAssesment(event.getTime());
        System.out.printf("Time %d: %d starts assesment (waited %d)\n", event.getTime(),patient.getID(), patient.getWait(event.getTime()));
        event.setEvent(3, event.getTime()+4);
        eventQ.addEvent(event);
    }
    
    private void assComplete(Event event) {
        Patient patient = event.getPatient();
        patient.setPriority(rand.nextInt(5)+1);
        patient.reset(event.getTime());
        System.out.printf("Time %d: %d assesment complete (Priority now %d)\n", event.getTime(), patient.getID(), patient.getPriority());
        System.out.printf("Time %d: %d (Priority %d) Enters the waiting room.\n", event.getTime(), patient.getID(), patient.getPriority());

        assesQ.dequeue();
        if(rooms < 3) {
            rooms++;
            event.setEvent(4, event.getTime());
            eventQ.addEvent(event);
        }
        else waitQ.addPat(patient);
        if(assesQ.getNext() != null) {
            eventQ.addEvent(new Event(((Q)assesQ.getNext()).getPatient(), 2, event.getTime()));
        }
    }
    
    //Treatment has started. Adds an event for finished treatment
    private void treatment(Event event) {
        //int x;
        //if(waitQ.getRooms() <= 3) x = 3-waitQ.getRooms();
        //else x = 0;
        Patient patient = event.getPatient();
        System.out.printf("Time %d: %d (Priority %d) starts treatment (waited %d, %d rm(s) remain)\n", event.getTime(), patient.getID(), patient.getPriority(), patient.getWait(event.getTime()), (3-rooms));
        event.setEvent(5, event.getTime()+patient.getTreatReq());
        eventQ.addEvent(event);
    }
    
    private void treatmentComplete(Event event) {
        Patient patient = event.getPatient();
        System.out.printf("Time %d: %d Treatment Finished.\n", event.getTime(), patient.getID());
        if(1 == patient.getPriority()) {
            patient.reset(event.getTime());
            if(admitQ.getNext() == null) {
                patient.reset(event.getTime());
                event.setEvent(6, event.getTime()+3);
                eventQ.addEvent(event);
            }
            admitQ.addPat(patient);
        }
        else {
            event.setEvent(7, event.getTime()+1);
            eventQ.addEvent(event);
        }
    }
    
    //Addmittance is finished. Signals admitQ for another patient
    private void admitted(Event event) {
        Patient patient = event.getPatient();
        System.out.printf("Time %d: %d (Priority %d, waited %d) Admitted to hospital\n", event.getTime(), patient.getID(), patient.getPriority(), patient.getWait(event.getTime()-3));
        event.setEvent(7, event.getTime());
        eventQ.addEvent(event);
        admitQ.dequeue();
        if(admitQ.getNext() != null) {
            eventQ.addEvent(new Event(((Q)admitQ.getNext()).getPatient(), 6, event.getTime()+3));
        }
    }
    
    //Patient has left the building. Signals waitQ for a patient to enter treatment
    private void depart(Event event) {
        Patient patient = event.getPatient();
        patient.setDepart(event.getTime());
        rooms--;
        System.out.printf("Time %d: %d (Priority %d) Departs, %d rm(s) remain\n", event.getTime(), patient.getID(), patient.getPriority(), 3-rooms);
        if(waitQ.getNext() != null) {
            eventQ.addEvent(new Event(waitQ.dequeue(), 4, event.getTime()));
            rooms++;
        }
    }
    
    //Creates Patient and adds them to the PatientQ and creates an arrival event and adds to EventQ
    private boolean createPatient() {
        if(!reader.hasNext()) {
            return true;
        }
        int aTime, treat;
        String arr;
        try {
            aTime = Integer.parseInt(reader.readNext());
            arr = reader.readNext();
            if(!arr.equals("E") && !arr.equals("W")) {
                throw new NumberFormatException();
            }
            treat = Integer.parseInt(reader.readNext());
        } catch(NumberFormatException e) {
            return false;
        }
        
        int priority;
        boolean walkin;
        if(arr.equals("E")) {
            priority = 1;
            walkin = false;
        } else {
            priority = 0;
            walkin = true;
        }
        Patient patient = new Patient(id++, priority, walkin);
        patient.setArrival(aTime);
        patient.setTreatReq(treat);
        
        patientQ.insertPat(patient);
        event = new Event(patient, 1, patient.getArrival());
        eventQ.addEvent(event);
        return true;
    }
}
