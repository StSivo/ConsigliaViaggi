package com.example.consigliaviaggi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.consigliaviaggi.LoginActivity;
import com.example.consigliaviaggi.Model.Utente;
import com.example.consigliaviaggi.Support.DatiUtenteQuery;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordDimenticataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_dimenticata);

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Riempire il campo email.");
        AlertDialog campoVuoto_alert = miaAlert.create();

        AlertDialog.Builder miaAlert2 = new AlertDialog.Builder(this);
        miaAlert2.setTitle("ATTENZIONE");
        miaAlert2.setMessage("Formato e-mail non corretto.");
        AlertDialog emailFormat_alert = miaAlert2.create();

        AlertDialog.Builder miaAlert3 = new AlertDialog.Builder(this);
        miaAlert3.setTitle("ATTENZIONE");
        miaAlert3.setMessage("E-mail non trovata.");
        AlertDialog nonTrovato_alert = miaAlert3.create();

        Button invia_button=(Button)findViewById(R.id.email_password_dimenticata_button);
        EditText email_form = (EditText) findViewById(R.id.email_form);

        invia_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                String email = email_form.getText().toString();
                if(ValidateEmail(email)) {
                    if (!email.isEmpty()) {
                        Utente utente = DatiUtenteQuery.RicercaDatiUtente(email);
                        if (utente!=null) {
                            startActivity(new Intent(PasswordDimenticataActivity.this, DomandaSegretaActivity.class)
                                    .putExtra("utente", utente));
                        } else {
                            nonTrovato_alert.show();
                        }
                    } else {
                        campoVuoto_alert.show();
                    }
                } else {
                    emailFormat_alert.show();
                }

            }
        });
    }

    public static boolean ValidateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
