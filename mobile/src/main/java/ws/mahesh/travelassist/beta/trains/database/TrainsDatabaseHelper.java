package ws.mahesh.travelassist.beta.trains.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.databases.providers.SQLiteAssetHelper;

/**
 * Created by mahesh on 10/03/15.
 */

public class TrainsDatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "final.db";
    private static final int DATABASE_VERSION = StaticHolder.DATABASE_VERSION;
    SQLiteDatabase db = getReadableDatabase();
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

    public TrainsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAllStations() {
        String STMT = "SELECT * FROM train_station_master";
        Cursor c = db.rawQuery(STMT, null);
        c.moveToFirst();
        return c;

    }

    public Cursor getRoute(int src, int dest) {
        String STMT = "SELECT * FROM train_station_route_mapping WHERE SOURCE_STATION_ID=" + src + " AND DEST_STATION_ID=" + dest;
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;

    }

    public Cursor getRouteFromID(int id) {
        String STMT = "SELECT * FROM train_station_route_mapping WHERE _ID=" + id;
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;

    }


    public Cursor getAllLines() {
        String STMT = "SELECT * FROM train_line";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;
    }

    public Cursor getTrainsFromTo(String table, int src, int dest) {
        String STMT = "SELECT A.TRAIN_ID,A.LINE_ID,A.NO_OF_CAR,A.STATION_ID,B.STATION_ID,A.TIME_IN_MINUTES,B.TIME_IN_MINUTES,A.SPEED,A.DIRECTION,A.START_STN_ID,A.END_STN_ID,A.SPECIAL_INFO,A.HOLIDAY_ONLY,A.SUNDAY_ONLY,A.NOT_ON_SUNDAY,A.PLATFORM_NO FROM " + table + " AS A INNER JOIN " + table + " AS B ON A.TRAIN_ID=B.TRAIN_ID WHERE A.STATION_ID=" + src + " AND B.STATION_ID=" + dest + " AND A.TIME_IN_MINUTES<B.TIME_IN_MINUTES AND A.TIME_IN_MINUTES>0 ORDER BY B.TIME_IN_MINUTES";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;
    }


    public Cursor getTrainDetails(String table, int trainID) {
        String STMT = "SELECT A.TRAIN_ID,A.LINE_ID,A.NO_OF_CAR,A.STATION_ID,A.TIME_IN_MINUTES,A.SPEED,A.DIRECTION,A.START_STN_ID,A.END_STN_ID,A.SPECIAL_INFO,A.HOLIDAY_ONLY,A.SUNDAY_ONLY,A.NOT_ON_SUNDAY,A.PLATFORM_NO,A.PLATFORM_SIDE FROM " + table + " AS A WHERE A.TRAIN_ID=" + trainID + " ORDER BY A.TIME_IN_MINUTES";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;
    }

    public Cursor getTrainsFromToNow(String table, int src, int dest, int nowMins) {
        String STMT = "SELECT A.TRAIN_ID,A.LINE_ID,A.NO_OF_CAR,A.STATION_ID,B.STATION_ID,A.TIME_IN_MINUTES,B.TIME_IN_MINUTES,A.SPEED,A.DIRECTION,A.START_STN_ID,A.END_STN_ID,A.SPECIAL_INFO,A.HOLIDAY_ONLY,A.SUNDAY_ONLY,A.NOT_ON_SUNDAY,A.PLATFORM_NO FROM " + table + " AS A INNER JOIN " + table + " AS B ON A.TRAIN_ID=B.TRAIN_ID WHERE A.STATION_ID=" + src + " AND B.STATION_ID=" + dest + " AND A.TIME_IN_MINUTES<B.TIME_IN_MINUTES AND A.TIME_IN_MINUTES>" + nowMins + " ORDER BY B.TIME_IN_MINUTES LIMIT 1";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;
    }

    public Cursor getTrainsFromToNowReach(String table, int src, int dest, int nowMins) {
        String STMT = "SELECT A.TRAIN_ID,A.LINE_ID,A.NO_OF_CAR,A.STATION_ID,B.STATION_ID,A.TIME_IN_MINUTES,B.TIME_IN_MINUTES,A.SPEED,A.DIRECTION,A.START_STN_ID,A.END_STN_ID,A.SPECIAL_INFO,A.HOLIDAY_ONLY,A.SUNDAY_ONLY,A.NOT_ON_SUNDAY,A.PLATFORM_NO FROM " + table + " AS A INNER JOIN " + table + " AS B ON A.TRAIN_ID=B.TRAIN_ID WHERE A.STATION_ID=" + src + " AND B.STATION_ID=" + dest + " AND A.TIME_IN_MINUTES<B.TIME_IN_MINUTES AND B.TIME_IN_MINUTES<" + nowMins + " ORDER BY A.TIME_IN_MINUTES DESC LIMIT 1";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;
    }
}
