package ws.mahesh.travelassist.beta.bus.finder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.StaticHolder;
import ws.mahesh.travelassist.beta.bus.StaticBusHolder;
import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.bus.finder.adapters.Bus1ListAdapter;
import ws.mahesh.travelassist.beta.bus.finder.adapters.Bus2ListAdapter;
import ws.mahesh.travelassist.beta.bus.finder.adapters.Bus3ListAdapter;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus1Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus2Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus3Object;
import ws.mahesh.travelassist.beta.bus.finder.models.BusETAObject;
import ws.mahesh.travelassist.beta.bus.finder.models.BusObject;
import ws.mahesh.travelassist.beta.bus.finder.models.StopObject;
import ws.mahesh.travelassist.beta.location.LocationProvider;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class BusRouteFinderActivity extends ActionBarActivity implements LocationProvider.LocationCallback {

    private final int THRESHOLD_LIMIT = 1;
    double randomDelay;
    private ProgressDialog progress;
    private AutoCompleteTextView ACTVSrc, ACTVDest;
    private Button FindBus;
    private RecyclerView recyclerView;
    private String SourceStop, DestinationStop;
    private int SourceStopId, DestinationStopId;
    private int List_Type = 0;
    private BusDatabaseHelper dbHelper;
    private ArrayAdapter stopsAdapter;
    private Bus1ListAdapter bus1Adapter;
    private Bus2ListAdapter bus2Adapter;
    private Bus3ListAdapter bus3Adapter;
    private LocationProvider mLocationProvider;

    private ImageButton ImgSrc, ImgDest;

    private BusETAFinder etaFinder = new BusETAFinder();

    private ArrayList<BusObject> BusList = new ArrayList<>();
    private ArrayList<Bus1Object> Bus1List = new ArrayList<>();
    private ArrayList<Bus2Object> Bus2List = new ArrayList<>();
    private ArrayList<Bus3Object> Bus3List = new ArrayList<>();
    private ArrayList<StopObject> StopsList = new ArrayList<>();
    private ArrayList<String> StopNameList = new ArrayList<>();
    private ArrayList<BusETAObject> BusETA = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_finder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus Route Finder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLocationProvider = new LocationProvider(this, this);

        recyclerView = (RecyclerView) findViewById(R.id.route_finder_recyclerview);
        ACTVSrc = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewSRC);
        ACTVDest = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewDEST);
        FindBus = (Button) findViewById(R.id.buttonFindBus);
        ImgSrc = (ImageButton) findViewById(R.id.imageButtonSrc);
        ImgDest = (ImageButton) findViewById(R.id.imageButtonDest);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));

        setup();
        stopsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, StopNameList);

        ACTVSrc.setAdapter(stopsAdapter);
        ACTVDest.setAdapter(stopsAdapter);

        if (Bus1List.isEmpty() && Bus2List.isEmpty() && Bus3List.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        }

        progress = new ProgressDialog(this);

        FindBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SourceStop = ACTVSrc.getText().toString();
                DestinationStop = ACTVDest.getText().toString();
                SourceStopId = getStopId(SourceStop);
                DestinationStopId = getStopId(DestinationStop);
                SourceStop = getStopName(SourceStopId);
                DestinationStop = getStopName(DestinationStopId);
                if (SourceStopId > 0 && DestinationStopId > 0 && (SourceStopId != DestinationStopId)) {
                    progress.setTitle("Please Wait");
                    progress.setMessage("Finding best route for you.");
                    progress.show();
                    progress.setCancelable(false);
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
                            BusETA = reParse(etaFinder.getBusETA(BusFinderUtils.getETAJsonList(BusETA)));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    reSetViews();
                                }
                            });
                        }
                    }).start();

                }
            }
        });
        ImgSrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.connect();
                ACTVSrc.setText(StaticBusHolder.getNearByStop());
            }
        });
        ImgDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationProvider.connect();
                ACTVDest.setText(StaticBusHolder.getNearByStop());
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(BusRouteFinderActivity.this, BusRouteViewActivity.class);
                i.putExtra("type", List_Type);
                switch (List_Type) {
                    case 1:
                        i.putExtra("object", Bus1List.get(position));
                        break;
                    case 2:
                        i.putExtra("object", Bus2List.get(position));
                        break;
                    case 3:
                        i.putExtra("object", Bus3List.get(position));
                        break;
                }
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void setViews() {
        //Log.e("BUSETAReq", BusFinderUtils.getETAJsonList(BusETA));
        recyclerView.setVisibility(View.GONE);
        if (Bus1List.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            bus1Adapter = new Bus1ListAdapter(this, Bus1List);
            recyclerView.setAdapter(bus1Adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List_Type = 1;

        } else if (Bus2List.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            bus2Adapter = new Bus2ListAdapter(this, Bus2List);
            recyclerView.setAdapter(bus2Adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List_Type = 2;
        } else if (Bus3List.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            bus3Adapter = new Bus3ListAdapter(this, Bus3List);
            recyclerView.setAdapter(bus3Adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List_Type = 3;
        }
    }

    private void reSetViews() {
        Random r = new Random();
        randomDelay = (15 + (20 - 15) * r.nextDouble()) / 10;
        //Log.e("BUSETAResp", BusFinderUtils.getETAJsonList(reParse(BusFinderUtils.getETAJsonList(BusETA))));
        switch (List_Type) {
            case 1:
                updateEtaBus1();
                break;
            case 2:
                updateEtaBus2();
                break;
            case 3:
                updateEtaBus3();
                break;
        }
    }

    private void updateEtaBus1() {
        for (int i = 0; i < Bus1List.size(); i++) {
            Bus1Object object = Bus1List.get(i);
            for (int j = 0; j < BusETA.size(); j++) {
                if ((object.getBusno() + "-" + object.getSrcStop() + "-" + object.getDestStop()).equals(BusETA.get(j).getId()))
                    if (BusETA.get(j) != null || BusETA.get(j).getEta() != null)
                        Bus1List.get(i).setEta("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
            }

        }
        bus1Adapter.notifyDataSetChanged();
    }

    private void updateEtaBus2() {
        for (int i = 0; i < Bus2List.size(); i++) {
            Bus2Object object = Bus2List.get(i);
            for (int j = 0; j < BusETA.size(); j++) {
                if (BusETA.get(j) != null || BusETA.get(j).getEta() != null) {
                    if ((object.getBus1no() + "-" + object.getSrcno() + "-" + object.getStopno()).equals(BusETA.get(j).getId()))
                        Bus2List.get(i).setEta1("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
                    else if ((object.getBus2no() + "-" + object.getStopno() + "-" + object.getDestno()).equals(BusETA.get(j).getId()))
                        Bus2List.get(i).setEta2("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
                }
            }
        }
        bus2Adapter.notifyDataSetChanged();
    }

    private void updateEtaBus3() {
        for (int i = 0; i < Bus3List.size(); i++) {
            Bus3Object object = Bus3List.get(i);
            for (int j = 0; j < BusETA.size(); j++) {
                if (BusETA.get(j) != null || BusETA.get(j).getEta() != null) {
                    if ((object.getBus1no() + "-" + object.getSrcno() + "-" + object.getStop1no()).equals(BusETA.get(j).getId()))
                        Bus3List.get(i).setEta1("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
                    else if ((object.getBus2no() + "-" + object.getStop1no() + "-" + object.getStop2no()).equals(BusETA.get(j).getId()))
                        Bus3List.get(i).setEta2("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
                    else if ((object.getBus3no() + "-" + object.getStop2no() + "-" + object.getDestno()).equals(BusETA.get(j).getId()))
                        Bus3List.get(i).setEta3("" + ((int) Math.ceil(Integer.parseInt(BusETA.get(j).getEta()) * randomDelay / 60)));
                }
            }

        }
        bus3Adapter.notifyDataSetChanged();
    }

    private void findRoutes() {
        clearAll();
        dbHelper = new BusDatabaseHelper(this);
        int count = 0;
        getBus1();
        count = count + Bus1List.size();
        if (count < THRESHOLD_LIMIT) {
            getBus2();
            count = count + Bus2List.size();
            if (count < THRESHOLD_LIMIT) {
                getBus3();
                count = count + Bus3List.size();
            }
        }
        //Log.e("Count",Bus1List.size()+"\n"+Bus2List.size()+"\n"+Bus3List.size());
        //Log.e("Found",Bus1List.toString()+"\n"+Bus2List.toString()+"\n"+Bus3List.toString());

        dbHelper.close();
    }

    private void clearAll() {
        Bus1List.clear();
        Bus2List.clear();
        Bus3List.clear();
        BusFinderUtils.clearLists();
        BusDatabaseHelper.clearAllLists();
    }

    private void getBus3() {
        int loopCount = 0;
        do {
            Cursor mCursor3 = dbHelper.getBus3A(SourceStopId, DestinationStopId, getStopLocation(DestinationStopId));
            int stop3 = dbHelper.getBusStop3();
            int bus3 = dbHelper.getBusId3();
            if (mCursor3 != null) {
                if (mCursor3.moveToFirst()) {
                    do {
                        Bus3List.add(new Bus3Object(SourceStopId, DestinationStopId, mCursor3.getInt(0), mCursor3.getInt(2), mCursor3.getInt(1), stop3, bus3, SourceStop, getBusName(mCursor3.getInt(0)), getStopName(mCursor3.getInt(2)), getBusName(mCursor3.getInt(1)), getStopName(stop3), getBusName(bus3), DestinationStop, BusFinderUtils.findDistance(getStopLocation(SourceStopId), getStopLocation(mCursor3.getInt(2)), getStopLocation(stop3), getStopLocation(DestinationStopId))));
                    } while (mCursor3.moveToNext());
                }
            }
            mCursor3.close();

            mCursor3 = dbHelper.getBus3B(SourceStopId, DestinationStopId, getStopLocation(DestinationStopId));
            stop3 = dbHelper.getBusStop3();
            bus3 = dbHelper.getBusId3();
            if (mCursor3 != null) {
                if (mCursor3.moveToFirst()) {
                    do {
                        Bus3List.add(new Bus3Object(SourceStopId, DestinationStopId, mCursor3.getInt(0), mCursor3.getInt(2), mCursor3.getInt(1), stop3, bus3, SourceStop, getBusName(mCursor3.getInt(0)), getStopName(mCursor3.getInt(2)), getBusName(mCursor3.getInt(1)), getStopName(stop3), getBusName(bus3), DestinationStop, BusFinderUtils.findDistance(getStopLocation(SourceStopId), getStopLocation(mCursor3.getInt(2)), getStopLocation(stop3), getStopLocation(DestinationStopId))));
                    } while (mCursor3.moveToNext());
                }
            }
            mCursor3.close();

            Collections.sort(Bus3List, new Comparator<Bus3Object>() {
                @Override
                public int compare(Bus3Object lhs, Bus3Object rhs) {
                    return Double.compare(lhs.getDistance(), rhs.getDistance());
                }
            });

            Bus3List = BusFinderUtils.validateRoutesFound3(Bus3List, this);
            BusETA = BusFinderUtils.get3BusETAObject();
            loopCount++;
        } while (Bus3List.size() < 1 && loopCount < 5);

    }

    private void getBus2() {
        Cursor c = dbHelper.getBus2(SourceStopId, DestinationStopId);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Bus2List.add(new Bus2Object(SourceStopId, DestinationStopId, c.getInt(0), c.getInt(2), c.getInt(1), SourceStop, getBusName(c.getInt(0)), getStopName(c.getInt(2)), getBusName(c.getInt(1)), DestinationStop, BusFinderUtils.findDistance(getStopLocation(SourceStopId), getStopLocation(c.getInt(2)), getStopLocation(DestinationStopId))));
                } while (c.moveToNext());
            }
        }
        c.close();

        Bus2List = BusFinderUtils.validateRoutesFound2(Bus2List, this);

        Collections.sort(Bus2List, new Comparator<Bus2Object>() {
            @Override
            public int compare(Bus2Object lhs, Bus2Object rhs) {
                return Double.compare(lhs.getDistance(), rhs.getDistance());
            }
        });
        BusETA = BusFinderUtils.get2BusETAObject();

    }

    private Location getStopLocation(int stop) {
        Location temp = new Location("");
        for (int i = 0; i < StopsList.size(); i++)
            if (StopsList.get(i).getStopid() == stop) {
                temp = StopsList.get(i).getLocation();
            }
        return temp;
    }


    private void getBus1() {
        Cursor c = dbHelper.getDirectBus(SourceStopId, DestinationStopId);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Bus1List.add(new Bus1Object(SourceStopId, DestinationStopId, c.getInt(0), SourceStop, DestinationStop, getBusName(c.getInt(0)), c.getString(1), c.getInt(2), c.getInt(3)));
                } while (c.moveToNext());
            }
        }
        c.close();

        BusETA = BusFinderUtils.get1BusETAObject(Bus1List);
    }

    private int getStopId(String stopname) {
        for (int i = 0; i < StopsList.size(); i++) {
            if ((StopsList.get(i).getStopname() + "-" + StopsList.get(i).getStopid()).equals(stopname))
                return StopsList.get(i).getStopid();
        }
        return 0;
    }

    private String getStopName(int id) {
        for (int i = 0; i < StopsList.size(); i++) {
            if ((StopsList.get(i).getStopid()) == id)
                return StopsList.get(i).getStopname();
        }
        return "";
    }

    private int getBusId(String busname) {
        for (int i = 0; i < BusList.size(); i++) {
            if ((BusList.get(i).getBusno()).equals(busname))
                return BusList.get(i).getBusid();
        }
        return 0;
    }

    private String getBusName(int id) {
        for (int i = 0; i < BusList.size(); i++) {
            if ((BusList.get(i).getBusid()) == id)
                return BusList.get(i).getBusno();
        }
        return "";
    }


    private void setup() {
        dbHelper = new BusDatabaseHelper(this);
        Cursor c = dbHelper.getAllBusStops();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    StopsList.add(new StopObject(c.getInt(0), c.getString(1), BusFinderUtils.makeLocation(c.getDouble(2), c.getDouble(3))));
                    StopNameList.add(c.getString(1) + "-" + c.getInt(0));
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_route_finder, menu);
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
            startActivity(new Intent(BusRouteFinderActivity.this, FeedBackActivity.class));
            return true;
        }
        if (id == R.id.action_screenshot) {
            makeScreenshot();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<BusETAObject> reParse(String res) {
        //Log.e("ETA RESPONSE", res);
        ArrayList<BusETAObject> resultETA = new ArrayList<>();
        try {
            JSONObject objectRes = new JSONObject(res);
            JSONArray resArray = objectRes.getJSONArray("request");
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject object1 = (JSONObject) resArray.get(i);
                resultETA.add(new BusETAObject(object1.getInt("bus_id"), object1.getInt("src"), object1.getInt("dest"), object1.getString("dir"), object1.getString("eta")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultETA;
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

    @Override
    public void handleNewLocation(Location location) {
        StaticHolder.currentLocation = location;
        mLocationProvider.disconnect();
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
