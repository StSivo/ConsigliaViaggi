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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private PasswordDimenticataActivity passwordDimenticataActivity;
    private SignupActivity signupActivity;
    private HomeLoggedActivity homeLoggedActivity;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Alert for missing credential/s
        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Compilare tutti i campi.");
        AlertDialog credenziali_alert = miaAlert.create();

        //Alert for wrong credential/s
        AlertDialog.Builder miaAlert2 = new AlertDialog.Builder(this);
        miaAlert2.setTitle("ATTENZIONE");
        miaAlert2.setMessage("E-mail e/o password errate.");
        AlertDialog errate_alert = miaAlert2.create();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button signup_button = findViewById(R.id.signup_button);
        Button login_button = findViewById(R.id.login_button);
        Button password_dimenticata_button = findViewById(R.id.password_dimenticata_button);
        EditText email_form = findViewById(R.id.email_form);
        EditText password_form = findViewById(R.id.password_form);


        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                String email = email_form.getText().toString();
                String password = password_form.getText().toString();
                if(!(email.isEmpty() || password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("04", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        startActivity(new Intent(LoginActivity.this, HomeLoggedActivity.class));
                                        finishAffinity();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("04", "signInWithEmail:failure", task.getException());
                                        errate_alert.show();
                                    }
                                }
                            });
                    // [END sign_in_with_email]
                }
                else{credenziali_alert.show();}
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        password_dimenticata_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(LoginActivity.this, PasswordDimenticataActivity.class));
            }
        });
    }
}
