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
import android.widget.Toast;

import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.Model.Utente;
import com.example.consigliaviaggi.Support.AggiungiRecensione;
import com.example.consigliaviaggi.Support.CheckRecensioniUtente;
import com.example.consigliaviaggi.Support.DatiUtenteQuery;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class AggiungiRecensioneActivity extends AppCompatActivity {

    private DatiUtenteQuery datiUtenteQuery;
    private CheckRecensioniUtente checkRecensioniUtente;
    private AggiungiRecensione aggiungiRecensione;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_recensione);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Hai gia aggiunto o richiesto l'aggiunta di una recensione.");
        AlertDialog giaRecensito_alert = miaAlert.create();

        AlertDialog.Builder miaAlert2 = new AlertDialog.Builder(this);
        miaAlert2.setTitle("ATTENZIONE");
        miaAlert2.setMessage("Titolo non valido:\n\nLa stringa contiene uno/più spazi all'inizio\n" +
                "La stringa contiene uno/più spazi tra due parole\nLa stringa contiene uno/più spazi alla fine");
        AlertDialog titoloNonValido_alert = miaAlert2.create();

        AlertDialog.Builder miaAlert3 = new AlertDialog.Builder(this);
        miaAlert3.setTitle("ATTENZIONE");
        miaAlert3.setMessage("Il titolo non può essere vuoto.");
        AlertDialog noTitolo_alert = miaAlert3.create();

        Button invia_recensione_button = findViewById(R.id.invia_recensione_button);
        Spinner voto_recensione_spinner = findViewById(R.id.voto_recensione_spinner);
        EditText titolo_recensione_form = findViewById(R.id.titolo_recensione_form);
        EditText commento_recensione_form = findViewById(R.id.commento_recensione_form);
        Switch dati_switch = findViewById(R.id.dati_switch);

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

                if (!titolo_recensione.isEmpty()) {
                    if (IsInvalidText(titolo_recensione)) {
                        titoloNonValido_alert.show();
                    } else {
                        if (CheckRecensioniUtente.CheckRecensioni(utente.getUsername(), struttura.getIndirizzo())) {
                            AggiungiRecensione.AggiungiRecensioneUp(titolo_recensione, voto_recensione,
                                    commento_recensione, struttura, utente, dati_switch.isChecked());
                            Toast.makeText(AggiungiRecensioneActivity.this, "Recensione inviata con successo.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            giaRecensito_alert.show();
                        }
                    }
                } else {
                    noTitolo_alert.show();
                }
            }
        });

    }

    public static boolean IsInvalidText(String text){
        String textRegex = "^\\s.*";
        String textRegex2= ".*\\s\\s.*";
        String textRegex3= ".*\\s$";
        Pattern pat = Pattern.compile(textRegex);
        Pattern pat2 = Pattern.compile(textRegex2);
        Pattern pat3 = Pattern.compile(textRegex3);
        if (text.isEmpty()){
            return false;
        }

        return pat.matcher(text).matches() || pat2.matcher(text).matches() || pat3.matcher(text).matches();
    }

}
