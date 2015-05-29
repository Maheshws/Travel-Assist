package ws.mahesh.travelassist.beta.bus.finder.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by mahesh on 14/03/15.
 */
public class MapsObject implements Parcelable {
    // Just cut and paste this for now
    public static final Parcelable.Creator<MapsObject> CREATOR = new Parcelable.Creator<MapsObject>() {
        public MapsObject createFromParcel(Parcel in) {
            return new MapsObject(in);
        }

        public MapsObject[] newArray(int size) {
            return new MapsObject[size];
        }
    };
    int busCount;
    int seq;
    String busName;
    String stopname;
    Location location;

    public MapsObject(int busCount, int seq, String busName, String stopname, Location location) {
        this.busCount = busCount;
        this.seq = seq;
        this.busName = busName;
        this.stopname = stopname;
        this.location = location;
    }

    private MapsObject(Parcel in) {
        // This order must match the order in writeToParcel()
        busCount = in.readInt();
        seq = in.readInt();
        busName = in.readString();
        stopname = in.readString();
        location = Location.CREATOR.createFromParcel(in);
        // Continue doing this for the rest of your member data
    }

    public int getBusCount() {
        return busCount;
    }

    public void setBusCount(int busCount) {
        this.busCount = busCount;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getStopname() {
        return stopname;
    }

    public void setStopname(String stopname) {
        this.stopname = stopname;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "MapsObject{" +
                "busCount=" + busCount +
                ", seq=" + seq +
                ", busName='" + busName + '\'' +
                ", stopname='" + stopname + '\'' +
                ", location=" + location +
                '}';
    }

    public void writeToParcel(Parcel out, int flags) {
        // Again this order must match the Question(Parcel) constructor
        out.writeInt(busCount);
        out.writeInt(seq);
        out.writeString(busName);
        out.writeString(stopname);
        location.writeToParcel(out, flags);
        // Again continue doing this for the rest of your member data
    }

    public int describeContents() {
        return 0;
    }
}
