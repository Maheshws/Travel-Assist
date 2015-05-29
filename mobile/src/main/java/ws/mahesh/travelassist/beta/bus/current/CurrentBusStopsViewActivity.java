package ws.mahesh.travelassist.beta.bus.current;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.StaticBusHolder;
import ws.mahesh.travelassist.beta.bus.current.adapter.CurrentStopsAdapter;
import ws.mahesh.travelassist.beta.bus.current.models.CurrentStopObject;
import ws.mahesh.travelassist.beta.bus.current.models.StopTimeObject;
import ws.mahesh.travelassist.beta.bus.current.models.TripsObject;
import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.bus.finder.BusFinderUtils;
import ws.mahesh.travelassist.beta.bus.finder.FinderRouteMapActivity;
import ws.mahesh.travelassist.beta.bus.finder.models.BusETAObject;
import ws.mahesh.travelassist.beta.bus.finder.models.MapsObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;


public class CurrentBusStopsViewActivity extends ActionBarActivity {
    private int UID;
    private int Route;
    private String ModeType;
    private ArrayList<CurrentStopObject> stops = new ArrayList<>();
    private RecyclerView recyclerView;
    private CurrentStopsAdapter adapter;
    private TripsObject object;
    private ArrayList<BusETAObject> etalist = new ArrayList<>();
    private ArrayList<MapsObject> mapsObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stops_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UID = extras.getInt("id");
            Route = extras.getInt("route");
            ModeType = extras.getString("mode");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Journey " + UID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setUp();
        recyclerView = (RecyclerView) findViewById(R.id.stops_recyclerview);
        adapter = new CurrentStopsAdapter(this, stops);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        setUp2();

    }

    private void setUp2() {
        object = StaticBusCurrentHolder.current;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ssa");
        Date time;
        ArrayList<StopTimeObject> temp = object.getStoptime();
        for (int i = 0; i < stops.size(); i++) {
            for (int j = 0; j < temp.size(); j++) {
                if (stops.get(i).getStopID() == temp.get(j).getStopId()) {
                    stops.get(i).setVisited(true);
                    time = new Date(temp.get(j).getTimestamp());
                    stops.get(i).setReachedAt(sdf.format(time));
                }
            }
        }
        updateStopsDistance(temp.size() - 1);
        int src = temp.get(temp.size() - 1).getStopId();
        for (int i = 0; i < stops.size(); i++) {
            if (!stops.get(i).isVisited()) {
                etalist.add(new BusETAObject(Route, src, stops.get(i).getStopID(), ModeType));
            }
        }
        /*
        Random r = new Random();
        final Double randomDelay = (15 + (20 - 15) * r.nextDouble())/10;
        new Thread(new Runnable() {
            @Override
            public void run() {
                BusETAFinder finder=new BusETAFinder();
                etalist=reParse(finder.getBusETA(BusFinderUtils.getETAJsonList(etalist)));
                for(int i=0;i<stops.size();i++){
                    if(!stops.get(i).isVisited()){
                        for(int j=0;j<etalist.size();j++) {
                            if (stops.get(i).getStopID() == etalist.get(j).getDestId()){
                                stops.get(i).setETA("" + ((int)Math.ceil(Integer.parseInt(etalist.get(j).getEta())*randomDelay / 60)));
                            }
                        }
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
*/

    }

    public ArrayList<BusETAObject> reParse(String res) {
        ArrayList<BusETAObject> resultETA = new ArrayList<>();
        try {
            JSONObject objectRes = new JSONObject(res);
            JSONArray resArray = objectRes.getJSONArray("request");
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject object1 = (JSONObject) resArray.get(i);
                resultETA.add(new BusETAObject(object1.getInt("bus_id"), object1.getInt("src"), object1.getInt("dest"), object1.getString("dir"), object1.getString("eta")));
            }
            Log.d("ETA", resultETA.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultETA;
    }


    private void updateStopsDistance(int i) {
        DecimalFormat df = new DecimalFormat("#.##");
        Location location = new Location("");
        location.setLongitude(stops.get(i).getLng());
        location.setLatitude(stops.get(i).getLat());
        double dist = 0.0;
        int eta;
        int stopcount = 0;
        for (int j = i; j < stops.size(); j++) {
            Location temp = new Location("");
            temp.setLatitude(stops.get(j).getLat());
            temp.setLongitude(stops.get(j).getLng());
            dist = Double.valueOf(df.format(dist + location.distanceTo(temp)));
            eta = (int) ((dist / 1000) * 2) + stopcount;
            stops.get(j).setDistance(df.format(dist / 1000) + " kms");
            stops.get(j).setETA("Reaching in " + eta + " mins");
            location.setLongitude(temp.getLongitude());
            location.setLatitude(temp.getLatitude());
            stopcount++;
        }
        adapter.notifyDataSetChanged();
        makeMapObjects();
    }

    private void makeMapObjects() {
        for (int i = 0; i < stops.size(); i++) {
            if (stops.get(i).isVisited())
                mapsObjects.add(new MapsObject(1, i, StaticBusHolder.getBusName(Route), stops.get(i).getStopName(), BusFinderUtils.makeLocation(stops.get(i).getLat(), stops.get(i).getLng())));
            else
                mapsObjects.add(new MapsObject(2, i, StaticBusHolder.getBusName(Route), stops.get(i).getStopName(), BusFinderUtils.makeLocation(stops.get(i).getLat(), stops.get(i).getLng())));
        }
    }

    private void setUp() {
        BusDatabaseHelper db = new BusDatabaseHelper(this);
        Cursor c = db.getStopsForBus(Route, ModeType);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    stops.add(new CurrentStopObject(c.getInt(0), c.getInt(1), c.getString(2), c.getDouble(3), c.getDouble(4), false, " ", " ", " "));
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_route_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.open_map) {
            Intent i = new Intent(this, FinderRouteMapActivity.class);
            i.putParcelableArrayListExtra("map_object", mapsObjects);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }

}

