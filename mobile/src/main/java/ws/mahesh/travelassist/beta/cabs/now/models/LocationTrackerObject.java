package ws.mahesh.travelassist.beta.cabs.now.models;

import android.location.Location;

/**
 * Created by mahesh on 29/03/15.
 */
public class LocationTrackerObject {
    int id;
    Location location;
    long time;

    public LocationTrackerObject(int id, Location location, long time) {
        this.id = id;
        this.location = location;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public long getTime() {
        return time;
    }
}
