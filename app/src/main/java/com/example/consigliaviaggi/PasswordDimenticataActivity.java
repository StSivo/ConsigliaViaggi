package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PasswordDimenticataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_dimenticata);

        this.setTitle("Recupero Password");
    }
}
