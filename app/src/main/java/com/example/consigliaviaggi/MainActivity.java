package com.example.consigliaviaggi;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.consigliaviaggi.Controller.HomeController;
import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button ricerca_button=(Button)findViewById(R.id.ricerca_button);
        Button login_button=(Button)findViewById(R.id.login_button);

        ricerca_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                Spinner tipo_struttura_spinner=(Spinner)findViewById(R.id.tipo_struttura_spinner);
                TextInputLayout nome_struttura_form=(TextInputLayout) findViewById(R.id.nome_struttura_form);
                TextInputLayout citta_form=(TextInputLayout) findViewById(R.id.citta_form);
                EditText prezzo_min_form=(EditText) findViewById(R.id.prezzo_min);
                EditText prezzo_max_form=(EditText) findViewById(R.id.prezzo_max);
                CheckBox proxmity_checkBox=(CheckBox)findViewById(R.id.proximity_checkBox);

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

                System.out.println("MAIN: "+ prezzo_max);
                System.out.println("MAIN: "+ prezzo_min);
                List<Struttura> risultati = HomeController.Ricerca(nome_struttura,citta,tipo_struttura,prezzo_min,prezzo_max);
                System.out.println("MainActivity: " + risultati.size());
                startActivity(new Intent(MainActivity.this, RicercaStrutturaActivity.class)
                        .putParcelableArrayListExtra("risultati", (ArrayList<? extends Parcelable>) risultati));

            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }


    /*public static List<Struttura> Ricerca(String nome_struttura, String citta, String tipo_struttura,int prezzo_min, int prezzo_max){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Struttura> risultati = new ArrayList<Struttura>();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        // Create a reference to the Strutture collection
        CollectionReference struttureRef = db.collection("Strutture");

        // Create a query against the collection.
        Query query = struttureRef;

        if (!nome_struttura.isEmpty()) {
            query = query.whereEqualTo("nome", nome_struttura);
        }
        if (!citta.isEmpty()) {
            query = query.whereEqualTo("citta", citta);
        }
        if (!tipo_struttura.equals("Tutte")) {
            query = query.whereEqualTo("tipo", tipo_struttura);
        }
        query=query.whereGreaterThanOrEqualTo("prezzo_min",prezzo_min).whereLessThanOrEqualTo("prezzo_max",prezzo_max);


        //After creating a query object, use the get() function to retrieve the results
        Task<QuerySnapshot> task = query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {}
                });
        while(!task.isComplete()){}
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("01", document.getId() + " => " + document.getData());
                Struttura struttura =  document.toObject(Struttura.class);
                risultati.add(struttura);
            }
        } else {
            Log.d("01", "Error getting documents: ", task.getException());
        }
        System.out.println("Ricerca: " + risultati.size());
        return risultati;
    }*/

}
