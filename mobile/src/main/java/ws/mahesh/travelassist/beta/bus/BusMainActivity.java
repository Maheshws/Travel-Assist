package ws.mahesh.travelassist.beta.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.buswise.BusWiseActivity;
import ws.mahesh.travelassist.beta.bus.current.CurrentBusMainActivity;
import ws.mahesh.travelassist.beta.bus.finder.BusRouteFinderActivity;
import ws.mahesh.travelassist.beta.others.FeedBackActivity;


public class BusMainActivity extends ActionBarActivity {
    private LinearLayout RouteFinderLayout;
    private ImageView RouteFinderImage;
    private TextView RouteFinderText;
    private LinearLayout BusWiseLayout;
    private ImageView BusWiseImage;
    private TextView BusWiseText;
    private LinearLayout BusCurrentLayout;
    private ImageView BusCurrentImage;
    private TextView BusCurrentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bus");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RouteFinderLayout = (LinearLayout) findViewById(R.id.bus_route_finder);
        RouteFinderImage = (ImageView) findViewById(R.id.imageViewBusRouteFinder);
        RouteFinderText = (TextView) findViewById(R.id.textViewBusRouteFinder);

        RouteFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusMainActivity.this, BusRouteFinderActivity.class));
            }
        });

        BusWiseLayout = (LinearLayout) findViewById(R.id.bus_wise_activity);
        BusWiseImage = (ImageView) findViewById(R.id.imageViewBusWise);
        BusWiseText = (TextView) findViewById(R.id.textViewBusWise);

        BusWiseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusMainActivity.this, BusWiseActivity.class));
            }
        });

        BusCurrentLayout = (LinearLayout) findViewById(R.id.bus_current_activity);
        BusCurrentImage = (ImageView) findViewById(R.id.imageViewCurrent);
        BusCurrentText = (TextView) findViewById(R.id.textViewCurrent);

        BusCurrentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BusMainActivity.this, CurrentBusMainActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bus_main, menu);
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
            startActivity(new Intent(BusMainActivity.this, FeedBackActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
