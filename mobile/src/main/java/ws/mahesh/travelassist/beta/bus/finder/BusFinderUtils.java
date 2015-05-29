package ws.mahesh.travelassist.beta.bus.finder;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus1Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus2Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus3Object;
import ws.mahesh.travelassist.beta.bus.finder.models.BusETAObject;

/**
 * Created by mahesh on 11/03/15.
 */
public class BusFinderUtils {

    private static BusDatabaseHelper dbHelper;
    private static ArrayList<BusETAObject> result = new ArrayList<>();
    private static ArrayList<String> validatorList = new ArrayList<>();

    public static Location makeLocation(double lat, double lng) {
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    public static double findDistance(Location l1, Location l2, Location l3) {
        double d1 = l1.distanceTo(l2);
        double d2 = l2.distanceTo(l3);
        return d1 + d2;
    }

    public static ArrayList<Bus2Object> validateRoutesFound2(ArrayList<Bus2Object> list, Context context) {
        dbHelper = new BusDatabaseHelper(context);
        int i = 0;
        while (i < list.size()) {
            if (list.get(i).getSrcStop().equals(list.get(i).getStop()) || list.get(i).getDestStop().equals(list.get(i).getStop()) || isVisited(list.get(i))) {
                list.remove(i);
            } else if (setUp2(list.get(i))) {
                list.remove(i);
            } else
                i++;
        }
        dbHelper.close();
        return list;
    }

    public static boolean isVisited(Bus2Object busTest2Object) {
        ArrayList<String> visited = new ArrayList<>();
        for (int i = 0; i < visited.size(); i++) {
            if (visited.get(i).equals(busTest2Object.getBus1() + "-" + busTest2Object.getBus2()))
                return true;
        }
        visited.add(busTest2Object.getBus1() + "-" + busTest2Object.getBus2());

        return false;
    }


    private static boolean setUp2(Bus2Object bus2Object) {
        boolean set1 = false, set2 = false, set4 = false;

        Cursor c = dbHelper.getBusTypeSequence(bus2Object.getBus1no(), bus2Object.getSrcno(), bus2Object.getStopno());
        if (c != null) {
            if (c.moveToFirst()) {
                if (validateETAObject(bus2Object.getBus1no(), bus2Object.getSrcno(), bus2Object.getStopno()))
                    result.add(new BusETAObject(bus2Object.getBus1no(), bus2Object.getSrcno(), bus2Object.getStopno(), c.getString(0)));
                set1 = true;
            }
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus2Object.getBus2no(), bus2Object.getStopno(), bus2Object.getDestno());
        if (c != null) {
            if (c.moveToFirst()) {
                if (validateETAObject(bus2Object.getBus2no(), bus2Object.getStopno(), bus2Object.getDestno()))
                    result.add(new BusETAObject(bus2Object.getBus2no(), bus2Object.getStopno(), bus2Object.getDestno(), c.getString(0)));
                set2 = true;
            }
        }
        c.close();
        if (!validatorList.contains(bus2Object.getBus1no() + "-" + bus2Object.getBus2no())) {

            set4 = true;
            validatorList.add(bus2Object.getBus1no() + "-" + bus2Object.getBus2no());
        }
        return !(set1 && set2 && set4);

    }

    public static double findDistance(Location l1, Location l2, Location l3, Location l4) {
        double d1 = l1.distanceTo(l2);
        double d2 = l2.distanceTo(l3);
        double d3 = l3.distanceTo(l4);
        return d1 + d2 + d3;
    }

    public static ArrayList<Bus3Object> validateRoutesFound3(ArrayList<Bus3Object> list, Context context) {
        dbHelper = new BusDatabaseHelper(context);
        int i = 0;
        while (i < list.size()) {
            if (setUp3(list.get(i))) {
                list.remove(i);
            } else
                i++;
        }
        dbHelper.close();
        return list;
    }

    private static boolean setUp3(Bus3Object bus3Object) {
        boolean set1 = false, set2 = false, set3 = false, set4 = false;

        Cursor c = dbHelper.getBusTypeSequence(bus3Object.getBus1no(), bus3Object.getSrcno(), bus3Object.getStop1no());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if (validateETAObject(bus3Object.getBus1no(), bus3Object.getSrcno(), bus3Object.getStop1no()))
                        result.add(new BusETAObject(bus3Object.getBus1no(), bus3Object.getSrcno(), bus3Object.getStop1no(), c.getString(0)));
                    set1 = true;
                } while (c.moveToNext());
            }
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus3Object.getBus2no(), bus3Object.getStop1no(), bus3Object.getStop2no());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if (validateETAObject(bus3Object.getBus2no(), bus3Object.getStop1no(), bus3Object.getStop2no()))
                        result.add(new BusETAObject(bus3Object.getBus2no(), bus3Object.getStop1no(), bus3Object.getStop2no(), c.getString(0)));
                    set2 = true;
                } while (c.moveToNext());
            }
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus3Object.getBus3no(), bus3Object.getStop2no(), bus3Object.getDestno());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if (validateETAObject(bus3Object.getBus3no(), bus3Object.getStop2no(), bus3Object.getDestno()))
                        result.add(new BusETAObject(bus3Object.getBus3no(), bus3Object.getStop2no(), bus3Object.getDestno(), c.getString(0)));
                    set3 = true;
                } while (c.moveToNext());
            }
            if (!validatorList.contains(bus3Object.getBus1no() + "-" + bus3Object.getBus2no() + "-" + bus3Object.getBus3no())) {
                set4 = true;
                validatorList.add(bus3Object.getBus1no() + "-" + bus3Object.getBus2no() + "-" + bus3Object.getBus3no());
            }
            c.close();
        }
        return !(set1 && set2 && set3 && set4);
        //return false;
    }

    public static ArrayList<BusETAObject> get1BusETAObject(ArrayList<Bus1Object> list) {
        result.clear();
        for (int i = 0; i < list.size(); i++) {
            if (validateETAObject(list.get(i).getBusno(), list.get(i).getSrcStop(), list.get(i).getDestStop()))
                result.add(new BusETAObject(list.get(i).getBusno(), list.get(i).getSrcStop(), list.get(i).getDestStop(), list.get(i).getType()));
        }
        return result;
    }

    public static ArrayList<BusETAObject> get2BusETAObject() {

        return result;
    }

    public static ArrayList<BusETAObject> get3BusETAObject() {

        return result;
    }

    private static boolean validateETAObject(int id, int src, int dest) {
        for (int j = 0; j < result.size(); j++) {
            if ((result.get(j).getId().equals(id + "-" + src + "-" + dest))) {
                return false;
            }
        }
        return true;
    }

    public static String getETAJsonList(ArrayList<BusETAObject> data) {
        String res = "{\"request\":[";
        for (int i = 0; i < data.size(); i++) {
            if (i == 0)
                res = res + data.get(i).getJson();
            else
                res = res + ", " + data.get(i).getJson();
        }
        return res + "]}";
    }

    public static void clearLists() {
        result.clear();
        validatorList.clear();
    }
}
