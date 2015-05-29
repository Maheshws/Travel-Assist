package ws.mahesh.travelassist.beta.cabs;

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
import ws.mahesh.travelassist.beta.cabs.farecalculator.CabsFareCalculatorActivity;
import ws.mahesh.travelassist.beta.cabs.now.CabsTravelNowMainActivity;

public class CabsMainActivity extends ActionBarActivity {
    private LinearLayout TravelNowLayout;
    private ImageView TravelNowImage;
    private TextView TravelNowText;
    private LinearLayout FareCalculatorLayout;
    private ImageView FareCalculatorImage;
    private TextView FareCalculatorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cabs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TravelNowLayout = (LinearLayout) findViewById(R.id.cabs_travel_now_selection);
        TravelNowImage = (ImageView) findViewById(R.id.imageViewCabsTravelNow);
        TravelNowText = (TextView) findViewById(R.id.textViewCabsTravelNow);

        TravelNowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CabsMainActivity.this, CabsTravelNowMainActivity.class));
            }
        });

        FareCalculatorLayout = (LinearLayout) findViewById(R.id.fare_calculator_selection);
        FareCalculatorImage = (ImageView) findViewById(R.id.imageViewFareCalculator);
        FareCalculatorText = (TextView) findViewById(R.id.textViewFareCalculator);

        FareCalculatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CabsMainActivity.this, CabsFareCalculatorActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabs_main, menu);
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
