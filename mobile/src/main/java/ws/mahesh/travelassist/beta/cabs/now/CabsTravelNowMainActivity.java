package ws.mahesh.travelassist.beta.cabs.now;

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
import ws.mahesh.travelassist.beta.cabs.customcabs.CustomCabsListActivity;

public class CabsTravelNowMainActivity extends ActionBarActivity {

    private LinearLayout TaxiTravelNowLayout;
    private ImageView TaxiTravelNowImage;
    private TextView TaxiTravelNowText;
    private LinearLayout SimpleTravelNowLayout;
    private ImageView SimpleTravelNowImage;
    private TextView SimpleTravelNowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs_travel_now_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Travel Now - Cabs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TaxiTravelNowLayout = (LinearLayout) findViewById(R.id.taxi_travel_now_selection);
        TaxiTravelNowImage = (ImageView) findViewById(R.id.imageViewTaxiTravelNow);
        TaxiTravelNowText = (TextView) findViewById(R.id.textViewTaxiTravelNow);

        TaxiTravelNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CabsTravelNowMainActivity.this, CabsTravelNowActivity.class);
                i.putExtra("id", 2);
                startActivity(i);
            }
        });

        SimpleTravelNowLayout = (LinearLayout) findViewById(R.id.simple_travel_now_selection);
        SimpleTravelNowImage = (ImageView) findViewById(R.id.imageViewSimpleTravelNow);
        SimpleTravelNowText = (TextView) findViewById(R.id.textViewSimpleTravelNow);

        SimpleTravelNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CabsTravelNowMainActivity.this, CustomCabsListActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabs_travel_now_main, menu);
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
