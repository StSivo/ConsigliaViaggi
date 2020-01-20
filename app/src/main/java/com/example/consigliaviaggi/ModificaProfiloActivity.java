package com.example.consigliaviaggi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.Model.Utente;
import com.example.consigliaviaggi.Support.DatiUtenteQuery;
import com.example.consigliaviaggi.Support.DatiUtenteUpdate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class ModificaProfiloActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_profilo);

        List<Utente> risultati = new ArrayList<>();

        //Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Il nuovo username è gia stato preso.");
        AlertDialog usernamePreso_alert = miaAlert.create();

        AlertDialog.Builder miaAlert2 = new AlertDialog.Builder(this);
        miaAlert2.setTitle("ATTENZIONE");
        miaAlert2.setMessage("La nuova e-mail è gia stata presa.");
        AlertDialog emailPresa_alert = miaAlert2.create();

        AlertDialog.Builder miaAlert3 = new AlertDialog.Builder(this);
        miaAlert3.setTitle("ATTENZIONE");
        miaAlert3.setMessage("I campi \"Password\" e \"Conferma Password\" sono diversi.");
        AlertDialog passwordDiverse_alert = miaAlert3.create();

        AlertDialog.Builder miaAlert4 = new AlertDialog.Builder(this);
        miaAlert4.setTitle("ATTENZIONE");
        miaAlert4.setMessage("Nessun campo è stato modificato.");
        AlertDialog noModifiche_alert = miaAlert4.create();

        Button modifica_button = findViewById(R.id.modifica_button);
        EditText nome_form = findViewById(R.id.nome_form);
        EditText cognome_form = findViewById(R.id.cognome_form);
        EditText username_form = findViewById(R.id.username_form);
        EditText email_form = findViewById(R.id.email_form);
        EditText password_form = findViewById(R.id.password_form);
        EditText cPassword_form = findViewById(R.id.cPassword_form);
        EditText domanda_segreta_form = findViewById(R.id.domanda_segreta_form);
        EditText risposta_form = findViewById(R.id.risposta_form);

        Utente utente = DatiUtenteQuery.RicercaDatiUtente(mAuth.getCurrentUser().getEmail());

        nome_form.setText(utente.getNome());
        cognome_form.setText(utente.getCognome());
        username_form.setText(utente.getUsername());
        email_form.setText(utente.getEmail());
        domanda_segreta_form.setText(utente.getDomanda_segreta());
        risposta_form.setText(utente.getRisposta());

        modifica_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nome_form.getText().toString();
                String cognome = cognome_form.getText().toString();
                String username = username_form.getText().toString();
                String email = email_form.getText().toString();
                String domanda_segreta = domanda_segreta_form.getText().toString();
                String risposta = risposta_form.getText().toString();
                String password = password_form.getText().toString();
                String cPassword = cPassword_form.getText().toString();

                int updateRet = DatiUtenteUpdate.DatiUtenteUpdate(utente,nome,cognome,username,email,
                        domanda_segreta,risposta,password,cPassword);
                if(updateRet==-1){
                    usernamePreso_alert.show();
                }
                else if(updateRet==-2){
                    emailPresa_alert.show();
                }
                else if(updateRet==-3){
                    passwordDiverse_alert.show();
                }
                else if(updateRet==-4){
                    noModifiche_alert.show();
                }
                else{
                    Toast.makeText(ModificaProfiloActivity.this, "Modifiche effettuate.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ModificaProfiloActivity.this, HomeLoggedActivity.class));
                    finishAffinity();
                }


               /* CollectionReference strutturaRef = db.collection("Utenti");

                // Create a query against the collection.
                Query query = strutturaRef.whereEqualTo("username",username_form);
                //After creating a query object, use the get() function to retrieve the results
                Task<QuerySnapshot> task = query.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {}
                        });

                //Waiting for async task to complete
                while(!task.isComplete()){}
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("07", document.getId() + " => " + document.getData());
                        Utente utente =  document.toObject(Utente.class);
                        risultati.add(utente);
                    }
                } else {
                    Log.d("07", "Error getting documents: ", task.getException());
                }
                if(risultati.size()==0){
                    Query query2 = strutturaRef.whereEqualTo("username",utente.getUsername());
                    //After creating a query object, use the get() function to retrieve the results
                    Task<QuerySnapshot> task2 = query.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {}
                            });

                    //Waiting for async task to complete
                    while(!task2.isComplete()){}
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("08", document.getId() + " => " + document.getData());
                            WriteBatch batch = db.batch();
                            DocumentReference docRef = db.collection("Utenti").document(document.getId());
                            batch.update(docRef,"username",username_form);
                            batch.update(docRef,"username",username_form);
                        }
                    } else {
                        Log.d("08", "Error getting documents: ", task.getException());
                    }
                }
                else{
                    usernamePreso_alert.show();
                }*/
            }
        });


    }
}
