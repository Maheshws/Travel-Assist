package ws.mahesh.travelassist.beta;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mahesh on 27/03/15.
 */
public class StaticHolder {
    public static int DATABASE_VERSION = 7;
    public static Location currentLocation = null;
    public static String SSShareText = "Shared using Travel Assist App";

    public static void saveBitmap(Bitmap bitmap, String path) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Travel Assist");
        if (!folder.exists()) {
            folder.mkdir();
        }
        File imagePath = new File(Environment.getExternalStorageDirectory() + path);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
}
