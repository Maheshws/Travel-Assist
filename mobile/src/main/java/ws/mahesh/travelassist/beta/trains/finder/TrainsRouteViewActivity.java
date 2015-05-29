package ws.mahesh.travelassist.beta.trains.finder;

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

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.adapter.TrainRouteViewAdapter;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;
import ws.mahesh.travelassist.beta.trains.finder.models.TrainFinderObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class TrainsRouteViewActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private int Route_ID;
    private TrainsDatabaseHelper dbHelper;

    private TextView RouteText;

    private ArrayList<String> existList = new ArrayList<>();

    private TrainRouteViewAdapter adapter;
    private ArrayList<TrainFinderObject> trainResultList = new ArrayList<>();
    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();

    private boolean showAll;

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
            showAll = extras.getBoolean("showAll");
        }

        recyclerView = (RecyclerView) findViewById(R.id.route_view_recyclerview);
        RouteText = (TextView) findViewById(R.id.textViewRoute);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setVisibility(View.GONE);
        setup();
        findRoutes();
    }

    private void clearAll() {
        trainResultList.clear();
    }

    private void findRoutes() {
        clearAll();
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor c = dbHelper.getRouteFromID(Route_ID);
        if (c != null) {
            if (c.moveToFirst()) {
                RouteText.setText(getStopName(c.getInt(1)) + " - " + getStopName(c.getInt(2)));
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
        dbHelper.close();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //show data
                setViews();
            }
        });
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
            adapter = new TrainRouteViewAdapter(this, trainResultList);
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
        stationsList = TrainStaticHolder.stationsList;
        linesList = TrainStaticHolder.linesList;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trains_route_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback) {
            startActivity(new Intent(TrainsRouteViewActivity.this, FeedBackActivity.class));
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
