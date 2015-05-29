package ws.mahesh.travelassist.beta.bus.finder.models;

import java.io.Serializable;

/**
 * Created by mahesh on 10/03/15.
 */
public class Bus2Object implements Serializable {
    int srcno;
    int destno;
    int bus1no;
    int stopno;
    int bus2no;
    String srcStop;
    String bus1;
    String stop;
    String bus2;
    String destStop;
    double distance;
    String eta1 = "ETA";
    String eta2 = "";
    String time;

    public Bus2Object(int srcno, int destno, int bus1no, int stopno, int bus2no, String srcStop, String bus1, String stop, String bus2, String destStop, double distance) {
        this.srcno = srcno;
        this.destno = destno;
        this.bus1no = bus1no;
        this.stopno = stopno;
        this.bus2no = bus2no;
        this.srcStop = srcStop;
        this.bus1 = bus1;
        this.stop = stop;
        this.bus2 = bus2;
        this.destStop = destStop;
        this.distance = distance;
    }

    public int getSrcno() {
        return srcno;
    }

    public void setSrcno(int srcno) {
        this.srcno = srcno;
    }

    public int getDestno() {
        return destno;
    }

    public void setDestno(int destno) {
        this.destno = destno;
    }

    public int getBus1no() {
        return bus1no;
    }

    public void setBus1no(int bus1no) {
        this.bus1no = bus1no;
    }

    public int getStopno() {
        return stopno;
    }

    public void setStopno(int stopno) {
        this.stopno = stopno;
    }

    public int getBus2no() {
        return bus2no;
    }

    public void setBus2no(int bus2no) {
        this.bus2no = bus2no;
    }

    public String getSrcStop() {
        return srcStop;
    }

    public void setSrcStop(String srcStop) {
        this.srcStop = srcStop;
    }

    public String getBus1() {
        return bus1;
    }

    public void setBus1(String bus1) {
        this.bus1 = bus1;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public String getBus2() {
        return bus2;
    }

    public void setBus2(String bus2) {
        this.bus2 = bus2;
    }

    public String getDestStop() {
        return destStop;
    }

    public void setDestStop(String destStop) {
        this.destStop = destStop;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEta1() {
        return eta1;
    }

    public void setEta1(String eta1) {
        this.eta1 = eta1;
    }

    public String getEta2() {
        return eta2;
    }

    public void setEta2(String eta2) {
        this.eta2 = eta2;
    }

    @Override
    public String toString() {
        return "Bus2Object{" +
                "srcno=" + srcno +
                ", destno=" + destno +
                ", bus1no=" + bus1no +
                ", stopno=" + stopno +
                ", bus2no=" + bus2no +
                ", srcStop='" + srcStop + '\'' +
                ", bus1='" + bus1 + '\'' +
                ", stop='" + stop + '\'' +
                ", bus2='" + bus2 + '\'' +
                ", destStop='" + destStop + '\'' +
                ", distance=" + distance +
                ", eta1='" + eta1 + '\'' +
                ", eta2='" + eta2 + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
