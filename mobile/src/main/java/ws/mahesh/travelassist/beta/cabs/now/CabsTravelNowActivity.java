package ws.mahesh.travelassist.beta.cabs.now;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.farecalculator.base.FareCalculator;
import ws.mahesh.travelassist.beta.cabs.now.map.CabsRouteMapActivity;
import ws.mahesh.travelassist.beta.cabs.now.models.LocationTrackerObject;
import ws.mahesh.travelassist.beta.location.LocationProviderCabs;

public class CabsTravelNowActivity extends ActionBarActivity implements LocationProviderCabs.LocationCallback {

    private LocationProviderCabs mLocationProvider;
    private TextView distanceTV, timeTV, waitTV;
    private Button BStart, BStop;

    private TextView BaseAmount, ExtraAmount, ExtraTime, TotalAmount, NightAmount, ModeType;

    private String DistanceTravelled, TimeTaken, WaitTime, TotalFare = "0", TimeTakeW = "", WaitTimeW = "", DistanceTravelledW = "";
    private int index, ModeId;
    private long baseTime;
    private Bitmap wearBG;


    private ArrayList<LocationTrackerObject> locationsList = new ArrayList<>();

    private NotificationManager mNotificationManager;
    private int notificationID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs_travel_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ModeId = extras.getInt("id");
        }

        distanceTV = (TextView) findViewById(R.id.textViewDistance);
        timeTV = (TextView) findViewById(R.id.textViewTime);
        waitTV = (TextView) findViewById(R.id.textViewWaitTime);

        ModeType = (TextView) findViewById(R.id.textViewMode);

        BaseAmount = (TextView) findViewById(R.id.textViewBaseAmount);
        ExtraAmount = (TextView) findViewById(R.id.textViewextraDistanceAmount);
        ExtraTime = (TextView) findViewById(R.id.textViewWaitTimeAmount);
        TotalAmount = (TextView) findViewById(R.id.textViewTotalAmount);
        NightAmount = (TextView) findViewById(R.id.textViewTotalAmountNight);

        BStart = (Button) findViewById(R.id.buttonStart);
        //BPause = (Button) findViewById(R.id.buttonPause);
        BStop = (Button) findViewById(R.id.buttonStop);

        mLocationProvider = new LocationProviderCabs(this, this);

        ModeType.setText(StaticCabsHolder.getCab(ModeId).getName() + " - " + StaticCabsHolder.getCab(ModeId).getCity());
        getSupportActionBar().setTitle("Travel Now - " + StaticCabsHolder.getCab(ModeId).getName());

        clearAll();
        BStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                displayNotification();
                mLocationProvider.connect();

            }
        });
        BStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.disconnect();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                cancelNotification();
            }
        });

        wearBG = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
    }

    private void clearAll() {
        locationsList.clear();
        index = 0;
        baseTime = System.currentTimeMillis();
        BaseAmount.setText("Rs. 0");
        ExtraAmount.setText("Rs. 0");
        ExtraTime.setText("Rs. 0");
        TotalAmount.setText("Rs. 0");
        NightAmount.setText("Rs. 0");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelNotification();
        mLocationProvider.disconnect();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
            if (tempd < 3)
                t = t + (locationsList.get(i).getTime() - (locationsList.get((i - 1)).getTime()));
        }
        if (locationsList.size() > 0)
            totalTime = locationsList.get(locationsList.size() - 1).getTime();
        updateViews(d, t, totalTime);
        updateFare(d, t);
    }


    private void updateViews(double distance, long time, long totalTime) {
        DecimalFormat df = new DecimalFormat("#0.000");
        DecimalFormat dfw = new DecimalFormat("#0.00");
        SimpleDateFormat f1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat f2w = new SimpleDateFormat("mm:ss");
        f1.setTimeZone(TimeZone.getTimeZone("GMT"));
        f2w.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d1 = new Date(time);
        Date d2 = new Date(totalTime);
        TimeTaken = f1.format(d2);
        WaitTime = f1.format(d1);
        TimeTakeW = f2w.format(d2);
        WaitTimeW = f2w.format(d1);
        distance = distance / 1000;
        DistanceTravelled = df.format(distance);
        DistanceTravelledW = dfw.format(distance);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                distanceTV.setText("Distance : " + DistanceTravelled + " km");
                timeTV.setText("Total Time : " + TimeTaken);
                waitTV.setText("Waiting Time : " + WaitTime);
            }
        });

    }

    private void updateFare(double d, long t) {
        final DecimalFormat df = new DecimalFormat("#0.0");
        final FareCalculator fareCalculator = new FareCalculator(StaticCabsHolder.getCab(ModeId), d / 1000, TimeUnit.MILLISECONDS.toMinutes(t));
        TotalFare = df.format(fareCalculator.getTotalAmount());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseAmount.setText("Rs. " + df.format(fareCalculator.getBaseAmount()));
                ExtraAmount.setText("Rs. " + df.format(fareCalculator.getExtraDistanceAmount()));
                ExtraTime.setText("Rs. " + df.format(fareCalculator.getExtraWaitingAmount()));
                TotalAmount.setText("Rs. " + df.format(fareCalculator.getTotalAmount()));
                NightAmount.setText("Rs. " + df.format(fareCalculator.getNightAmount()));
                updateNotification();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabs_travel_now, menu);
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
        if (id == R.id.action_screenshot) {
            makeScreenshot();
            return true;
        }
        if (id == R.id.open_map) {
            StaticCabsHolder.locationsList = locationsList;
            startActivity(new Intent(CabsTravelNowActivity.this, CabsRouteMapActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeScreenshot() {
        Bitmap bitmap = takeScreenshot();
        String imgName = "/Travel Assist/screenshot" + System.currentTimeMillis() + ".jpeg";
        StaticHolder.saveBitmap(bitmap, imgName);
        openShareImageDialog(Environment.getExternalStorageDirectory() + imgName);
        Toast.makeText(this, "Screenshot save in " + imgName, Toast.LENGTH_LONG).show();
    }

    private Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void openShareImageDialog(String filePath) {
        if (!filePath.equals("")) {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra((Intent.EXTRA_TEXT), StaticHolder.SSShareText);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent, "Share Image"));
        }
    }

    protected void displayNotification() {
        Log.i("Start", "notification");

      /* Invoking the default notification service */
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(StaticCabsHolder.getCab(ModeId).getName() + " Fare : " + TotalFare);
        mBuilder.setContentText("Distance :" + DistanceTravelledW + "kms, Waiting Time " + WaitTimeW);
        mBuilder.setSmallIcon(R.drawable.taxi);
        mBuilder.extend(new NotificationCompat.WearableExtender().setBackground(wearBG));

        mBuilder.setAutoCancel(true);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

    protected void cancelNotification() {
        Log.i("Cancel", "notification");
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
    }

    protected void updateNotification() {

      /* Invoking the default notification service */
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(StaticCabsHolder.getCab(ModeId).getName() + " Fare : " + TotalFare);
        mBuilder.setContentText("Distance : " + DistanceTravelledW + "kms, Waiting Time " + WaitTimeW);
        mBuilder.setSmallIcon(R.drawable.taxi);
        mBuilder.extend(new NotificationCompat.WearableExtender().setBackground(wearBG));

        mBuilder.setAutoCancel(true);

        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      /* Update the existing notification using same notification ID */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
