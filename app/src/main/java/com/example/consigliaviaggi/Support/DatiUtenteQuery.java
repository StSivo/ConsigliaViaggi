package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DatiUtenteQuery {

    public static Utente RicercaDatiUtente(String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Utente utente = new Utente();
        // Create a reference to the Utenti collection
        CollectionReference utentiRef = db.collection("Utenti");

        // Create a query against the collection.
        Query query = utentiRef.whereEqualTo("email",email);
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
                utente =  document.toObject(Utente.class);
                return utente;
            }
        } else {
            Log.d("07", "Error getting documents: ", task.getException());
        }

        return null;
    }
}
