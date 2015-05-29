package ws.mahesh.travelassist.beta.cabs.customcabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.customcabs.adapter.CustomCabsListAdapter;
import ws.mahesh.travelassist.beta.cabs.models.CabsObject;
import ws.mahesh.travelassist.beta.utils.DividerItemDecoration;

public class CustomCabsListActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private ArrayList<CabsObject> cabsList = new ArrayList<>();
    private CustomCabsListAdapter adapter;
    private ImageButton fabImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cabs_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Custom Cabs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabImageButton = (ImageButton) findViewById(R.id.fab_image_button);

        recyclerView = (RecyclerView) findViewById(R.id.cabs_list_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        cabsList = StaticCabsHolder.getCabsCustomCabsList();
        adapter = new CustomCabsListAdapter(this, cabsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomCabsListActivity.this, AddCustomCabActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cabsList = StaticCabsHolder.getCabsCustomCabsList();
        adapter = new CustomCabsListAdapter(this, cabsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_cabs_list, menu);
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
