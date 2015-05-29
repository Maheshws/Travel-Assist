package ws.mahesh.travelassist.beta.bus.finder.models;

/**
 * Created by mahesh on 11/03/15.
 */
public class BusObject {
    int busid;
    String busno;
    String route;

    public BusObject(int busid, String busno, String route) {
        this.busid = busid;
        this.busno = busno;
        this.route = route;
    }

    public int getBusid() {
        return busid;
    }

    public String getBusno() {
        return busno;
    }

    public String getRoute() {
        return route;
    }
}
