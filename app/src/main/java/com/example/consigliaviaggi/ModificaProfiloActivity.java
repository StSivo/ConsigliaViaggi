package com.example.consigliaviaggi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

        AlertDialog.Builder miaAlert5 = new AlertDialog.Builder(this);
        miaAlert5.setTitle("ATTENZIONE");
        miaAlert5.setMessage("Formato e-mail non corretto.");
        AlertDialog emailFormat_alert = miaAlert5.create();

        AlertDialog.Builder miaAlert6 = new AlertDialog.Builder(this);
        miaAlert6.setTitle("ATTENZIONE");
        miaAlert6.setMessage("I seguenti campi non possono essere vuoti:" +
                "\n\"Nome\"\n\"Cognome\"\n\"Username\"\n\"E-mail\"\n\"Domanda Segreta\"\n\"Risposta\"");
        AlertDialog campi_alert = miaAlert6.create();

        AlertDialog.Builder miaAlert7 = new AlertDialog.Builder(this);
        miaAlert7.setTitle("ATTENZIONE");
        miaAlert7.setMessage("La password deve essere di almeno 6 caratteri");
        AlertDialog passwordCorta_alert = miaAlert7.create();

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

                if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || username.isEmpty() ||
                        domanda_segreta.isEmpty() || risposta.isEmpty()) {
                    campi_alert.show();
                } else {
                    if (ValidateEmail(email)) {
                        if (password.length()>=6 || password.isEmpty()) {
                            int updateRet = DatiUtenteUpdate.DatiUtenteUpdate(utente, nome, cognome, username, email,
                                    domanda_segreta, risposta, password, cPassword);
                            if (updateRet == -1) {
                                usernamePreso_alert.show();
                            } else if (updateRet == -2) {
                                emailPresa_alert.show();
                            } else if (updateRet == -3) {
                                passwordDiverse_alert.show();
                            } else if (updateRet == -4) {
                                noModifiche_alert.show();
                            } else {
                                Toast.makeText(ModificaProfiloActivity.this, "Modifiche effettuate.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ModificaProfiloActivity.this, HomeLoggedActivity.class));
                                finishAffinity();
                            }
                        } else {
                            passwordCorta_alert.show();
                        }
                    } else {
                        emailFormat_alert.show();
                    }
                }
            }
        });

    }

    public static boolean ValidateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
