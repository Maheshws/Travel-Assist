package ws.mahesh.travelassist.beta.bus.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.bus.finder.BusFinderUtils;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus3FinderObject;
import ws.mahesh.travelassist.beta.databases.providers.SQLiteAssetHelper;

/**
 * Created by mahesh on 10/03/15.
 */
public class BusDatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "final.db";
    private static final int DATABASE_VERSION = StaticHolder.DATABASE_VERSION;
    private static ArrayList<String> bus3List = new ArrayList<>();
    private static ArrayList<Integer> stop3List = new ArrayList<>();
    SQLiteDatabase db = getReadableDatabase();
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    private ArrayList<Bus3FinderObject> bus3FinderObjects = new ArrayList<>();
    private int bus3, stop3;

    public BusDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void clearAllLists() {
        bus3List.clear();
        stop3List.clear();
    }

    public Cursor getAllBusStops() {
        String[] sqlSelect = {"stop_id", "stop_name", "stop_lat", "stop_lng"};
        String sqlTables = "bus_stops";

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, null, null,
                null, null, null);

        c.moveToFirst();
        return c;

    }

    public Cursor getDirectBus(int stops, int stopd) {
        String STMT = "select A.bus_id as BUS, A.type as TYPE, A.stop_sequence as StartSeq, B.stop_sequence as EndSeq from bus_routes as A INNER JOIN bus_routes as B ON A.bus_id=B.bus_id where A.stop_id=" + stops + " AND B.stop_id=" + stopd + " AND A.stop_sequence<B.stop_sequence AND A.type=B.type";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;

    }

    public Cursor getBus2(int stops, int stopd) {
        String STMT = "select DISTINCT A.bus_id as BUS1,B.bus_id as BUS2, A.stop_id as STOP from bus_routes as A INNER JOIN bus_routes as B ON A.stop_id=B.stop_id and A.type=B.type where A.bus_id IN( Select bus_id from bus_routes where stop_id=" + stops + ") AND B.bus_id IN( Select bus_id from bus_routes where stop_id=" + stopd + ")";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;

    }

    public Cursor getBus3A(int stops, int stopd, Location dest) {
        String STMT = "select DISTINCT B.bus_id as BUS3, A.stop_id as STOP, C.stop_lat as LAT,C.stop_lng as LNG from bus_routes as A INNER JOIN bus_routes as B ON A.stop_id=B.stop_id and A.type=B.type INNER JOIN bus_stops as C ON B.stop_id=C.stop_id where A.bus_id IN( Select bus_id from bus_routes where stop_id IN (select stop_id from bus_routes where bus_id in( select bus_id from bus_routes where stop_id=" + stops + "))) AND B.bus_id IN( Select bus_id from bus_routes where stop_id=" + stopd + ")";
        Cursor c = db.rawQuery(STMT, null);
        stop3 = stopd;
        c.moveToFirst();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus3FinderObjects.add(new Bus3FinderObject(c.getInt(1), c.getInt(0), BusFinderUtils.makeLocation(c.getDouble(2), c.getDouble(3)), dest));
                } while (c.moveToNext());
            }
            Collections.sort(bus3FinderObjects, new Comparator<Bus3FinderObject>() {
                @Override
                public int compare(Bus3FinderObject lhs, Bus3FinderObject rhs) {
                    return Double.compare(lhs.getDistance(), rhs.getDistance());
                }
            });
            int i = 0;
            do {
                stop3 = bus3FinderObjects.get(i).getStop();
                bus3 = bus3FinderObjects.get(i).getBus();
                i++;
            } while (i < bus3FinderObjects.size() && !bus3Validated());
            Cursor n = getBus2(stops, stop3);
            //Log.e("BUS"+i, bus3List.toString());
            return n;
        }

        return null;
    }

    private boolean bus3Validated() {
        if (bus3List.contains(bus3 + "-" + stop3))
            return false;
        else {
            bus3List.add(bus3 + "-" + stop3);
            return true;
        }
    }

    public Cursor getBus3B(int stops, int stopd, Location dest) {
        String STMT = "select DISTINCT B.bus_id as BUS3, A.stop_id as STOP, C.stop_lat as LAT,C.stop_lng as LNG from bus_routes as A INNER JOIN bus_routes as B ON A.stop_id=B.stop_id and A.type=B.type INNER JOIN bus_stops as C ON B.stop_id=C.stop_id where A.bus_id IN( Select bus_id from bus_routes where  stop_id=" + stops + ") AND B.bus_id IN( Select bus_id from bus_routes where stop_id IN (select stop_id from bus_routes where bus_id in( select bus_id from bus_routes where stop_id=" + stopd + ")))";
        Cursor c = db.rawQuery(STMT, null);
        stop3 = stopd;
        c.moveToFirst();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus3FinderObjects.add(new Bus3FinderObject(c.getInt(1), c.getInt(0), BusFinderUtils.makeLocation(c.getDouble(2), c.getDouble(3)), dest));
                } while (c.moveToNext());
            }

            int i = 0;
            do {
                stop3 = bus3FinderObjects.get(i).getStop();
                bus3 = bus3FinderObjects.get(i).getBus();
                i++;
            } while (i < bus3FinderObjects.size() && !bus3Validated());
            Cursor n = getBus2(stops, stop3);
            //Log.e("BUS"+i, bus3List.toString());
            return n;
        }

        return null;
    }

    public Cursor getAllBuses() {
        String STMT = "select id,route_short_name,route_long_name from bus_info";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();

        return c;
    }

    public Cursor getBusInfoBetween(int bus, int src, int dest, String type) {
        String STMT = "select A.stop_sequence,A.stop_id,B.stop_name,B.stop_lat,B.stop_lng from bus_routes as A inner join bus_stops as B on A.stop_id=B.stop_id where bus_id=" + bus + " and type='" + type + "' and stop_sequence between " + src + " and " + dest;
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();

        return c;
    }

    public Cursor getBusTypeSequence(int bus, int src, int dest) {
        String STMT = "select A.type as TYPE, A.stop_sequence as StartSeq, B.stop_sequence as EndSeq from bus_routes as A INNER JOIN bus_routes as B ON A.bus_id=B.bus_id where A.stop_id=" + src + " AND B.stop_id=" + dest + " AND A.bus_id=" + bus + " AND A.stop_sequence<B.stop_sequence AND A.type=B.type";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();

        return c;
    }

    public int getBusStop3() {
        return stop3;
    }

    public int getBusId3() {
        return bus3;
    }

    public Cursor getAllBusInfo() {
        String STMT = "select * from bus_info";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();

        return c;
    }

    public Cursor getStopsForBus(int busId, String busType) {
        String STMT = "SELECT A.stop_sequence,A.stop_id,B.stop_name,B.stop_lat,B.stop_lng FROM bus_routes as A INNER JOIN bus_stops as B on A.stop_id=B.stop_id  WHERE bus_id=" + busId + " AND type='" + busType + "'";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();

        return c;
    }
}
