package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AggiungiRecensione {



    public static void AggiungiRecensioneUp(String titolo_recensione, int voto_recensione, String commento,
                                            Struttura struttura, Utente utente, boolean useUsername){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> recensione = new HashMap<>();
        //Choose to put username or name and surname
        if(useUsername){
            recensione.put("autore", utente.getUsername());
        }
        else{
            recensione.put("autore", utente.getNome()+" "+utente.getCognome());
        }
        recensione.put("username",utente.getUsername());
        recensione.put("titolo", titolo_recensione);
        recensione.put("commento", commento);
        recensione.put("voto", voto_recensione);
        recensione.put("struttura",struttura.getIndirizzo());
        recensione.put("pubblicata", false);

        // Add a new document with a generated ID
        db.collection("Recensioni")
                .add(recensione)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("05", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("05", "Error adding document", e);
                    }
                });
    }
}
