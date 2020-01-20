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

import com.example.consigliaviaggi.Model.Utente;
import com.example.consigliaviaggi.Support.DatiUtenteQuery;
import com.example.consigliaviaggi.Support.DatiUtenteUpdate;
import com.example.consigliaviaggi.Support.SignupUtente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        List<Utente> risultati = new ArrayList<>();

        //Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        miaAlert4.setMessage("Formato e-mail non corretto.");
        AlertDialog emailFormat_alert = miaAlert4.create();

        AlertDialog.Builder miaAlert5 = new AlertDialog.Builder(this);
        miaAlert5.setTitle("ATTENZIONE");
        miaAlert5.setMessage("I seguenti campi non possono essere vuoti:" +
                "\n\"Nome\"\n\"Cognome\"\n\"Username\"\n\"E-mail\"\n\"Domanda Segreta\"\n\"Risposta\"");
        AlertDialog campi_alert = miaAlert5.create();

        AlertDialog.Builder miaAlert6 = new AlertDialog.Builder(this);
        miaAlert6.setTitle("ATTENZIONE");
        miaAlert6.setMessage("La password deve essere di almeno 6 caratteri");
        AlertDialog passwordCorta_alert = miaAlert6.create();

        Button modifica_button = findViewById(R.id.modifica_button);
        EditText nome_form = findViewById(R.id.nome_form);
        EditText cognome_form = findViewById(R.id.cognome_form);
        EditText username_form = findViewById(R.id.username_form);
        EditText email_form = findViewById(R.id.email_form);
        EditText password_form = findViewById(R.id.password_form);
        EditText cPassword_form = findViewById(R.id.cPassword_form);
        EditText domanda_segreta_form = findViewById(R.id.domanda_segreta_form);
        EditText risposta_form = findViewById(R.id.risposta_form);

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
                        domanda_segreta.isEmpty() || risposta.isEmpty() || password.isEmpty() || cPassword.isEmpty()) {
                    campi_alert.show();
                } else {
                    if (ValidateEmail(email)) {
                        if (password.length()>=6) {
                            if (password.equals(cPassword)) {
                                int updateRet = SignupUtente.AggiungiUtente(nome, cognome, username, email,
                                        domanda_segreta, risposta, password, cPassword);
                                if (updateRet == -1) {
                                    usernamePreso_alert.show();
                                } else if (updateRet == -2) {
                                    emailPresa_alert.show();
                                } else {
                                    Toast.makeText(SignupActivity.this, "Account creato, effettua il login.", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                    finishAffinity();
                                }
                            } else {
                                passwordDiverse_alert.show();
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

    public static boolean ValidateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
