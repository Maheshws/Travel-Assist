package ws.mahesh.travelassist.beta.bus.current.models;

/**
 * Created by mahesh on 05/04/15.
 */
public class StopTimeObject {
    int stopId;
    long timestamp;

    public StopTimeObject(int stopId, long timestamp) {
        this.stopId = stopId;
        this.timestamp = timestamp;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StopTimeObject{" +
                "stopId=" + stopId +
                ", timestamp=" + timestamp +
                '}';
    }
}
