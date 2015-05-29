package ws.mahesh.travelassist.beta.bus.finder.models;

import java.io.Serializable;

/**
 * Created by mahesh on 10/03/15.
 */
public class Bus3Object implements Serializable {
    int srcno;
    int destno;
    int bus1no;
    int stop1no;
    int bus2no;
    int stop2no;
    int bus3no;
    String srcStop;
    String bus1;
    String stop1;
    String bus2;
    String stop2;
    String bus3;
    String destStop;
    double distance;
    String eta1 = "ETA";
    String eta2 = "";
    String eta3 = "";
    String time;

    public Bus3Object(int srcno, int destno, int bus1no, int stop1no, int bus2no, int stop2no, int bus3no, String srcStop, String bus1, String stop1, String bus2, String stop2, String bus3, String destStop, double distance) {
        this.srcno = srcno;
        this.destno = destno;
        this.bus1no = bus1no;
        this.stop1no = stop1no;
        this.bus2no = bus2no;
        this.stop2no = stop2no;
        this.bus3no = bus3no;
        this.srcStop = srcStop;
        this.bus1 = bus1;
        this.stop1 = stop1;
        this.bus2 = bus2;
        this.stop2 = stop2;
        this.bus3 = bus3;
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

    public int getStop1no() {
        return stop1no;
    }

    public void setStop1no(int stop1no) {
        this.stop1no = stop1no;
    }

    public int getBus2no() {
        return bus2no;
    }

    public void setBus2no(int bus2no) {
        this.bus2no = bus2no;
    }

    public int getStop2no() {
        return stop2no;
    }

    public void setStop2no(int stop2no) {
        this.stop2no = stop2no;
    }

    public int getBus3no() {
        return bus3no;
    }

    public void setBus3no(int bus3no) {
        this.bus3no = bus3no;
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

    public String getStop1() {
        return stop1;
    }

    public void setStop1(String stop1) {
        this.stop1 = stop1;
    }

    public String getBus2() {
        return bus2;
    }

    public void setBus2(String bus2) {
        this.bus2 = bus2;
    }

    public String getStop2() {
        return stop2;
    }

    public void setStop2(String stop2) {
        this.stop2 = stop2;
    }

    public String getBus3() {
        return bus3;
    }

    public void setBus3(String bus3) {
        this.bus3 = bus3;
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

    public String getEta3() {
        return eta3;
    }

    public void setEta3(String eta3) {
        this.eta3 = eta3;
    }

    @Override
    public String toString() {
        return "Bus3Object{" +
                "time='" + time + '\'' +
                ", eta3='" + eta3 + '\'' +
                ", eta2='" + eta2 + '\'' +
                ", eta1='" + eta1 + '\'' +
                ", distance=" + distance +
                ", destStop='" + destStop + '\'' +
                ", bus3='" + bus3 + '\'' +
                ", stop2='" + stop2 + '\'' +
                ", bus2='" + bus2 + '\'' +
                ", stop1='" + stop1 + '\'' +
                ", bus1='" + bus1 + '\'' +
                ", srcStop='" + srcStop + '\'' +
                ", bus3no=" + bus3no +
                ", stop2no=" + stop2no +
                ", bus2no=" + bus2no +
                ", stop1no=" + stop1no +
                ", bus1no=" + bus1no +
                ", destno=" + destno +
                ", srcno=" + srcno +
                '}';
    }
}
