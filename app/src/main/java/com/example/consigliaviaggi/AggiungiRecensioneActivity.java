package com.example.consigliaviaggi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.Model.Utente;
import com.example.consigliaviaggi.Support.AggiungiRecensioneUpload;
import com.example.consigliaviaggi.Support.CheckRecensioniUtente;
import com.example.consigliaviaggi.Support.DatiUtenteQuery;
import com.google.firebase.auth.FirebaseAuth;

public class AggiungiRecensioneActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_recensione);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Hai gia aggiunto o richiesto l'aggiunta di una recensione");
        AlertDialog giaRecensito_alert = miaAlert.create();

        Button invia_recensione_button = (Button)findViewById(R.id.invia_recensione_button);
        Spinner voto_recensione_spinner = (Spinner)findViewById(R.id.voto_recensione_spinner);
        EditText titolo_recensione_form = (EditText)findViewById(R.id.titolo_recensione_form);
        EditText commento_recensione_form = (EditText)findViewById(R.id.commento_recensione_form);
        Switch dati_switch = (Switch)findViewById(R.id.dati_switch);

        Utente utente = DatiUtenteQuery.RicercaDatiUtente(mAuth.getCurrentUser().getEmail());

        dati_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    dati_switch.setText("             Pubblica con username");
                }
                else{
                    dati_switch.setText("Pubblica con nome e cognome");
                }
            }
        });

        invia_recensione_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titolo_recensione = titolo_recensione_form.getText().toString();
                String commento_recensione = commento_recensione_form.getText().toString();
                int voto_recensione = Integer.parseInt(voto_recensione_spinner.getSelectedItem().toString());
                Struttura struttura = getIntent().getParcelableExtra("struttura");

                if(CheckRecensioniUtente.CheckRecensioni(utente.getUsername(),struttura.getIndirizzo())) {
                    AggiungiRecensioneUpload.AggiungiRecensioneUp(titolo_recensione, voto_recensione,
                            commento_recensione, struttura, utente, dati_switch.isChecked());
                }
                else{
                    giaRecensito_alert.show();
                }
            }
        });

    }
}