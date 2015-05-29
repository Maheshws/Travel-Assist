package ws.mahesh.travelassist.beta.trains.schedule;

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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;
import ws.mahesh.travelassist.beta.trains.schedule.adapter.TrainScheduleFinderAdapter;
import ws.mahesh.travelassist.beta.trains.schedule.model.TrainScheduleObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class TrainSchedulesActivity extends ActionBarActivity {


    private RecyclerView recyclerView;
    private int SrcID, DestID, LineID, ScrollPosition;
    private TrainsDatabaseHelper dbHelper;
    private String RouteInfo;

    private ProgressDialog progress;

    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();
    private ArrayList<TrainScheduleObject> trains = new ArrayList<>();
    private TrainScheduleFinderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_schedules);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SrcID = extras.getInt("src");
            DestID = extras.getInt("dest");
            LineID = extras.getInt("line");
            RouteInfo = extras.getString("route");
        }
        getSupportActionBar().setTitle(RouteInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        progress = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.route_view_recyclerview);

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
                findTrains();
            }
        }).start();

    }

    private void findTrains() {
        clearAll();
        ScrollPosition = 0;
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int nowMins = (hours * 60) + minutes;
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor c = dbHelper.getTrainsFromTo(TrainStaticHolder.getTableFromLine(LineID), SrcID, DestID);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if ((c.getInt(6) - c.getInt(5) < 360)) {
                        trains.add(new TrainScheduleObject(c.getInt(0), c.getInt(2), c.getInt(5), c.getInt(6), c.getInt(3), c.getInt(4), c.getInt(9), c.getInt(10), c.getInt(13), c.getInt(12), c.getInt(14), c.getString(8), c.getString(7), c.getString(15)));
                        if (c.getInt(5) < nowMins)
                            ScrollPosition++;
                    }
                } while (c.moveToNext());
            }
        }

        c.close();
        dbHelper.close();
        //Log.e("LIST", trains.toString());
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
        if (trains.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TrainScheduleFinderAdapter(this, trains, LineID);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.scrollToPosition(ScrollPosition - 1);
        }
    }

    private void clearAll() {
        trains.clear();
    }

    private void setup() {
        stationsList = TrainStaticHolder.stationsList;
        linesList = TrainStaticHolder.linesList;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_train_schedules, menu);
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
            startActivity(new Intent(TrainSchedulesActivity.this, FeedBackActivity.class));
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
