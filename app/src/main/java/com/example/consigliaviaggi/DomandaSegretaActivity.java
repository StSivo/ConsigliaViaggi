package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.consigliaviaggi.ui.login.LoginActivity;

public class DomandaSegretaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domanda_segreta);

        Button domanda_segreta_button=(Button)findViewById(R.id.domanda_segreta_button);
        domanda_segreta_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(DomandaSegretaActivity.this, ModificaPasswordActivity.class));
            }
        });
    }
}
