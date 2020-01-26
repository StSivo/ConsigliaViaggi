package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupUtente {

    public static int AggiungiUtente(String nome, String cognome, String username,
                                     String email, String domanda_segreta, String risposta,
                                     String password, String cPassword){

        List<Utente> risultati = new ArrayList<>();

        //Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        CollectionReference utenteRef = db.collection("Utenti");

        // Create a query against the collection.
        Query query = utenteRef.whereEqualTo("email", email);
        //After creating a query object, use the get() function to retrieve the results
        Task<QuerySnapshot> task = query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    }
                });

        //Waiting for async task to complete
        while (!task.isComplete()) {}
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("13", document.getId() + " => " + document.getData());
                Utente user = document.toObject(Utente.class);
                risultati.add(user);
            }
        } else {
            Log.d("13", "Error getting documents: ", task.getException());
        }
        //If the new email is not already taken, we check if the username is already taken
        if (risultati.size() == 0){
            // Create a query against the collection.
            Query query2 = utenteRef.whereEqualTo("username", username);
            //After creating a query object, use the get() function to retrieve the results
            Task<QuerySnapshot> task2 = query2.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        }
                    });

            //Waiting for async task to complete
            while (!task2.isComplete()) {}
            if (task2.isSuccessful()) {
                for (QueryDocumentSnapshot document : task2.getResult()) {
                    Log.d("15", document.getId() + " => " + document.getData());
                    Utente user = document.toObject(Utente.class);
                    risultati.add(user);
                }
            } else {
                Log.d("15", "Error getting documents: ", task.getException());
            }
            //If the new username is not already taken, we create the new account
            if (risultati.size() == 0) {
                Map<String, Object> newUtente = new HashMap<>();

                newUtente.put("username",username);
                newUtente.put("nome", nome);
                newUtente.put("cognome", cognome);
                newUtente.put("email", email);
                newUtente.put("domanda_segreta",domanda_segreta);
                newUtente.put("risposta", risposta);

                // Add a new document with a generated ID
                db.collection("Utenti")
                        .add(newUtente)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("16", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("16", "Error adding document", e);
                            }
                        });

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("14", "createUserWithEmail:success");
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("14", "createUserWithEmail:failure", task.getException());
                                }
                            }
                        });
            }
            else{
                return -1;
            }
        }
        else{
            return -2;
        }
        return 0;
    }
}
