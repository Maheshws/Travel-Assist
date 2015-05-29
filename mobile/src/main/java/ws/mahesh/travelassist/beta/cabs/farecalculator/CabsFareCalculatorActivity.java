package ws.mahesh.travelassist.beta.cabs.farecalculator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.farecalculator.adapter.CabsFareCalculatorAdapter;
import ws.mahesh.travelassist.beta.cabs.farecalculator.base.FareCalculator;

public class CabsFareCalculatorActivity extends ActionBarActivity {
    private EditText ETDistance, ETWait;
    private Button CalFare;
    private ProgressDialog progress;

    private RecyclerView recyclerView;
    private ArrayList<FareCalculator> results = new ArrayList<>();
    private CabsFareCalculatorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabs_fare_calculator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fare Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ETDistance = (EditText) findViewById(R.id.editTextDistance);
        ETWait = (EditText) findViewById(R.id.editTextWaitTime);
        CalFare = (Button) findViewById(R.id.buttonCalculate);
        recyclerView = (RecyclerView) findViewById(R.id.cabs_fare_recyclerview);
        recyclerView.setVisibility(View.GONE);

        CalFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(ETDistance.getText().toString().equals("") || ETWait.getText().toString().equals(""))) {
                    CalculateFare();
                }
            }
        });
    }

    private void CalculateFare() {
        results.clear();
        double dist = Double.parseDouble(ETDistance.getText().toString());
        double wt = Double.parseDouble(ETWait.getText().toString());
        for (int i = 0; i < StaticCabsHolder.getCabsList().size(); i++) {
            results.add(new FareCalculator(StaticCabsHolder.getCabsList().get(i), dist, wt));
        }
        adapter = new CabsFareCalculatorAdapter(this, results);

        recyclerView.setVisibility(View.GONE);
        if (results.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new CabsFareCalculatorAdapter(this, results);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cabs_fare_calculator, menu);
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
