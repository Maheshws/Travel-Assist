package ws.mahesh.travelassist.beta.others;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.utils.googlesheets.GoogleFormUploader;

public class FeedBackActivity extends ActionBarActivity {
    private EditText ETName, ETMessage;
    private Button BTSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ETName = (EditText) findViewById(R.id.editTextName);
        ETMessage = (EditText) findViewById(R.id.editTextMessage);
        BTSubmit = (Button) findViewById(R.id.buttonSubmit);

        BTSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = ETMessage.getText().toString();
                String name = ETName.getText().toString();
                if (name.length() > 4 && message.length() > 5) {
                    GoogleFormUploader uploader = new GoogleFormUploader("1gvQrO9DKLOOMzS7KUSwWbmou4EWgp4HD-E6PMFq9MRw");
                    uploader.addEntry("1323894097", name);
                    uploader.addEntry("1546882991", message);
                    uploader.upload();
                    ETMessage.setText("");
                } else
                    Toast.makeText(FeedBackActivity.this, "Please enter a valid name/message.", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_back, menu);
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
