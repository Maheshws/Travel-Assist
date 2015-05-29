package ws.mahesh.travelassist.beta.bus.finder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus1Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus2Object;
import ws.mahesh.travelassist.beta.bus.finder.models.Bus3Object;
import ws.mahesh.travelassist.beta.bus.finder.models.MapsObject;

public class BusRouteViewActivity extends ActionBarActivity {
    private int data_size = 0;
    private Bus1Object bus1Object;
    private Bus2Object bus2Object;
    private Bus3Object bus3Object;
    private BusDatabaseHelper dbHelper;
    private LinearLayout mainLayout;
    private int stop_count = 1;
    private int bus_count = 1;
    private ArrayList<MapsObject> mapsObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_view);
        Bundle extras = getIntent().getExtras();
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus Route");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (extras != null) {
            dbHelper = new BusDatabaseHelper(this);
            data_size = extras.getInt("type");
            if (data_size == 1) {
                bus1Object = (Bus1Object) getIntent().getSerializableExtra("object");
                setUp1();
            } else if (data_size == 2) {
                bus2Object = (Bus2Object) getIntent().getSerializableExtra("object");
                setUp2();
            } else if (data_size == 3) {
                bus3Object = (Bus3Object) getIntent().getSerializableExtra("object");
                setUp3();
            }
            dbHelper.close();
        }


    }

    private void setUp3() {
        Cursor c = dbHelper.getBusTypeSequence(bus3Object.getBus1no(), bus3Object.getSrcno(), bus3Object.getStop1no());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus1Object = new Bus1Object(bus3Object.getSrcno(), bus3Object.getStop1no(), bus3Object.getBus1no(), bus3Object.getSrcStop(), bus3Object.getStop1(), bus3Object.getBus1(), bus3Object.getEta1(), c.getString(0), c.getInt(1), c.getInt(2));
                } while (c.moveToNext());
            }
            setUp1();
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus3Object.getBus2no(), bus3Object.getStop1no(), bus3Object.getStop2no());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus1Object = new Bus1Object(bus3Object.getStop1no(), bus3Object.getStop2no(), bus3Object.getBus2no(), bus3Object.getStop1(), bus3Object.getStop2(), bus3Object.getBus2(), bus3Object.getEta2(), c.getString(0), c.getInt(1), c.getInt(2));
                } while (c.moveToNext());
            }
            setUp1();
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus3Object.getBus3no(), bus3Object.getStop2no(), bus3Object.getDestno());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus1Object = new Bus1Object(bus3Object.getStop2no(), bus3Object.getDestno(), bus3Object.getBus3no(), bus3Object.getStop2(), bus3Object.getDestStop(), bus3Object.getBus3(), bus3Object.getEta3(), c.getString(0), c.getInt(1), c.getInt(2));
                } while (c.moveToNext());
            }
            setUp1();
        }
        c.close();

    }

    private void setUp2() {
        Cursor c = dbHelper.getBusTypeSequence(bus2Object.getBus1no(), bus2Object.getSrcno(), bus2Object.getStopno());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus1Object = new Bus1Object(bus2Object.getSrcno(), bus2Object.getStopno(), bus2Object.getBus1no(), bus2Object.getSrcStop(), bus2Object.getStop(), bus2Object.getBus1(), bus2Object.getEta1(), c.getString(0), c.getInt(1), c.getInt(2));
                } while (c.moveToNext());
            }
            setUp1();
        }
        c.close();
        c = dbHelper.getBusTypeSequence(bus2Object.getBus2no(), bus2Object.getStopno(), bus2Object.getDestno());
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    bus1Object = new Bus1Object(bus2Object.getStopno(), bus2Object.getDestno(), bus2Object.getBus2no(), bus2Object.getStop(), bus2Object.getDestStop(), bus2Object.getBus2(), bus2Object.getEta2(), c.getString(0), c.getInt(1), c.getInt(2));
                } while (c.moveToNext());
            }
            setUp1();
        }
        c.close();
    }

    private void setUp1() {
        Cursor c = dbHelper.getBusInfoBetween(bus1Object.getBusno(), bus1Object.getSrcSeq(), bus1Object.getDestSeq(), bus1Object.getType());
        View parent = getLayoutInflater().inflate(R.layout.item_bus_row, null);
        TextView Bus = (TextView) parent.findViewById(R.id.textViewBus);
        TextView Type = (TextView) parent.findViewById(R.id.textViewType);
        Bus.setText(bus1Object.getBusName());
        Type.setText(bus1Object.getType());
        mainLayout.addView(parent);
        LinearLayout parentLayout = (LinearLayout) parent.findViewById(R.id.parent_main);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    View child = getLayoutInflater().inflate(R.layout.item_stop_row, null);
                    TextView Stop = (TextView) child.findViewById(R.id.textViewStop1);
                    TextView Seq = (TextView) child.findViewById(R.id.textViewSeq);
                    TextView No = (TextView) child.findViewById(R.id.textViewNo);
                    Stop.setText(c.getString(2));
                    Seq.setText(c.getString(0));
                    No.setText("" + stop_count);
                    parentLayout.addView(child);
                    stop_count++;
                    mapsObjects.add(new MapsObject(bus_count, stop_count, bus1Object.getBusName(), c.getString(2), BusFinderUtils.makeLocation(c.getDouble(3), c.getDouble(4))));
                } while (c.moveToNext());
            }
        }
        c.close();
        bus_count++;
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

}
