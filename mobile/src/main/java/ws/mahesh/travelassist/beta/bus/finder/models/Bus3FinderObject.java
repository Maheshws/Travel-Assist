package ws.mahesh.travelassist.beta.bus.finder.models;

import android.location.Location;

/**
 * Created by mahesh on 13/03/15.
 */
public class Bus3FinderObject {
    int stop;
    int bus;
    Location loc;
    double distance;

    public Bus3FinderObject(int stop, int bus, Location loc, Location dest) {
        this.stop = stop;
        this.bus = bus;
        this.loc = loc;
        distance = loc.distanceTo(dest);
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = bus;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Bus3FinderObject{" +
                "stop=" + stop +
                ", bus=" + bus +
                ", loc=" + loc +
                ", distance=" + distance +
                '}';
    }
}
