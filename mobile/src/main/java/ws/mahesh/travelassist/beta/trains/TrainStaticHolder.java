package ws.mahesh.travelassist.beta.trains;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;

/**
 * Created by mahesh on 27/03/15.
 */
public class TrainStaticHolder {
    public static ArrayList<StationObject> stationsList = new ArrayList<>();
    public static ArrayList<LineObject> linesList = new ArrayList<>();
    public static ArrayList<String> stationNameList = new ArrayList<>();


    public static int getStationID(String name) {

        for (int i = 0; i < stationsList.size(); i++) {
            if ((stationsList.get(i).getName()).equals(name))
                return stationsList.get(i).getId();
        }
        return 0;
    }

    public static String getStationName(int id) {

        for (int i = 0; i < stationsList.size(); i++) {
            if (stationsList.get(i).getId() == id)
                return stationsList.get(i).getName();
        }
        return null;
    }

    public static String getLineCode(int id) {
        for (int i = 0; i < linesList.size(); i++) {
            if (linesList.get(i).getId() == id)
                return linesList.get(i).getCode();
        }
        return "";
    }

    public static String getLine(int id) {
        for (int i = 0; i < linesList.size(); i++) {
            if (linesList.get(i).getId() == id)
                return linesList.get(i).getName();
        }
        return "";
    }

    public static int getLineIdFromStation(int src, int dest) {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 114, 115, 116, 117, 118, 119, 120, 121);
        List<Integer> list2 = Arrays.asList(29, 30, 31, 32, 33, 34, 35, 9, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 125, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 122, 61, 123, 62);
        List<Integer> list3 = Arrays.asList(29, 30, 31, 32, 33, 34, 35, 9, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 125, 51, 52, 53, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76);
        List<Integer> list4 = Arrays.asList(29, 30, 31, 77, 78, 79, 80, 81, 82, 83, 38, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97);
        List<Integer> list5 = Arrays.asList(29, 30, 31, 77, 78, 79, 80, 81, 98, 11, 12, 13, 14, 15, 16);
        List<Integer> list6 = Arrays.asList(16, 15, 14, 13, 12, 11, 98, 81, 82, 83, 38, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97);
        List<Integer> list7 = Arrays.asList(46, 103, 102, 101, 100, 99, 90, 91, 92, 93, 94, 95, 96, 97);
        List<Integer> list8 = Arrays.asList(46, 103, 102, 101, 100, 99, 89, 88);
        List<Integer> list9 = Arrays.asList(26, 113, 112, 111, 110, 125, 108, 107, 106, 105, 104, 97);
        List<Integer> list10 = Arrays.asList(12, 16, 21, 24, 26, 28, 114, 115, 116, 117, 118, 119, 120, 121);
        List<Integer> list11 = Arrays.asList(85, 127, 128, 129, 130, 131, 132);
        List<Integer> list12 = Arrays.asList(40, 133, 134, 135, 136, 137, 138, 139, 16, 140, 141, 142);
        List<Integer> list13 = Arrays.asList(26, 113, 112, 111, 110, 125, 49);
        if (list1.contains(src) && list1.contains(dest))
            return 1;
        else if (list2.contains(src) && list2.contains(dest))
            return 2;
        else if (list3.contains(src) && list3.contains(dest))
            return 2;
        else if (list4.contains(src) && list4.contains(dest))
            return 3;
        else if (list5.contains(src) && list5.contains(dest))
            return 3;
        else if (list6.contains(src) && list6.contains(dest))
            return 3;
        else if (list7.contains(src) && list7.contains(dest))
            return 4;
        else if (list8.contains(src) && list8.contains(dest))
            return 4;
        else if (list9.contains(src) && list9.contains(dest))
            return 5;
        else if (list10.contains(src) && list10.contains(dest))
            return 6;
        else if (list11.contains(src) && list11.contains(dest))
            return 7;
        else if (list12.contains(src) && list12.contains(dest))
            return 8;
        else if (list13.contains(src) && list13.contains(dest))
            return 5;

        return 0;
    }

    public static String getTableFromLine(int id) {
        String TABLE_NAME = "train_schedule_l";
        switch (id) {
            case 1:
                return TABLE_NAME + "1";
            case 2:
                return TABLE_NAME + "2";
            case 3:
                return TABLE_NAME + "34";
            case 4:
                return TABLE_NAME + "34";
            case 5:
                return TABLE_NAME + "56";
            case 6:
                return TABLE_NAME + "56";
            case 7:
                return TABLE_NAME + "7";
            case 8:
                return TABLE_NAME + "8";
        }
        return null;
    }

    public static String getTimeFromMins(int mins) {
        String s;
        if (mins < 1440) {
            int hours = mins / 60;
            int minutes = mins % 60;
            s = String.format("%02d:%02d", hours, minutes);
        } else {
            int hours = (mins - 1400) / 60;
            int minutes = (mins - 1400) % 60;
            s = String.format("%02d:%02d", hours, minutes);
        }
        try {
            DateFormat f1 = new SimpleDateFormat("HH:mm");
            Date d = f1.parse(s);
            DateFormat f2 = new SimpleDateFormat("h:mma");
            return f2.format(d).toUpperCase();
        } catch (Exception e) {
            return s;
        }
    }

    public static String getNearByStation() {
        int id = 1;
        double dist = 9999999999.9999;
        if (StaticHolder.currentLocation != null) {
            Location current = StaticHolder.currentLocation;
            for (int i = 0; i < stationsList.size(); i++) {
                if (current.distanceTo(stationsList.get(i).getLocation()) < dist) {
                    dist = current.distanceTo(stationsList.get(i).getLocation());
                    id = stationsList.get(i).getId();
                }
            }
            return getStationName(id);
        }
        return null;
    }

}
