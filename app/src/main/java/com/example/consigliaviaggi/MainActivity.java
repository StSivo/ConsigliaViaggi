package com.example.consigliaviaggi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.consigliaviaggi.Support.RicercaStruttureQuery;
import com.example.consigliaviaggi.Model.Struttura;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            mAuth.signOut();
        }

        Button ricerca_button=(Button)findViewById(R.id.ricerca_button);
        Button login_button=(Button)findViewById(R.id.login_button);
        CheckBox proximity_checkBox=(CheckBox)findViewById(R.id.proximity_checkBox);

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("La ricerca non ha prodotto risultati.");
        AlertDialog noResults_alert = miaAlert.create();

        ricerca_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                Spinner tipo_struttura_spinner=(Spinner)findViewById(R.id.tipo_struttura_spinner);
                TextInputLayout nome_struttura_form=(TextInputLayout) findViewById(R.id.nome_struttura_form);
                TextInputLayout citta_form=(TextInputLayout) findViewById(R.id.citta_form);
                EditText prezzo_min_form=(EditText) findViewById(R.id.prezzo_min);
                EditText prezzo_max_form=(EditText) findViewById(R.id.prezzo_max);

                int prezzo_min;
                int prezzo_max;
                String nome_struttura;
                String citta;
                String tipo_struttura;

                if(prezzo_min_form.getText().toString().isEmpty()){
                    prezzo_min=0;
                }
                else{
                    prezzo_min=Integer.parseInt(prezzo_min_form.getText().toString());
                }
                if(prezzo_max_form.getText().toString().isEmpty()){
                    prezzo_max=999999;
                }
                else{
                    prezzo_max=Integer.parseInt(prezzo_max_form.getText().toString());
                }

                nome_struttura=nome_struttura_form.getEditText().getText().toString();
                citta=citta_form.getEditText().getText().toString();
                tipo_struttura=tipo_struttura_spinner.getSelectedItem().toString();

                //Let the controller handle the Firestore query
                List<Struttura> risultati = RicercaStruttureQuery.Ricerca(nome_struttura,citta,tipo_struttura,prezzo_min,prezzo_max);

                //Filter results by proximity.
                //We don't need to check the permissions as we have already checked the box.
                if(proximity_checkBox.isChecked() && !risultati.isEmpty()){
                    Location user = getLocation();
                    double lat = user.getLatitude();
                    double lon = user.getLongitude();
                    final double offset = 0.005;
                    for (int i = 0; i < risultati.size(); i++) {
                        if(!(lat-offset<=risultati.get(i).getLat() && lon-offset<=risultati.get(i).getLon() && lat+offset>=risultati.get(i).getLat() && lon+offset>=risultati.get(i).getLon())){
                            risultati.remove(i);
                            i--;
                        }
                    }
                }

                //Alert for no results search
                if(risultati.size()==0){
                    noResults_alert.show();
                }
                else{
                    startActivity(new Intent(MainActivity.this, RicercaStrutturaActivity.class)
                            .putParcelableArrayListExtra("risultati", (ArrayList<? extends Parcelable>) risultati)
                            .putExtra("proximity_enabled", proximity_checkBox.isChecked()));
                }

            }
        });

        proximity_checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(proximity_checkBox.isChecked()){
                    requestLocationPermission();
                    if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==-1){
                        proximity_checkBox.setChecked(false);
                    }
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

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
