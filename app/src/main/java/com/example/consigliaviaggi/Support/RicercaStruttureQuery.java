package com.example.consigliaviaggi.Support;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.consigliaviaggi.Model.Struttura;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RicercaStruttureQuery {

    public static List<Struttura> Ricerca(String nome_struttura, String citta, String tipo_struttura,
                                          int prezzo_min, int prezzo_max){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Struttura> risultati = new ArrayList<>();

        // Create a reference to the Strutture collection
        CollectionReference struttureRef = db.collection("Strutture");

        // Create a query against the collection.
        Query query = struttureRef;

        if (!nome_struttura.isEmpty()) {
            query = query.whereEqualTo("nome", nome_struttura);
        }
        if (!citta.isEmpty()) {
            query = query.whereEqualTo("citta", citta);
        }
        if (!tipo_struttura.equals("Tutte")) {
            query = query.whereEqualTo("tipo", tipo_struttura);
        }
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
                Log.d("01", document.getId() + " => " + document.getData());
                Struttura struttura =  document.toObject(Struttura.class);
                risultati.add(struttura);
            }
        } else {
            Log.d("01", "Error getting documents: ", task.getException());
        }

        //Apply price range filters
        for(int i=0; i<risultati.size(); i++) {
            if((risultati.get(i).getPrezzo_min()<prezzo_min)|(risultati.get(i).getPrezzo_max()>prezzo_max)) {
                risultati.remove(i);
            }
        }

        return risultati;
    }
}
