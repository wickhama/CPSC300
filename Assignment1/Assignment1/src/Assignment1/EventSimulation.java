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
            System.out.println("Error with file");
            return;
        }
        figure out waitQ add and remove order!
        maybe restart???
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
            waitQ.addPat(patient);
            if(rooms < 3) { 
                rooms++;
                event.setEvent(4, event.getTime());
                eventQ.addEvent(event);
            }
            System.out.printf("Time %d: %d (Priority %d) Enters the waiting room\n",event.getTime(), patient.getID(), patient.getPriority());
        }
        
        createPatient();
        
    }
    
    //Assesment is done, and signals the AssessQ for another patient
    private void asseses(Event event) {
        Patient patient = event.getPatient();
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
        waitQ.addPat(patient);
        assesQ.dequeue();
        if(rooms < 3) {
            rooms++;
            System.out.println("Event Created"+patient.getID());
            event.setEvent(4, event.getTime());
            eventQ.addEvent(event);
        }
        if(assesQ.getNext() != null) {
            eventQ.addEvent(new Event(assesQ.dequeue(), 2, event.getTime()));
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
            if(admitQ.getNext() == null) {
                patient.reset(event.getTime());
                event.setEvent(6, event.getTime()+3);
                patient.reset(event.getTime());
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
        System.out.printf("Time %d: %d (Priority %d, waited %d) Admitted to hospital\n", event.getTime(), patient.getID(), patient.getPriority(), patient.getWait(event.getTime()));
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
        //int x;
        //if(3 >= waitQ.getRooms()) x = 3-waitQ.getRooms();
        //else x = 0;
        rooms--;
        waitQ.dequeue();
        System.out.printf("Time %d: %d (Priority %d) Departs, %d rm(s) remain\n", event.getTime(), patient.getID(), patient.getPriority(), 3-rooms);
        if(waitQ.getNext() != null) {waitQ.print();
            eventQ.addEvent(new Event(((WaitQ)waitQ.getNext()).getPatient(), 4, event.getTime()));
            rooms++;
        }
    }
    
    //Creates Patient and adds them to the PatientQ and creates an arrival event and adds to EventQ
    private boolean createPatient() {
        int aTime;
        try {
            aTime = Integer.parseInt(reader.readNext());
        } catch(NumberFormatException e) {
            return false;
        }
        
        int priority;
        boolean walkin;
        if(reader.readNext().equals("E")) {
            priority = 1;
            walkin = false;
        } else {
            priority = 0;
            walkin = true;
        }
        Patient patient = new Patient(id++, priority, walkin);
        patient.setArrival(aTime);
        patient.setTreatReq(Integer.parseInt(reader.readNext()));
        
        patientQ.insertPat(patient);
        event = new Event(patient, 1, patient.getArrival());
        eventQ.addEvent(event);
        return true;
    }
}
