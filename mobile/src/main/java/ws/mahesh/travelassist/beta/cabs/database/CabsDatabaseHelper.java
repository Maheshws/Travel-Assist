package ws.mahesh.travelassist.beta.cabs.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.cabs.models.CabsObject;
import ws.mahesh.travelassist.beta.databases.providers.SQLiteAssetHelper;

/**
 * Created by mahesh on 10/03/15.
 */
public class CabsDatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "final.db";
    private static final int DATABASE_VERSION = StaticHolder.DATABASE_VERSION;
    SQLiteDatabase db = getReadableDatabase();
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

    public CabsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getAllCabsFare() {
        String STMT = "SELECT * FROM cabs_base";
        Cursor c = db.rawQuery(STMT, null);

        c.moveToFirst();
        return c;

    }

    public void addCustomCab(CabsObject object) {
        String STMT = "INSERT INTO cabs_base (NAME,CITY,BASE_FARE,BASE_DISTANCE,BASE_WAIT_TIME,FARE_PER_UNIT,DISTANCE_PER_UNIT,WAIT_TIME_PER_UNIT,FARE_PER_WAIT_TIME,NIGHT_FARE_MULTIPLIER,CAB_CUSTOM) VALUES ('" + object.getName() + "','" + object.getCity() + "'," + object.getBaseFare() + "," + object.getBaseDistance() + "," + object.getBaseWaitTime() + "," + object.getFarePerUnit() + "," + object.getDistancePerUnit() + "," + object.getWaitTimePerUnit() + "," + object.getFarePerWaitTime() + "," + object.getNightMultiplier() + "," + object.getCustomCab() + ");";
        db.execSQL(STMT);
    }

    public void deleteCustomCab(int id) {
        String STMT = "DELETE FROM cabs_base WHERE _ID=" + id;
        db.execSQL(STMT);
    }

}
