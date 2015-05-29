package ws.mahesh.travelassist.beta.bus.current;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.buswise.models.BusWiseObject;
import ws.mahesh.travelassist.beta.bus.current.adapter.CurrentBusMainListAdapter;
import ws.mahesh.travelassist.beta.bus.database.BusDatabaseHelper;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class CurrentBusMainActivity extends ActionBarActivity {
    private ArrayList<BusWiseObject> list = new ArrayList<>();
    private CurrentBusMainListAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.bus_wise_recyclerview);

        setUp();
        adapter = new CurrentBusMainListAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));


    }

    private void setUp() {
        BusDatabaseHelper dbHelper = new BusDatabaseHelper(this);
        Cursor c = dbHelper.getAllBusInfo();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    list.add(new BusWiseObject(c.getInt(0), c.getString(1), c.getString(2)));
                } while (c.moveToNext());
            }
        }
        c.close();
        dbHelper.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_wise, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                adapter.getFilter().filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

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
