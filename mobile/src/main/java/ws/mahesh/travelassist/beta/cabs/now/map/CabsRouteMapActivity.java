package ws.mahesh.travelassist.beta.cabs.now.map;

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
import ws.mahesh.travelassist.beta.cabs.StaticCabsHolder;
import ws.mahesh.travelassist.beta.cabs.now.models.LocationTrackerObject;

/**
 * Created by mahesh on 14/03/15.
 */
public class CabsRouteMapActivity extends ActionBarActivity implements OnMapReadyCallback {
    PolylineOptions polylineOptions;
    private ArrayList<LocationTrackerObject> locationsList = null;
    private ArrayList<LatLng> arrayPoints = new ArrayList<>();

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
        locationsList = StaticCabsHolder.locationsList;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (locationsList != null && locationsList.size() > 0) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationsList.get(0).getLocation().getLatitude(), locationsList.get(0).getLocation().getLongitude()), 13));
            for (int i = 0; i < locationsList.size(); i++) {
                if (i == 0) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(locationsList.get(i).getLocation().getLatitude(), locationsList.get(i).getLocation().getLongitude()))
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                arrayPoints.add(new LatLng(locationsList.get(i).getLocation().getLatitude(), locationsList.get(i).getLocation().getLongitude()));
                if (i == locationsList.size() - 1) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(locationsList.get(i).getLocation().getLatitude(), locationsList.get(i).getLocation().getLongitude()))
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
            }
        }

        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(10);
        polylineOptions.addAll(arrayPoints);
        map.addPolyline(polylineOptions);

    }

}
