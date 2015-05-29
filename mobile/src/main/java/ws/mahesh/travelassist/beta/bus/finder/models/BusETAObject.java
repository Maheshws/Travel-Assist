package ws.mahesh.travelassist.beta.bus.finder.models;

/**
 * Created by mahesh on 18/03/15.
 */
public class BusETAObject {
    String id;
    int busId;
    int srcId;
    int destId;
    String type;
    String eta;

    public BusETAObject(int busId, int srcId, int destId, String type) {
        this.busId = busId;
        this.srcId = srcId;
        this.destId = destId;
        this.type = type;
        id = busId + "-" + srcId + "-" + destId;
    }

    public BusETAObject(int busId, int srcId, int destId, String type, String eta) {
        this.busId = busId;
        this.srcId = srcId;
        this.destId = destId;
        this.type = type;
        this.eta = eta;
        id = busId + "-" + srcId + "-" + destId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public int getDestId() {
        return destId;
    }

    public void setDestId(int destId) {
        this.destId = destId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    @Override
    public String toString() {
        return "BusETAObject{" +
                "id='" + id + '\'' +
                ", busId=" + busId +
                ", srcId=" + srcId +
                ", destId=" + destId +
                ", type='" + type + '\'' +
                ", eta='" + eta + '\'' +
                '}';
    }

    public String getJson() {
        return "{\"bus_id\":\"" + busId + "\", \"src\": " + srcId + ", \"dest\": " + destId + ", \"eta\": " + eta + ", \"dir\": \"" + type + "\"}";
    }
}
