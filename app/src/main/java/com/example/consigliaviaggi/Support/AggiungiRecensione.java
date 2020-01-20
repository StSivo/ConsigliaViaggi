package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Struttura;
import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1beta1.WriteResult;

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

        /*float nuova_vm = (struttura.getValutazione_media()+voto_recensione)/2;

        CollectionReference strutturaRef = db.collection("Strutture");

        // Create a query against the collection.
        Query query = strutturaRef.whereEqualTo("indirizzo",struttura.getIndirizzo());
        //After creating a query object, use the get() function to retrieve the results
        Task<QuerySnapshot> task = query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {}
                });

        //Waiting for async task to complete
        while(!task.isComplete()){}
        if (task.isSuccessful()) {
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("07", document.getId() + " => " + document.getData());
                db.collection("Strutture").document(document.getId()).update("valutazione_media",nuova_vm);
            }
        } else {
            Log.d("07", "Error getting documents: ", task.getException());
        }*/


    }
}
