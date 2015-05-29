package ws.mahesh.travelassist.beta.cabs;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.cabs.models.CabsObject;
import ws.mahesh.travelassist.beta.cabs.now.models.LocationTrackerObject;

/**
 * Created by mahesh on 30/03/15.
 */
public class StaticCabsHolder {
    public static ArrayList<CabsObject> cabsList = new ArrayList<>();
    public static ArrayList<LocationTrackerObject> locationsList = null;


    public static ArrayList<CabsObject> getCabsList() {
        return cabsList;
    }

    public static ArrayList<CabsObject> getCabsCustomCabsList() {
        ArrayList<CabsObject> result = new ArrayList<>();
        for (int i = 0; i < cabsList.size(); i++) {
            if (cabsList.get(i).getCustomCab() != 0) {
                result.add(cabsList.get(i));
            }
        }
        return result;
    }

    public static void removeCustomCab(int id) {
        for (int i = 0; i < cabsList.size(); i++) {
            if (cabsList.get(i).getId() == id) {
                cabsList.remove(cabsList.get(i));
            }
        }
    }

    public static CabsObject getCab(int id) {
        for (int i = 0; i < cabsList.size(); i++) {
            if (cabsList.get(i).getId() == id) {
                return cabsList.get(i);
            }
        }
        return null;
    }
}
