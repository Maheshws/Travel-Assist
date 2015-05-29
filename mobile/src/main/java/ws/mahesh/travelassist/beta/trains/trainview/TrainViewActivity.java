package ws.mahesh.travelassist.beta.trains.trainview;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.trains.TrainStaticHolder;
import ws.mahesh.travelassist.beta.trains.database.TrainsDatabaseHelper;
import ws.mahesh.travelassist.beta.trains.finder.models.LineObject;
import ws.mahesh.travelassist.beta.trains.finder.models.StationObject;
import ws.mahesh.travelassist.beta.trains.trainview.adapter.TrainViewAdapter;
import ws.mahesh.travelassist.beta.trains.trainview.model.TrainViewObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class TrainViewActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private int TrainID, LineID, SrcID, DestID, ScrollSrc, ScrollDest;
    private TrainsDatabaseHelper dbHelper;
    private String RouteInfo;

    private TextView Route, Speed, Cars;

    private ProgressDialog progress;

    private ArrayList<StationObject> stationsList = new ArrayList<>();
    private ArrayList<LineObject> linesList = new ArrayList<>();
    private ArrayList<TrainViewObject> trains = new ArrayList<>();
    private TrainViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TrainID = extras.getInt("id");
            LineID = extras.getInt("line");
            RouteInfo = extras.getString("route");
            SrcID = extras.getInt("src");
            DestID = extras.getInt("dest");
        }
        getSupportActionBar().setTitle(RouteInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        progress = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.route_view_recyclerview);
        Route = (TextView) findViewById(R.id.textViewRoute);
        Speed = (TextView) findViewById(R.id.textViewSpeed);
        Cars = (TextView) findViewById(R.id.textViewCars);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setVisibility(View.GONE);
        progress.setTitle("Please Wait");
        progress.setMessage("Finding best trains for you.");
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
        int i = 0;
        dbHelper = new TrainsDatabaseHelper(this);
        Cursor c = dbHelper.getTrainDetails(TrainStaticHolder.getTableFromLine(LineID), TrainID);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if ((c.getInt(6) - c.getInt(5) < 360)) {
                        trains.add(new TrainViewObject(c.getInt(0), c.getInt(2), c.getInt(4), c.getInt(3), c.getInt(7), c.getInt(8), c.getInt(11), c.getInt(10), c.getInt(12), c.getString(6), c.getString(5), c.getString(9), c.getString(13), c.getString(14)));
                        if (c.getInt(3) == SrcID)
                            ScrollSrc = i;
                        if (c.getInt(3) == DestID)
                            ScrollDest = i;
                        i++;
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
                Route.setText(RouteInfo);
                if (trains.get(0).getSpeed().equals("S"))
                    Speed.setText("SLOW");
                else if (trains.get(0).getSpeed().equals("F"))
                    Speed.setText("FAST");
                if (trains.get(0).getCars() > 0)
                    Cars.setText("CARS: " + trains.get(0).getCars());
                progress.dismiss();
            }
        });
    }

    private void setViews() {
        recyclerView.setVisibility(View.GONE);
        if (trains.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new TrainViewAdapter(this, trains, ScrollSrc, ScrollDest);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.scrollToPosition(ScrollSrc - 1);
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
        getMenuInflater().inflate(R.menu.menu_train_view, menu);
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
            startActivity(new Intent(TrainViewActivity.this, FeedBackActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
