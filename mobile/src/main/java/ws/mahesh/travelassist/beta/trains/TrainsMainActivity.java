package ws.mahesh.travelassist.beta.trains;

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
import ws.mahesh.travelassist.beta.others.FeedBackActivity;
import ws.mahesh.travelassist.beta.trains.finder.TrainsFinderActivity;
import ws.mahesh.travelassist.beta.trains.now.TravelNowTrainFinderActivity;

public class TrainsMainActivity extends ActionBarActivity {
    private LinearLayout RouteFinderLayout;
    private ImageView RouteFinderImage;
    private TextView RouteFinderText;
    private LinearLayout TravelNowLayout;
    private ImageView TravelNowImageView;
    private TextView TravelNowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trains");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RouteFinderLayout = (LinearLayout) findViewById(R.id.train_finder_activity);
        RouteFinderImage = (ImageView) findViewById(R.id.imageViewTrainsRouteFinder);
        RouteFinderText = (TextView) findViewById(R.id.textViewTrainsRouteFinder);

        RouteFinderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainsMainActivity.this, TrainsFinderActivity.class));

            }
        });
        TravelNowLayout = (LinearLayout) findViewById(R.id.travel_now_finder);
        TravelNowImageView = (ImageView) findViewById(R.id.imageViewTravelNow);
        TravelNowText = (TextView) findViewById(R.id.textViewTravelNow);

        TravelNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainsMainActivity.this, TravelNowTrainFinderActivity.class));

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trains_main, menu);
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
            startActivity(new Intent(TrainsMainActivity.this, FeedBackActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
