package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Recensione;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecensioniStrutturaQuery {

    public static List<Recensione> RicercaRecensione(String struttura) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Recensione> risultati = new ArrayList<>();

        // Create a reference to the Recensione collection
        CollectionReference recensioniRef = db.collection("Recensioni");

        // Create a query against the collection.
        Query query = recensioniRef.whereEqualTo("struttura",struttura).whereEqualTo("pubblicata",true);

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
                Log.d("02", document.getId() + " => " + document.getData());
                Recensione recensione =  document.toObject(Recensione.class);
                risultati.add(recensione);
            }
        } else {
            Log.d("02", "Error getting documents: ", task.getException());
        }

        return risultati;
    }
}
