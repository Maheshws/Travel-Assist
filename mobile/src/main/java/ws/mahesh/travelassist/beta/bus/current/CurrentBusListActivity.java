package ws.mahesh.travelassist.beta.bus.current;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.StaticBusHolder;
import ws.mahesh.travelassist.beta.bus.current.adapter.CurrentBusActiveListAdapter;
import ws.mahesh.travelassist.beta.bus.current.helpers.CurrentBusFinder;
import ws.mahesh.travelassist.beta.bus.current.models.TripsObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class CurrentBusListActivity extends ActionBarActivity {
    private ArrayList<TripsObject> list = new ArrayList<>();
    private CurrentBusActiveListAdapter adapter;
    private RecyclerView recyclerView;

    private ProgressDialog progress;

    private int RouteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bus_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RouteID = extras.getInt("id");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Current Buses : " + StaticBusHolder.getBusName(RouteID));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.bus_wise_recyclerview);
        recyclerView.setVisibility(View.GONE);
        progress = new ProgressDialog(this);
        progress.setTitle("Please Wait");
        progress.setMessage("Finding running buses " + StaticBusHolder.getBusName(RouteID));
        progress.setCancelable(false);
        progress.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setUp();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });
            }
        }).start();

    }

    private void setUp() {
        CurrentBusFinder finder = new CurrentBusFinder();
        String result = finder.getCurrentBus(RouteID);
        Log.d("Response", result);
        list = CurrentBusFinder.parseResponse(result.replaceAll("u'", "\\'"));
        //Log.e("Parsed",list.toString());
        if (list.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new CurrentBusActiveListAdapter(CurrentBusListActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(CurrentBusListActivity.this));
                    recyclerView.addItemDecoration(new DividerItemDecoration(CurrentBusListActivity.this, null));
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_bus_list, menu);
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
}
