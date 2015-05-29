package ws.mahesh.travelassist.beta.trains.now;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;
import ws.mahesh.travelassist.beta.trains.finder.models.TrainFinderObject;
import ws.mahesh.travelassist.beta.trains.now.adapter.TravelNowTrainScheduleFinderAdapter;
import ws.mahesh.travelassist.beta.trains.now.model.TravelNowTrainScheduleObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class TravelNowTrainViewActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private int Route_ID, nowMins = 0;
    private TrainsDatabaseHelper dbHelper;

    private TextView RouteText;
    private String ToolbarTitle;
    private String TravelType;

    private ProgressDialog progress;

    private ArrayList<String> existList = new ArrayList<>();

    private TravelNowTrainScheduleFinderAdapter adapter;
    private ArrayList<TrainFinderObject> trainResultList = new ArrayList<>();
    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();
    private ArrayList<TravelNowTrainScheduleObject> trains = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_route_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Train Route View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Route_ID = extras.getInt("id");
            nowMins = extras.getInt("time");
            TravelType = extras.getString("travelType");
        }
        progress = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.route_view_recyclerview);
        RouteText = (TextView) findViewById(R.id.textViewRoute);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setVisibility(View.GONE);
        progress.setTitle("Please Wait");
        progress.setMessage("Finding best trains for you.");
        progress.setCancelable(false);
        progress.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setup();
                findRoutes();
            }
        }).start();

    }

    private void clearAll() {
        trainResultList.clear();
        trains.clear();
    }

    private void findRoutes() {
        clearAll();
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor c = dbHelper.getRouteFromID(Route_ID);
        if (c != null) {
            if (c.moveToFirst()) {
                ToolbarTitle = getStopName(c.getInt(1)) + " - " + getStopName(c.getInt(2));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RouteText.setText(ToolbarTitle);
                    }
                });
                String nodes = c.getString(3);
                if (nodes.contains("|")) {
                    int SourceID = c.getInt(1);
                    int DestID = c.getInt(2);
                    int src = SourceID;
                    int dest;
                    for (String retval : nodes.split("\\|")) {
                        dest = Integer.parseInt(retval);
                        getTrainFromDB(src, dest);
                        src = dest;
                    }
                    getTrainFromDB(src, DestID);
                } else if (nodes.equals("0")) {
                    getTrainFromDB(c.getInt(1), c.getInt(2));
                } else {
                    int SourceID = c.getInt(1);
                    int DestID = c.getInt(2);
                    int MidID = Integer.parseInt(c.getString(3));
                    getTrainFromDB(SourceID, MidID);
                    getTrainFromDB(MidID, DestID);
                }
            }
        }

        c.close();
        getTrainNowSchedule();
        dbHelper.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //show data
                setViews();
                progress.dismiss();
            }
        });
    }

    private void getTrainNowSchedule() {
        if (nowMins == 0) {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            nowMins = (hours * 60) + minutes;
        }
        if (TravelType.equals("Leave")) {
            getLeaveTimeTrainSchedule();
        } else if (TravelType.equals("Reach")) {
            getReachTimeTrainSchedule();
        }

    }

    private void getLeaveTimeTrainSchedule() {
        for (int i = 0; i < trainResultList.size(); i++) {
            Cursor c = dbHelper.getTrainsFromToNow(TrainStaticHolder.getTableFromLine(trainResultList.get(i).getLineId()), trainResultList.get(i).getSrc(), trainResultList.get(i).getDest(), nowMins);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if ((c.getInt(6) - c.getInt(5) < 360)) {
                            trains.add(new TravelNowTrainScheduleObject(c.getInt(0), c.getInt(2), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getInt(9), c.getInt(10), c.getInt(13), c.getInt(12), c.getInt(14), c.getString(8), c.getString(7), c.getString(15), trainResultList.get(i).getLineId()));
                        }
                    } while (c.moveToNext());
                }
            }

            if (trains.size() <= i) {
                c = dbHelper.getTrainsFromTo(TrainStaticHolder.getTableFromLine(trainResultList.get(i).getLineId()), trainResultList.get(i).getSrc(), trainResultList.get(i).getDest());
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            if ((c.getInt(6) - c.getInt(5) < 360)) {
                                trains.add(new TravelNowTrainScheduleObject(c.getInt(0), c.getInt(2), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getInt(9), c.getInt(10), c.getInt(13), c.getInt(12), c.getInt(14), c.getString(8), c.getString(7), c.getString(15), trainResultList.get(i).getLineId()));
                                break;
                            }
                        } while (c.moveToNext());
                    }
                }
            }
            nowMins = trains.get(trains.size() - 1).getDest_time() + 5;
            if (nowMins > 1440)
                nowMins = nowMins - 1440;
            c.close();

        }
    }

    private void getReachTimeTrainSchedule() {
        int size = 0;
        for (int i = trainResultList.size() - 1; i >= 0; i--) {
            if (nowMins < 300) {
                nowMins = 1440 + nowMins;
            }
            Cursor c = dbHelper.getTrainsFromToNowReach(TrainStaticHolder.getTableFromLine(trainResultList.get(i).getLineId()), trainResultList.get(i).getSrc(), trainResultList.get(i).getDest(), nowMins);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if ((c.getInt(6) - c.getInt(5) < 360)) {
                            trains.add(new TravelNowTrainScheduleObject(c.getInt(0), c.getInt(2), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getInt(9), c.getInt(10), c.getInt(13), c.getInt(12), c.getInt(14), c.getString(8), c.getString(7), c.getString(15), trainResultList.get(i).getLineId()));
                        }
                    } while (c.moveToNext());
                }
            }

            if (trains.size() <= size) {
                c = dbHelper.getTrainsFromTo(TrainStaticHolder.getTableFromLine(trainResultList.get(i).getLineId()), trainResultList.get(i).getSrc(), trainResultList.get(i).getDest());
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            if ((c.getInt(6) - c.getInt(5) < 360)) {
                                trains.add(new TravelNowTrainScheduleObject(c.getInt(0), c.getInt(2), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getInt(9), c.getInt(10), c.getInt(13), c.getInt(12), c.getInt(14), c.getString(8), c.getString(7), c.getString(15), trainResultList.get(i).getLineId()));
                                break;
                            }
                        } while (c.moveToNext());
                    }
                }
            }
            nowMins = trains.get(trains.size() - 1).getSrc_time() - 5;

            c.close();
            size++;
        }
        Collections.reverse(trains);
    }


    private void getTrainFromDB(int src, int dest) {
        existList.clear();
        Cursor c = dbHelper.getRoute(src, dest);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String nodes = c.getString(3);
                    if (nodes.equals("0") && !existList.contains(getStopName(src) + " - " + getStopName(dest))) {
                        if (c.getString(5) == null || c.getString(5).equals(""))
                            trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), src, dest, TrainStaticHolder.getLineIdFromStation(src, dest), getStopName(src) + " - " + getStopName(dest), c.getString(6), getLineCode(c.getInt(5)), getLine(c.getInt(5))));
                        else
                            trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), src, dest, c.getInt(5), getStopName(src) + " - " + getStopName(dest), c.getString(6), getLineCode(c.getInt(5)), getLine(c.getInt(5))));
                        existList.add(getStopName(src) + " - " + getStopName(dest));
                    }
                } while (c.moveToNext());
            }
        }
        c.close();
    }

    private void setViews() {
        recyclerView.setVisibility(View.GONE);
        if (trainResultList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TravelNowTrainScheduleFinderAdapter(this, trains);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        getSupportActionBar().setTitle("Reach " + TrainStaticHolder.getStationName(trains.get(trains.size() - 1).getDest_station_id()) + " by " + TrainStaticHolder.getTimeFromMins(trains.get(trains.size() - 1).getDest_time()));
    }

    private int getStopID(String name) {

        for (int i = 0; i < stationsList.size(); i++) {
            if ((stationsList.get(i).getName()).equals(name))
                return stationsList.get(i).getId();
        }
        return 0;
    }

    private String getStopName(int id) {

        for (int i = 0; i < stationsList.size(); i++) {
            if (stationsList.get(i).getId() == id)
                return stationsList.get(i).getName();
        }
        return null;
    }

    private String getLineCode(int id) {
        for (int i = 0; i < linesList.size(); i++) {
            if (linesList.get(i).getId() == id)
                return linesList.get(i).getCode();
        }
        return "";
    }


    private String getLine(int id) {
        for (int i = 0; i < linesList.size(); i++) {
            if (linesList.get(i).getId() == id)
                return linesList.get(i).getName();
        }
        return "";
    }

    private void setup() {
        stationsList = TrainStaticHolder.stationsList;
        linesList = TrainStaticHolder.linesList;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel_now_train_view, menu);
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

}
