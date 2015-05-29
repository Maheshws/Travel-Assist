package ws.mahesh.travelassist.beta.bus.buswise.models;

/**
 * Created by mahesh on 16/03/15.
 */
public class BusWiseObject {
    int id;
    String shortName;
    String longName;

    public BusWiseObject(int id, String shortName, String longName) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    @Override
    public String toString() {
        return "BusWiseObject{" +
                "id=" + id +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                '}';
    }
}
