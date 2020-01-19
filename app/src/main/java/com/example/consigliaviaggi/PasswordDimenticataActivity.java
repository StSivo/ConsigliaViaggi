package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.consigliaviaggi.LoginActivity;

public class PasswordDimenticataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_dimenticata);

        this.setTitle("Recupero Password");

        Button invia_button=(Button)findViewById(R.id.email_password_dimenticata_button);
        invia_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(PasswordDimenticataActivity.this, DomandaSegretaActivity.class));
            }
        });
    }
}
