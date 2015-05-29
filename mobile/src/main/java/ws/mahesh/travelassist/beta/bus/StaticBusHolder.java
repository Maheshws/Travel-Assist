package ws.mahesh.travelassist.beta.bus;

import android.location.Location;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.bus.finder.models.BusObject;
import ws.mahesh.travelassist.beta.bus.finder.models.StopObject;

/**
 * Created by mahesh on 07/04/15.
 */
public class StaticBusHolder {

    public static ArrayList<BusObject> BusList = new ArrayList<>();
    public static ArrayList<StopObject> StopsList = new ArrayList<>();


    public static String getBusStopName(int id) {
        for (int i = 0; i < StopsList.size(); i++) {
            if ((StopsList.get(i).getStopid()) == id)
                return StopsList.get(i).getStopname();
        }
        return "";
    }

    public static String getBusName(int id) {
        for (int i = 0; i < BusList.size(); i++) {
            if ((BusList.get(i).getBusid()) == id)
                return BusList.get(i).getBusno();
        }
        return "";
    }

    public static String getNearByStop() {
        int id = 1;
        double dist = 9999999999.9999;
        if (StaticHolder.currentLocation != null) {
            Location current = StaticHolder.currentLocation;
            for (int i = 0; i < StopsList.size(); i++) {
                if (current.distanceTo(StopsList.get(i).getLocation()) < dist) {
                    dist = current.distanceTo(StopsList.get(i).getLocation());
                    id = StopsList.get(i).getStopid();
                }
            }
            return getBusStopName(id) + "-" + id;
        }
        return "";
    }
}
