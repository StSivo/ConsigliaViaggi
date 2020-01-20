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
import android.widget.TextView;
import android.widget.Toast;

import com.example.consigliaviaggi.LoginActivity;
import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DomandaSegretaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domanda_segreta);

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Risposta errata, riprovare.");
        AlertDialog errata_alert = miaAlert.create();

        Button domanda_segreta_button=findViewById(R.id.domanda_segreta_button);
        TextView domanda_segreta_text=findViewById(R.id.domanda_segreta_text);
        EditText risposta_form = findViewById(R.id.risposta_form);

        Utente utente = getIntent().getParcelableExtra("utente");
        domanda_segreta_text.setText(utente.getDomanda_segreta());
        System.out.println(utente.getEmail());

        domanda_segreta_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(risposta_form.getText().toString().equals(utente.getRisposta())) {

                    FirebaseAuth.getInstance().sendPasswordResetEmail(utente.getEmail())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("08", "Email sent.");
                                        Toast.makeText(DomandaSegretaActivity.this, "Email inviata.", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(DomandaSegretaActivity.this, MainActivity.class));
                                        finishAffinity();
                                    }
                                }
                            });
                }
                else{
                    errata_alert.show();
                }
            }
        });
    }
}
