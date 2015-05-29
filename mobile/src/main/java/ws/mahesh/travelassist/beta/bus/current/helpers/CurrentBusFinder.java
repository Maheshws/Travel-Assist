package ws.mahesh.travelassist.beta.bus.current.helpers;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ws.mahesh.travelassist.beta.bus.current.models.StopTimeObject;
import ws.mahesh.travelassist.beta.bus.current.models.TripsObject;

/**
 * Created by mahesh on 18/03/15.
 */
public class CurrentBusFinder {
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public static ArrayList<TripsObject> parseResponse(String res) {
        ArrayList<TripsObject> objects = new ArrayList<>();
        try {
            JSONObject objectRes = new JSONObject(res);
            JSONArray resArray = objectRes.getJSONArray("response");
            for (int i = 0; i < resArray.length(); i++) {
                ArrayList<StopTimeObject> temp = new ArrayList<>();
                JSONObject object1 = (JSONObject) resArray.get(i);
                JSONArray stoptimeArray = object1.getJSONArray("results" + object1.getInt("trip_id"));
                for (int j = 0; j < stoptimeArray.length(); j++) {
                    JSONObject object2 = (JSONObject) stoptimeArray.get(j);
                    temp.add(new StopTimeObject(object2.getInt("stop_id"), object2.getLong("time")));
                }
                objects.add(new TripsObject(objectRes.getInt("bus_id"), object1.getInt("trip_id"), temp, object1.getString("dir")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return objects;

    }

    public String getCurrentBus(int id) {
        String URL = "http://tm-server.appspot.com/activelist?route_id=" + id;
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(URL));
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else {
                result = "Something went Wrong";
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }


}
