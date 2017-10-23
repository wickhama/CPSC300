package Assignment1;

import Nodes.TimeNodes;

/**
 *
 * @author SamTheTurdBurgler
 */
public class Patient {
    
    private int id, priority, startWt;
    private boolean walkin;
    private TimeNodes time; //{Arrival, Assesment, Treatment Required, Departure, TotalWait};
    private TimeNodes tNode;
    
    public Patient(int id, int priority, boolean walkin) {
        this.id = id;
        this.priority = priority;
        this.walkin = walkin;
        startWt = 0;
        time = new TimeNodes();
        time.create();
    }
    
    public void reset(int tu) {startWt = tu;}
    public int getWait(int tu) {
        int wait = tu-startWt;
        totalWait(wait);
        reset(tu);
        return wait;
    }
    
    public int getID() {return id;}
    public int getPriority() {return priority;}
    public void setPriority(int priority) {this.priority = priority;}
    public boolean getWalkin() {return walkin;}
    
    public void setArrival(int arriveT) {time.setTime(arriveT); reset(arriveT);}
    public int getArrival() {return time.getTime();}
    
    public void setAssesment(int assT) {
        ((TimeNodes)time.getNext()).setTime(assT);
        getWait(assT);
    }
    public int getAssesment() {
        return ((TimeNodes)time.getNext()).getTime();
    }
    
    public void setTreatReq(int tretReq) {
        tNode = time;
        for(int i=0; i<2; i++) {
            tNode = (TimeNodes)tNode.getNext();
        }
        tNode.setTime(tretReq);
    }
    public int getTreatReq() {
        tNode = time;
        for(int i=0; i<2; i++) {
            tNode = (TimeNodes)tNode.getNext();
        }
        return tNode.getTime();
    }
    
    public void setDepart(int depart) {
        tNode = time;
        for(int i=0; i<3; i++) {
            tNode = (TimeNodes) tNode.getNext();
        }
        tNode.setTime(depart);
    }
    public int getDepart() {
        tNode = time;
        for(int i=0; i<3; i++) {
            tNode = (TimeNodes) tNode.getNext();
        }
        return tNode.getTime();
    }
    
    public void totalWait(int tu) {
        tNode = time;
        for(int i=0; i<4; i++) {
            tNode = (TimeNodes) tNode.getNext();
        }
        tNode.setTime(tNode.getTime() + tu);
    }
    
    public int getTotWait() {
        tNode = time;
        for(int i=0; i<4; i++) {
            tNode = (TimeNodes) tNode.getNext();
        }
        return tNode.getTime();
    }
    
    public void printTimes() {
        tNode = time;
        while(tNode != null) {
            System.out.println(tNode.getTime());
            tNode = (TimeNodes)tNode.getNext();
        }
    }
}
