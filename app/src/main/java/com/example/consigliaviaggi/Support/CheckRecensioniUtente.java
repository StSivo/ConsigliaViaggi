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

public class CheckRecensioniUtente {

    public static boolean CheckRecensioni(String username,String indirizzo_struttura){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Recensione> recensione = new ArrayList<>();
        // Create a reference to the Recensione collection
        CollectionReference recensioniRef = db.collection("Recensioni");
        System.out.println("SUPPORT: "+username+" - "+indirizzo_struttura);
        // Create a query against the collection.
        Query query = recensioniRef.whereEqualTo("username",username).whereEqualTo("struttura",indirizzo_struttura);
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
                Log.d("06", document.getId() + " => " + document.getData());
                Recensione rec =  document.toObject(Recensione.class);
                recensione.add(rec);
            }
        } else {
            Log.d("06", "Error getting documents: ", task.getException());
        }

        if(recensione.size()==0){ return true; }
        else{  return false; }
    }
}
