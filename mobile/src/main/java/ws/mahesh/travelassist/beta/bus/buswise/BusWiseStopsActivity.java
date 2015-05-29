package ws.mahesh.travelassist.beta.bus.buswise;

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
import ws.mahesh.travelassist.beta.bus.finder.BusFinderUtils;
import ws.mahesh.travelassist.beta.bus.finder.FinderRouteMapActivity;
import ws.mahesh.travelassist.beta.bus.finder.models.MapsObject;

public class BusWiseStopsActivity extends ActionBarActivity {
    private int BusId;
    private String BusName;
    private String BusType;
    private LinearLayout mainLayout;
    private ArrayList<MapsObject> mapsObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route_view);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus Route");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            BusId = extras.getInt("busId");
            BusName = extras.getString("busName");
        }
        BusType = "UP";
        setUp();
        BusType = "DOWN";
        setUp();

    }

    private void setUp() {
        BusDatabaseHelper dbHelper = new BusDatabaseHelper(this);
        Cursor c = dbHelper.getStopsForBus(BusId, BusType);
        View parent = getLayoutInflater().inflate(R.layout.item_bus_row, null);
        TextView Bus = (TextView) parent.findViewById(R.id.textViewBus);
        TextView Type = (TextView) parent.findViewById(R.id.textViewType);
        Bus.setText(BusName);
        Type.setText(BusType);
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
                    No.setText(c.getString(0));
                    Seq.setVisibility(View.GONE);
                    parentLayout.addView(child);
                    if (BusType.equals("UP"))
                        mapsObjects.add(new MapsObject(1, c.getInt(0), BusName, c.getString(2), BusFinderUtils.makeLocation(c.getDouble(3), c.getDouble(4))));
                    else
                        mapsObjects.add(new MapsObject(3, c.getInt(0), BusName, c.getString(2), BusFinderUtils.makeLocation(c.getDouble(3), c.getDouble(4))));
                } while (c.moveToNext());
            }
        }
        c.close();
        dbHelper.close();
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
