package ws.mahesh.travelassist.beta.bus.finder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import ws.mahesh.travelassist.beta.R;
import ws.mahesh.travelassist.beta.bus.finder.models.MapsObject;

/**
 * Created by mahesh on 14/03/15.
 */
public class FinderRouteMapActivity extends ActionBarActivity implements OnMapReadyCallback {
    PolylineOptions polylineOptions;
    private ArrayList<MapsObject> mapsObjects = new ArrayList<>();
    private ArrayList<LatLng> arrayPoints1 = new ArrayList<>();
    private ArrayList<LatLng> arrayPoints2 = new ArrayList<>();
    private ArrayList<LatLng> arrayPoints3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Route Map");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mapsObjects = extras.getParcelableArrayList("map_object");
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        int c1 = 0, c2 = 0, c3 = 0;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mapsObjects.get(0).getLocation().getLatitude(), mapsObjects.get(0).getLocation().getLongitude()), 13));
        for (int i = 0; i < mapsObjects.size(); i++) {
            if (mapsObjects.get(i).getBusCount() == 1) {
                if (c1 == 0) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()))
                            .title(mapsObjects.get(i).getStopname())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    c1++;
                }
                arrayPoints1.add(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()));
            } else if (mapsObjects.get(i).getBusCount() == 2) {
                if (c2 == 0) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()))
                            .title(mapsObjects.get(i).getStopname())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    c2++;
                    arrayPoints1.add(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()));
                }
                arrayPoints2.add(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()));
            } else if (mapsObjects.get(i).getBusCount() == 3) {
                if (c3 == 0) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()))
                            .title(mapsObjects.get(i).getStopname())
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    c3++;
                    arrayPoints2.add(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()));
                }
                arrayPoints3.add(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()));
            }
            if (i == (mapsObjects.size() - 1)) {
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(mapsObjects.get(i).getLocation().getLatitude(), mapsObjects.get(i).getLocation().getLongitude()))
                        .title(mapsObjects.get(i).getStopname())
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

        }

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(arrayPoints1);
        map.addPolyline(polylineOptions);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(5);
        polylineOptions.addAll(arrayPoints2);
        map.addPolyline(polylineOptions);

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        polylineOptions.addAll(arrayPoints3);
        map.addPolyline(polylineOptions);


    }

}
