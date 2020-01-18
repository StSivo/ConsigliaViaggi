package com.example.consigliaviaggi;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;

import com.example.consigliaviaggi.Model.Struttura;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        requestLocationPermission();
        if(ContextCompat.checkSelfPermission(MapsActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==0){
            mMap.setMyLocationEnabled(true);
        }
        else {  mMap.setMyLocationEnabled(false); }

        //Center the camera on user's position if the proximity search is enabled, on Italy's otherwise
        if(mMap.isMyLocationEnabled() && getIntent().getExtras().getBoolean("proximity_enabled")) {
            // Move the camera on your location
            Location user = getLocation();
            double lat = user.getLatitude();
            double lon = user.getLongitude();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon),(float)17));
        }
        else{
            // Move the camera on Italy
            LatLng italia = new LatLng(41.871941, 12.567380);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(italia,(float)5.5));
        }
        //Retrive data from previous activity
        List<Struttura> risultati = getIntent().getParcelableArrayListExtra("risultati");

        for (int i=0;i<risultati.size();i++){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(risultati.get(i).getLat(),risultati.get(i).getLon())).title(risultati.get(i).getNome()));
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "Autorizzare i seguenti permessi.", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            @SuppressLint("MissingPermission") Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                @SuppressLint("MissingPermission") Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                System.out.println("1::"+loc);
                System.out.println("2::"+loc.getLatitude());
                return loc;
            }
        } else {
            return null;
        }
    }

}

