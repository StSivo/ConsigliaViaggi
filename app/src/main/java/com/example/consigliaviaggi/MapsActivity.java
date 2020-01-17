package com.example.consigliaviaggi;

import androidx.fragment.app.FragmentActivity;

import android.location.Geocoder;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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

        //Retrive data from previous activity
        List<Struttura> risultati = getIntent().getParcelableArrayListExtra("risultati");

        // Add a marker in Sydney and move the camera
        LatLng napoli = new LatLng(40.839981, 14.252540);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(napoli,15));

        for (int i=0;i<risultati.size();i++){
            System.out.println("MAPS: " + risultati.get(i).getLat() + " " + risultati.get(i).getLon());
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(risultati.get(i).getLat(),risultati.get(i).getLon())).title(risultati.get(i).getNome()));
        }

    }
}
