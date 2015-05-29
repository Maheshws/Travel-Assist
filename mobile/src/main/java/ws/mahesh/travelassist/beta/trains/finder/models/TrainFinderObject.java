package ws.mahesh.travelassist.beta.trains.finder.models;

/**
 * Created by mahesh on 25/03/15.
 */
public class TrainFinderObject {
    int id;
    int prefId;
    int src;
    int dest;
    int lineId;
    String route;
    String eta;
    String linecode;
    String line;

    public TrainFinderObject(int id, int prefId, int src, int dest, int lineId, String route, String eta, String linecode, String line) {
        this.id = id;
        this.prefId = prefId;
        this.src = src;
        this.dest = dest;
        this.lineId = lineId;
        this.route = route;
        this.eta = eta;
        this.linecode = linecode;
        this.line = line;
    }

    public TrainFinderObject(int id, int prefId, int src, int dest, String route, String eta, String linecode, String line) {
        this.id = id;
        this.prefId = prefId;
        this.src = src;
        this.dest = dest;
        this.route = route;
        this.eta = eta;
        this.linecode = linecode;
        this.line = line;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrefId() {
        return prefId;
    }

    public void setPrefId(int prefId) {
        this.prefId = prefId;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getLinecode() {
        return linecode;
    }

    public void setLinecode(String linecode) {
        this.linecode = linecode;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "TrainFinderObject{" +
                "line='" + line + '\'' +
                ", linecode='" + linecode + '\'' +
                ", eta='" + eta + '\'' +
                ", route='" + route + '\'' +
                ", lineId=" + lineId +
                ", dest=" + dest +
                ", src=" + src +
                ", prefId=" + prefId +
                ", id=" + id +
                '}';
    }
}
