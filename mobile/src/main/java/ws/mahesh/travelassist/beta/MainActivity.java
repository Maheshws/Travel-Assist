package ws.mahesh.travelassist.beta;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import ws.mahesh.travelassist.beta.bus.BusMainActivity;
import ws.mahesh.travelassist.beta.bus.StaticBusHolder;
import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.bus.finder.BusFinderUtils;
import ws.mahesh.travelassist.beta.bus.finder.models.BusObject;
import ws.mahesh.travelassist.beta.bus.finder.models.StopObject;
import ws.mahesh.travelassist.beta.cabs.CabsMainActivity;
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.auto.AutosMainActivity;
import ws.mahesh.travelassist.beta.cabs.database.CabsDatabaseHelper;
import ws.mahesh.travelassist.beta.cabs.models.CabsObject;
import ws.mahesh.travelassist.beta.location.LocationProvider;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.TrainsMainActivity;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;


public class MainActivity extends ActionBarActivity implements LocationProvider.LocationCallback {
    private LinearLayout BusLayout;
    private ImageView BusImage;
    private TextView BusText;
    private LinearLayout TrainLayout;
    private ImageView TrainImage;
    private TextView TrainText;
    private LinearLayout CabsLayout;
    private ImageView CabsImage;
    private TextView CabsText;
    private LinearLayout AutoLayout;
    private ImageView AutoImage;
    private TextView AutoText;
    private LocationProvider mLocationProvider;


    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();
    private ArrayList<String> StopNameList = new ArrayList<>();
    private ArrayList<CabsObject> cabsList = new ArrayList<>();

    private ArrayList<BusObject> BusList = new ArrayList<>();
    private ArrayList<StopObject> BusStopsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLocationProvider = new LocationProvider(this, this);
        mLocationProvider.connect();

        BusLayout = (LinearLayout) findViewById(R.id.bus_selection);
        BusImage = (ImageView) findViewById(R.id.imageViewBestBus);
        //  BusText = (TextView) findViewById(R.id.textViewBestBus);

        BusLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BusMainActivity.class));
            }
        });

        TrainLayout = (LinearLayout) findViewById(R.id.train_selection);
        TrainImage = (ImageView) findViewById(R.id.imageViewTrains);
        // TrainText = (TextView) findViewById(R.id.textViewTrains);

        TrainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TrainsMainActivity.class));
            }
        });

        CabsLayout = (LinearLayout) findViewById(R.id.cabs_selection);
        CabsImage = (ImageView) findViewById(R.id.imageViewCabs);
        //  CabsText = (TextView) findViewById(R.id.textViewCabs);

        CabsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CabsMainActivity.class));
            }
        });

        AutoLayout = (LinearLayout) findViewById(R.id.auto_selection);
        AutoImage = (ImageView) findViewById(R.id.imageViewAuto);
        // AutoText = (TextView) findViewById(R.id.textViewAuto);

        AutoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AutosMainActivity.class));
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                setup();
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationProvider.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            copyShareMessage();
            return true;
        }
        if (id == R.id.action_feedback) {
            startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void copyShareMessage() {
        String Message = "Join Open Beta program of Travel Assist https://betas.to/yY3ksHAQ";
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(Message);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Share", Message);
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(this, "Share text copied to clipboard", Toast.LENGTH_LONG).show();
    }

    private void setup() {
        TrainsDatabaseHelper dbHelper;
        CabsDatabaseHelper cabsDatabaseHelper;
        cabsDatabaseHelper = new CabsDatabaseHelper(this);
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor mCursor = dbHelper.getAllStations();
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    stationsList.add(new StationObject(mCursor.getInt(0), mCursor.getString(1), BusFinderUtils.makeLocation(mCursor.getDouble(3), mCursor.getDouble(4))));
                    StopNameList.add(mCursor.getString(1));
                } while (mCursor.moveToNext());
            }
        }
        mCursor.close();
        TrainStaticHolder.stationsList = stationsList;
        TrainStaticHolder.stationNameList = StopNameList;
        mCursor = dbHelper.getAllLines();
        dbHelper.close();
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    linesList.add(new LineObject(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2)));
                } while (mCursor.moveToNext());
            }
        }
        mCursor.close();
        TrainStaticHolder.linesList = linesList;
        mCursor = cabsDatabaseHelper.getAllCabsFare();
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    cabsList.add(new CabsObject(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), mCursor.getDouble(3), mCursor.getDouble(4), mCursor.getDouble(5), mCursor.getDouble(6), mCursor.getDouble(7), mCursor.getDouble(8), mCursor.getDouble(9), mCursor.getDouble(10), mCursor.getInt(11)));
                } while (mCursor.moveToNext());
            }
        }
        mCursor.close();
        StaticCabsHolder.cabsList = cabsList;
        cabsDatabaseHelper.close();
        setUpBus();

    }

    private void setUpBus() {
        BusDatabaseHelper dbHelper = new BusDatabaseHelper(this);
        Cursor c = dbHelper.getAllBusStops();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    BusStopsList.add(new StopObject(c.getInt(0), c.getString(1), BusFinderUtils.makeLocation(c.getDouble(2), c.getDouble(3))));
                } while (c.moveToNext());
            }
        }
        c.close();
        c = dbHelper.getAllBuses();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    BusList.add(new BusObject(c.getInt(0), c.getString(1), c.getString(2)));
                } while (c.moveToNext());
            }
        }
        c.close();
        dbHelper.close();
        StaticBusHolder.BusList = BusList;
        StaticBusHolder.StopsList = BusStopsList;
    }

    @Override
    public void handleNewLocation(Location location) {
        if (StaticHolder.currentLocation != null && StaticHolder.currentLocation.distanceTo(location) < 5)
            mLocationProvider.disconnect();
        else
            StaticHolder.currentLocation = location;
        //Log.d("LOCATION",location.toString());
        //double currentLatitude = location.getLatitude();
        //double currentLongitude = location.getLongitude();
    }
}
