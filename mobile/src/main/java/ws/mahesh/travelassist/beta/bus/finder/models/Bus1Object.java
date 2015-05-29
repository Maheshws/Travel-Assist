package ws.mahesh.travelassist.beta.bus.finder.models;

import java.io.Serializable;

/**
 * Created by mahesh on 10/03/15.
 */
public class Bus1Object implements Serializable {
    int srcStop;
    int destStop;
    int busno;
    String srcName;
    String destName;
    String busName;
    String eta = "ETA";
    String nextBus;
    String type;
    int srcSeq;
    int destSeq;

    public Bus1Object(int srcStop, int destStop, int busno, String srcName, String destName, String busName, String type, int srcSeq, int destSeq) {
        this.srcStop = srcStop;
        this.destStop = destStop;
        this.busno = busno;
        this.srcName = srcName;
        this.destName = destName;
        this.busName = busName;
        this.type = type;
        this.srcSeq = srcSeq;
        this.destSeq = destSeq;
    }

    public Bus1Object(int srcStop, int destStop, int busno, String srcName, String destName, String busName, String eta, String type, int srcSeq, int destSeq) {
        this.srcStop = srcStop;
        this.destStop = destStop;
        this.busno = busno;
        this.srcName = srcName;
        this.destName = destName;
        this.busName = busName;
        this.eta = eta;
        this.type = type;
        this.srcSeq = srcSeq;
        this.destSeq = destSeq;
    }

    public int getSrcStop() {
        return srcStop;
    }

    public void setSrcStop(int srcStop) {
        this.srcStop = srcStop;
    }

    public int getDestStop() {
        return destStop;
    }

    public void setDestStop(int destStop) {
        this.destStop = destStop;
    }

    public int getBusno() {
        return busno;
    }

    public void setBusno(int busno) {
        this.busno = busno;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getNextBus() {
        return nextBus;
    }

    public void setNextBus(String nextBus) {
        this.nextBus = nextBus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSrcSeq() {
        return srcSeq;
    }

    public void setSrcSeq(int srcSeq) {
        this.srcSeq = srcSeq;
    }

    public int getDestSeq() {
        return destSeq;
    }

    public void setDestSeq(int destSeq) {
        this.destSeq = destSeq;
    }

    @Override
    public String toString() {
        return "Bus1Object{" +
                "destSeq=" + destSeq +
                ", srcSeq=" + srcSeq +
                ", type='" + type + '\'' +
                ", nextBus='" + nextBus + '\'' +
                ", eta='" + eta + '\'' +
                ", busName='" + busName + '\'' +
                ", destName='" + destName + '\'' +
                ", srcName='" + srcName + '\'' +
                ", busno=" + busno +
                ", destStop=" + destStop +
                ", srcStop=" + srcStop +
                '}';
    }
}
