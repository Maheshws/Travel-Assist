package ws.mahesh.travelassist.beta.cabs.customcabs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.database.CabsDatabaseHelper;
import ws.mahesh.travelassist.beta.cabs.models.CabsObject;

public class AddCustomCabActivity extends ActionBarActivity {
    private Button SaveCab;
    private EditText Name, City, BaseDistance, BaseFare, BaseWaitTime, DistanceUnit, FarePerDistance, WaitTimeUnit, FarePerWaitTime, NightFare;
    private CabsDatabaseHelper dbHelper;
    private ArrayList<CabsObject> cabsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_cab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Custom Cab");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Name = (EditText) findViewById(R.id.editTextName);
        City = (EditText) findViewById(R.id.editTextCity);
        BaseDistance = (EditText) findViewById(R.id.editTextBaseDistance);
        BaseFare = (EditText) findViewById(R.id.editTextBaseAmount);
        BaseWaitTime = (EditText) findViewById(R.id.editTextBaseWT);
        DistanceUnit = (EditText) findViewById(R.id.editTextDistanceUnit);
        FarePerDistance = (EditText) findViewById(R.id.editTextFarePerDistance);
        WaitTimeUnit = (EditText) findViewById(R.id.editTextWaitTimeUnit);
        FarePerWaitTime = (EditText) findViewById(R.id.editTextFarePerWT);
        NightFare = (EditText) findViewById(R.id.editTextNightFare);

        SaveCab = (Button) findViewById(R.id.buttonSubmit);

        SaveCab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new CabsDatabaseHelper(AddCustomCabActivity.this);
                SaveCabInfo();
            }
        });
    }

    private void SaveCabInfo() {
        if (validateInfo()) {
            cabsList.clear();
            final CabsObject object = new CabsObject();
            object.setName(Name.getText().toString());
            object.setCity(City.getText().toString());
            object.setBaseDistance(Double.parseDouble(BaseDistance.getText().toString()));
            object.setBaseFare(Double.parseDouble(BaseFare.getText().toString()));
            object.setBaseWaitTime(Double.parseDouble(BaseWaitTime.getText().toString()));
            object.setDistancePerUnit(Double.parseDouble(DistanceUnit.getText().toString()));
            object.setFarePerUnit(Double.parseDouble(FarePerDistance.getText().toString()));
            object.setWaitTimePerUnit(Double.parseDouble(WaitTimeUnit.getText().toString()));
            object.setFarePerWaitTime(Double.parseDouble(FarePerWaitTime.getText().toString()));
            object.setNightMultiplier(Double.parseDouble(NightFare.getText().toString()));
            object.setCustomCab(1);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    dbHelper.addCustomCab(object);
                    updateCurrentInfo();
                    dbHelper.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            clearAll();
                            Toast.makeText(AddCustomCabActivity.this, "Info Saved!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(this, "Please check your input", Toast.LENGTH_LONG).show();
        }
    }

    private void clearAll() {
        Name.setText("");
        City.setText("");
        BaseDistance.setText("");
        BaseFare.setText("");
        BaseWaitTime.setText("");
        DistanceUnit.setText("");
        FarePerDistance.setText("");
        WaitTimeUnit.setText("");
        FarePerWaitTime.setText("");
        NightFare.setText("");
    }

    private void updateCurrentInfo() {
        Cursor mCursor = dbHelper.getAllCabsFare();
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    cabsList.add(new CabsObject(mCursor.getInt(0), mCursor.getString(1), mCursor.getString(2), mCursor.getDouble(3), mCursor.getDouble(4), mCursor.getDouble(5), mCursor.getDouble(6), mCursor.getDouble(7), mCursor.getDouble(8), mCursor.getDouble(9), mCursor.getDouble(10), mCursor.getInt(11)));
                } while (mCursor.moveToNext());
            }
        }
        mCursor.close();
        StaticCabsHolder.cabsList = cabsList;
    }

    private boolean validateInfo() {
        if (Name.getText().toString().equals(""))
            return false;
        else if (City.getText().toString().equals(""))
            return false;
        else if (BaseDistance.getText().toString().equals(""))
            return false;
        else if (BaseFare.getText().toString().equals(""))
            return false;
        else if (BaseWaitTime.getText().toString().equals(""))
            return false;
        else if (DistanceUnit.getText().toString().equals(""))
            return false;
        else if (FarePerDistance.getText().toString().equals(""))
            return false;
        else if (WaitTimeUnit.getText().toString().equals(""))
            return false;
        else if (FarePerWaitTime.getText().toString().equals(""))
            return false;
        else if (NightFare.getText().toString().equals(""))
            return false;

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_custom_cab, menu);
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
