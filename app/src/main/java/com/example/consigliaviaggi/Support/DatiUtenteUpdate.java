package com.example.consigliaviaggi.Support;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.consigliaviaggi.Model.Utente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class DatiUtenteUpdate {

    public static int DatiUtenteUpdate(Utente utente, String nome, String cognome, String username,
                                       String email, String domanda_segreta, String risposta,
                                       String password, String cPassword) {

        List<Utente> risultati = new ArrayList<>();

        //Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference utenteRef = db.collection("Utenti");

        //If these two fields differs, then we need to check if the new username is already taken
        if(!username.equals(utente.getUsername())) {
            // Create a query against the collection.
            Query query = utenteRef.whereEqualTo("username", username);
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
                    Log.d("07", document.getId() + " => " + document.getData());
                    Utente user = document.toObject(Utente.class);
                    risultati.add(user);
                }
            } else {
                Log.d("07", "Error getting documents: ", task.getException());
            }

            //If the new username is not already taken, we check what fields the user changed
            //and update the User document
            if (risultati.size() == 0) {
                Query query2 = utenteRef.whereEqualTo("username", utente.getUsername());
                //After creating a query object, use the get() function to retrieve the results
                Task<QuerySnapshot> task2 = query2.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {}
                        });

                //Waiting for async task to complete
                while (!task2.isComplete()) {}
                if (task2.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task2.getResult()) {
                        Log.d("08", document.getId() + " => " + document.getData());
                        WriteBatch batch = db.batch();
                        DocumentReference docRef = db.collection("Utenti").document(document.getId());
                        batch.update(docRef, "username", username);
                        if (!nome.equals(utente.getNome())) {
                            batch.update(docRef, "nome", nome);
                        }
                        if (!cognome.equals(utente.getCognome())) {
                            batch.update(docRef, "cognome", cognome);
                        }
                        if (!domanda_segreta.equals(utente.getDomanda_segreta())) {
                            batch.update(docRef, "domanda_segreta", domanda_segreta);
                        }
                        if (!risposta.equals(utente.getRisposta())) {
                            batch.update(docRef, "risposta", risposta);
                        }
                        //If these two fields differs, then we need to check if the new email is already taken
                        if (!email.equals(utente.getEmail())) {
                            // Create a query against the collection.
                            Query query4 = utenteRef.whereEqualTo("email", email);
                            //After creating a query object, use the get() function to retrieve the results
                            Task<QuerySnapshot> task4 = query4.get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        }
                                    });

                            //Waiting for async task to complete
                            while (!task4.isComplete()) {
                            }
                            if (task4.isSuccessful()) {
                                for (QueryDocumentSnapshot document2 : task4.getResult()) {
                                    Log.d("11", document2.getId() + " => " + document.getData());
                                    Utente user = document2.toObject(Utente.class);
                                    risultati.add(user);
                                }
                            } else {
                                Log.d("11", "Error getting documents: ", task4.getException());
                            }
                            if (risultati.size() == 0) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                Task<Void> task5 = user.updateEmail(email)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("12", "User email address updated.");
                                                }
                                            }
                                        });
                                batch.update(docRef, "email", email);

                            } else {
                                return -2;
                            }
                        }
                        if (!password.isEmpty()){
                            if (password.equals(cPassword)){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                user.updatePassword(password)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("12", "User password updated.");
                                                }
                                            }
                                        });
                            }
                            else{
                                return -3;
                            }
                        }

                        Task task3 = batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
                        while (task3.isComplete()) {
                        }
                        if (task3.isSuccessful()) {
                            return 0;
                        } else {
                            Log.d("09", "Error getting documents: ", task.getException());
                        }
                    }
                } else {
                    Log.d("08", "Error getting documents: ", task.getException());
                }
            } else {
                return -1;
            }
        }
        else{
            Query query2 = utenteRef.whereEqualTo("username", utente.getUsername());
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
                    Log.d("08", document.getId() + " => " + document.getData());
                    boolean flag = false; //Checks if at least one field has changed
                    boolean password_flag = false; //If the password will change, no warnings will be shown
                    WriteBatch batch = db.batch();
                    DocumentReference docRef = db.collection("Utenti").document(document.getId());
                    if (!nome.equals(utente.getNome())) {
                        batch.update(docRef, "nome", nome);
                        flag = true;
                    }
                    if (!cognome.equals(utente.getCognome())) {
                        batch.update(docRef, "cognome", cognome);
                        flag = true;
                    }
                    if (!domanda_segreta.equals(utente.getDomanda_segreta())) {
                        batch.update(docRef, "domanda_segreta", domanda_segreta);
                        flag = true;
                    }
                    if (!risposta.equals(utente.getRisposta())) {
                        batch.update(docRef, "risposta", risposta);
                        flag = true;
                    }
                    //If these two fields differs, then we need to check if the new email is already taken
                    if (!email.equals(utente.getEmail())) {
                        // Create a query against the collection.
                        Query query4 = utenteRef.whereEqualTo("email", email);
                        //After creating a query object, use the get() function to retrieve the results
                        Task<QuerySnapshot> task4 = query4.get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    }
                                });

                        //Waiting for async task to complete
                        while (!task4.isComplete()) {
                        }
                        if (task4.isSuccessful()) {
                            for (QueryDocumentSnapshot document2 : task4.getResult()) {
                                Log.d("11", document2.getId() + " => " + document.getData());
                                Utente user = document2.toObject(Utente.class);
                                risultati.add(user);
                            }
                        } else {
                            Log.d("11", "Error getting documents: ", task4.getException());
                        }
                        if (risultati.size() == 0) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("12", "User email address updated.");
                                            }
                                        }
                                    });

                            batch.update(docRef,"email",email);
                            flag = true;

                        } else {
                            return -2;
                        }
                    }
                    if (!password.isEmpty()){
                        if (password.equals(cPassword)){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.updatePassword(password)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("12", "User password updated.");
                                            }
                                        }
                                    });
                            password_flag = true;
                        }
                        else{
                            return -3;
                        }
                    }
                    //If at least one field has been edited then we submit the update to Firebase
                    if (flag) {
                        Task task3 = batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
                        while (task3.isComplete()) {
                        }
                        if (task3.isSuccessful()) {
                            return 0;
                        } else {
                            Log.d("13", "Error");
                        }
                    }
                    else{
                        if (!password_flag) {
                            return -4;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
