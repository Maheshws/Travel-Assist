package ws.mahesh.travelassist.beta.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.databases.providers.SQLiteAssetHelper;

/**
 * Created by mahesh on 10/03/15.
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "final.db";
    private static final int DATABASE_VERSION = StaticHolder.DATABASE_VERSION;
    SQLiteDatabase db = getReadableDatabase();
    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}
