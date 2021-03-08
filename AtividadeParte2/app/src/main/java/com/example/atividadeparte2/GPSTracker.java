package com.example.atividadeparte2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPSTracker implements LocationListener {

    Context context;
    public GPSTracker(Context c)
    {
        context = c;
    }

    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_LONG).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkGPSEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Nós também verificamos pelo NETWORK_PROVIDER porque num dos nossos dispositivos, o GPS_PROVIDER não retornava nada
        if(isGPSEnabled || isNetworkGPSEnabled)
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (l != null)
            {
                return l;
            }
            else if(isNetworkGPSEnabled)
            {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 6000, 10, this);
                return lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        else
        {
            Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
