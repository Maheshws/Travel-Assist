package ws.mahesh.travelassist.beta.bus.current.models;

/**
 * Created by mahesh on 03/03/15.
 */
public class CurrentStopObject {
    int Sequence;
    int StopID;
    String StopName;
    double lat;
    double lng;
    boolean visited;
    String ReachedAt;
    String Distance;
    String ETA;

    public CurrentStopObject(int sequence, int stopID, String stopName, double lat, double lng, boolean visited, String reachedAt, String distance, String ETA) {
        Sequence = sequence;
        StopID = stopID;
        StopName = stopName;
        this.lat = lat;
        this.lng = lng;
        this.visited = visited;
        ReachedAt = reachedAt;
        Distance = distance;
        this.ETA = ETA;
    }

    public int getSequence() {
        return Sequence;
    }

    public int getStopID() {
        return StopID;
    }

    public String getStopName() {
        return StopName;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getReachedAt() {
        return ReachedAt;
    }

    public void setReachedAt(String reachedAt) {
        ReachedAt = reachedAt;
    }

    public String getETA() {
        return ETA;
    }

    public void setETA(String ETA) {
        this.ETA = ETA;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }
}
