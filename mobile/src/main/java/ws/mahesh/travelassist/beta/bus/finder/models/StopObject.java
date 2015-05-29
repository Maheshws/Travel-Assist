package ws.mahesh.travelassist.beta.bus.finder.models;

import android.location.Location;

/**
 * Created by mahesh on 10/03/15.
 */
public class StopObject {
    int stopid;
    String stopname;
    Location location;

    public StopObject(int stopid, String stopname, Location location) {
        this.stopid = stopid;
        this.stopname = stopname;
        this.location = location;
    }

    public int getStopid() {
        return stopid;
    }

    public String getStopname() {
        return stopname;
    }

    public Location getLocation() {
        return location;
    }
}
