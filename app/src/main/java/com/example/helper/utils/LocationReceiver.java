package com.example.helper.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by HP on 09-03-2018.
 */

public class LocationReceiver extends BroadcastReceiver {
    public static LocationReceiverListener locationReceiverListener;

    public LocationReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            assert lm != null;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (locationReceiverListener != null) {

                locationReceiverListener.onLocationGetting(gps_enabled);

            }

            //noinspection ConstantConditions
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                Toast.makeText(context, "GPS ENABLED whooooooo", Toast.LENGTH_SHORT).show();
//                Intent pushIntent = new Intent(context, LocalService.class);
//                context.startService(pushIntent);
            }


        } catch (Exception ex) {
        }

    }

    public interface LocationReceiverListener {
        void onLocationGetting(boolean gps_enabled);
    }
}
