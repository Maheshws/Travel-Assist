package ws.mahesh.travelassist.beta.trains.finder.models;

import android.location.Location;

/**
 * Created by mahesh on 23/03/15.
 */
public class StationObject {
    int id;
    String name;
    String code;
    Location location;
    String lineid;

    public StationObject(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    @Override
    public String toString() {
        return "StationObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", location=" + location +
                ", lineid='" + lineid + '\'' +
                '}';
    }
}
