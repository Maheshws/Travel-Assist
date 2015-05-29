package ws.mahesh.travelassist.beta.cabs.now;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.cabs.now.models.LocationTrackerObject;
import ws.mahesh.travelassist.beta.location.LocationProviderCabs;

public class SimpleTravelNowActivity extends ActionBarActivity implements LocationProviderCabs.LocationCallback {

    private LocationProviderCabs mLocationProvider;
    private TextView distanceTV, timeTV, waitTV;
    private Button BStart, BPause, BStop;

    private String DistanceTravelled, TimeTaken, WaitTime;
    private int index;
    private long baseTime;

    private ArrayList<LocationTrackerObject> locationsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs_travel_now_simple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Travel Now - Distance");

        distanceTV = (TextView) findViewById(R.id.textViewDistance);
        timeTV = (TextView) findViewById(R.id.textViewTime);
        waitTV = (TextView) findViewById(R.id.textViewWaitTime);

        BStart = (Button) findViewById(R.id.buttonStart);
        BPause = (Button) findViewById(R.id.buttonPause);
        BStop = (Button) findViewById(R.id.buttonStop);

        BStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                mLocationProvider.connect();
            }
        });
        BStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.disconnect();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        mLocationProvider = new LocationProviderCabs(this, this);
    }

    private void clearAll() {
        locationsList.clear();
        index = 0;
        baseTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationProvider.disconnect();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabs_drive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleNewLocation(final Location location) {
        //Log.e("Location", location.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                addLocation(location);
            }
        }).start();

    }

    private void addLocation(Location location) {
        locationsList.add(new LocationTrackerObject(index, location, (System.currentTimeMillis() - baseTime)));
        updateValues();
    }

    private void updateValues() {
        double d = 0.0, tempd;
        long t = 0, totalTime = 0;
        for (int i = 1; i < locationsList.size(); i++) {
            tempd = (locationsList.get(i).getLocation().distanceTo(locationsList.get((i - 1)).getLocation()));
            d = d + tempd;
            if (tempd < 1)
                t = t + (locationsList.get(i).getTime() - (locationsList.get((i - 1)).getTime()));
        }
        if (locationsList.size() > 0)
            totalTime = locationsList.get(locationsList.size() - 1).getTime();
        updateViews(d, t, totalTime);

    }

    private void updateViews(double distance, long time, long totalTime) {
        DecimalFormat df = new DecimalFormat("#0.000");
        SimpleDateFormat f1 = new SimpleDateFormat("HH:mm:ss");
        f1.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d1 = new Date(time);
        Date d2 = new Date(totalTime);
        TimeTaken = f1.format(d2);
        WaitTime = f1.format(d1);
        distance = distance / 1000;
        DistanceTravelled = df.format(distance);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                distanceTV.setText(DistanceTravelled + "km");
                timeTV.setText(TimeTaken);
                waitTV.setText(WaitTime);
            }
        });


    }


}
