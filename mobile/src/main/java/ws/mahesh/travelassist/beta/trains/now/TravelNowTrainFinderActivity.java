package ws.mahesh.travelassist.beta.trains.now;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.bus.finder.BusFinderUtils;
import ws.mahesh.travelassist.beta.location.LocationProvider;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;
import ws.mahesh.travelassist.beta.trains.finder.models.TrainFinderObject;
import ws.mahesh.travelassist.beta.trains.now.adapter.TravelNowTrainFinderAdapter;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class TravelNowTrainFinderActivity extends ActionBarActivity implements LocationProvider.LocationCallback {


    private ProgressDialog progress;
    private AutoCompleteTextView ACTVSrc, ACTVDest;
    private Button FindBus;
    private ImageButton ImgSrc, ImgDest;

    private RadioButton LeaveBy, ReachBy;
    private EditText TravelLaterTimeHR, TravelLaterTimeMIN;
    private RadioGroup TravelTime;
    private int nowTime = 0;
    private String TripType = "";

    private RecyclerView recyclerView;
    private String SourceStop, DestinationStop;
    private int SourceStopId, DestinationStopId;
    private TrainsDatabaseHelper dbHelper;
    private ArrayAdapter stopsAdapter;
    private ArrayList<String> StopNameList = new ArrayList<>();

    private LocationProvider mLocationProvider;

    private TravelNowTrainFinderAdapter adapter;
    private ArrayList<TrainFinderObject> trainResultList = new ArrayList<>();
    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_now_train_finder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Travel Now Finder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocationProvider = new LocationProvider(this, this);

        recyclerView = (RecyclerView) findViewById(R.id.route_finder_recyclerview);
        ACTVSrc = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewSRC);
        ACTVDest = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewDEST);
        FindBus = (Button) findViewById(R.id.buttonFindTrains);
        ImgSrc = (ImageButton) findViewById(R.id.imageButtonSrc);
        ImgDest = (ImageButton) findViewById(R.id.imageButtonDest);

        LeaveBy = (RadioButton) findViewById(R.id.radioButtonNow);
        ReachBy = (RadioButton) findViewById(R.id.radioButtonLater);
        TravelLaterTimeHR = (EditText) findViewById(R.id.editTextTimeHR);
        TravelLaterTimeMIN = (EditText) findViewById(R.id.editTextTimeMIN);
        TravelTime = (RadioGroup) findViewById(R.id.radioGroupTime);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        setup();
        stopsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, StopNameList);

        ACTVSrc.setAdapter(stopsAdapter);
        ACTVDest.setAdapter(stopsAdapter);
        if (nowTime == 0) {
            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            nowTime = (hours * 60) + minutes;
            TravelLaterTimeHR.setText(String.format("%02d", hours));
            TravelLaterTimeMIN.setText(String.format("%02d", minutes));
        }

        if (trainResultList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        }

        progress = new ProgressDialog(this);

        FindBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                SourceStop = ACTVSrc.getText().toString();
                DestinationStop = ACTVDest.getText().toString();
                SourceStopId = getStopID(SourceStop);
                DestinationStopId = getStopID(DestinationStop);
                if (TravelLaterTimeHR.getText().toString().equals("") || TravelLaterTimeMIN.getText().toString().equals(""))
                    Toast.makeText(TravelNowTrainFinderActivity.this, "Invalid time selected", Toast.LENGTH_LONG).show();
                else {
                    int hours = Integer.parseInt(TravelLaterTimeHR.getText().toString());
                    int mins = Integer.parseInt(TravelLaterTimeMIN.getText().toString());
                    if ((ReachBy.isChecked() && hours < 25 && hours > -1 && mins > -1 && mins < 61) || LeaveBy.isChecked()) {
                        if (SourceStopId > 0 && DestinationStopId > 0 && (SourceStopId != DestinationStopId)) {
                            progress.setTitle("Please Wait");
                            progress.setMessage("Finding best route for you.");
                            progress.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    findRoutes();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            setViews();
                                            progress.dismiss();
                                        }
                                    });

                                }
                            }).start();
                            nowTime = (hours * 60) + mins;
                        }
                        if (LeaveBy.isChecked()) {
                            TripType = "Leave";
                        } else if (ReachBy.isChecked()) {
                            TripType = "Reach";
                        }
                    } else
                        Toast.makeText(TravelNowTrainFinderActivity.this, "Invalid time selected", Toast.LENGTH_LONG).show();
                }
            }
        });
        ImgSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.connect();
                ACTVSrc.setText(TrainStaticHolder.getNearByStation());
            }
        });
        ImgDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.connect();
                ACTVDest.setText(TrainStaticHolder.getNearByStation());
            }
        });


    }

    private void clearAll() {
        trainResultList.clear();
    }

    private void findRoutes() {
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor c = dbHelper.getRoute(SourceStopId, DestinationStopId);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String nodes = c.getString(3);
                    String times = c.getString(6);
                    String lines = c.getString(5);
                    if (nodes.equals("0")) {
                        if (c.getString(5).equals("") || c.getString(5) == null) {
                            trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), SourceStopId, DestinationStopId, TrainStaticHolder.getLineIdFromStation(SourceStopId, DestinationStopId), "Direct Train", c.getString(6), getLineCode(TrainStaticHolder.getLineIdFromStation(SourceStopId, DestinationStopId)), getLine(TrainStaticHolder.getLineIdFromStation(SourceStopId, DestinationStopId))));
                        } else
                            trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), SourceStopId, DestinationStopId, TrainStaticHolder.getLineIdFromStation(SourceStopId, DestinationStopId), "Direct Train", c.getString(6), getLineCode(Integer.parseInt(c.getString(5))), getLine(c.getInt(5))));
                        break;
                    } else if (nodes.contains("|")) {
                        String route = "";
                        String line = "";
                        String linecode = "";
                        int time = 0;
                        for (String retval : nodes.split("\\|")) {
                            route = route + " - " + getStopName(Integer.parseInt(retval));
                        }
                        for (String retval : times.split("\\|")) {
                            time = time + Integer.parseInt(retval);
                        }
                        if (lines != null) {
                            for (String retval : lines.split("\\|")) {
                                if (!retval.equals("")) {
                                    linecode = linecode + " - " + getLineCode(Integer.parseInt(retval));
                                    line = line + " - " + getLine(Integer.parseInt(retval));
                                }
                            }
                        }
                        trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), SourceStopId, DestinationStopId, "VIA " + route, "" + time, "VIA " + linecode, "VIA " + line));
                    } else if (times.contains("|")) {
                        int time = 0;
                        String line = "", linecode = "";
                        for (String retval : times.split("\\|")) {
                            time = time + Integer.parseInt(retval);
                        }
                        if (lines != null) {
                            for (String retval : lines.split("\\|")) {
                                if (!retval.equals("")) {
                                    linecode = linecode + " - " + getLineCode(Integer.parseInt(retval));
                                    line = line + " - " + getLine(Integer.parseInt(retval));
                                }
                            }
                        }
                        trainResultList.add(new TrainFinderObject(c.getInt(0), c.getInt(4), SourceStopId, DestinationStopId, "VIA - " + getStopName(Integer.parseInt(nodes)), "" + time, "VIA " + linecode, "VIA " + line));
                    }
                } while (c.moveToNext());
            }
        }

        c.close();
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

    private void setViews() {
        recyclerView.setVisibility(View.GONE);
        if (trainResultList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TravelNowTrainFinderAdapter(this, trainResultList, nowTime, TripType);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
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
        if (TrainStaticHolder.stationsList.isEmpty()) {
            dbHelper = new TrainsDatabaseHelper(this);
            Cursor mCursor = dbHelper.getAllStations();
            dbHelper.close();
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
            dbHelper = new TrainsDatabaseHelper(this);
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
        }
        stationsList = TrainStaticHolder.stationsList;
        linesList = TrainStaticHolder.linesList;
        StopNameList = TrainStaticHolder.stationNameList;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel_now_train_finder, menu);
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
    public void handleNewLocation(Location location) {
        StaticHolder.currentLocation = location;
        mLocationProvider.disconnect();
    }
}
