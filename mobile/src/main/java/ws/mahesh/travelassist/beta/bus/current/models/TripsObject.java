package ws.mahesh.travelassist.beta.bus.current.models;

import java.util.ArrayList;

/**
 * Created by mahesh on 05/04/15.
 */
public class TripsObject {
    int BusId;
    int TripID;
    ArrayList<StopTimeObject> stoptime;
    String dir;

    public TripsObject(int busId, int tripID, ArrayList<StopTimeObject> stoptime, String dir) {
        BusId = busId;
        TripID = tripID;
        this.stoptime = stoptime;
        this.dir = dir;
    }

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public int getTripID() {
        return TripID;
    }

    public void setTripID(int tripID) {
        TripID = tripID;
    }

    public ArrayList<StopTimeObject> getStoptime() {
        return stoptime;
    }

    public void setStoptime(ArrayList<StopTimeObject> stoptime) {
        this.stoptime = stoptime;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "TripsObject{" +
                "BusId=" + BusId +
                ", TripID=" + TripID +
                ", stoptime=" + stoptime +
                ", dir='" + dir + '\'' +
                '}';
    }
}
