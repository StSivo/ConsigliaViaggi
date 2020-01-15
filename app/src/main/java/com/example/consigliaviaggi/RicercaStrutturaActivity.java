package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.consigliaviaggi.Model.Struttura;

import java.util.ArrayList;
import java.util.List;

public class RicercaStrutturaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ricerca_struttura);
        List<String> risultati_ricerca = new ArrayList<String>();

        //Retrive data from previous activity
        List<Struttura> risultati = getIntent().getParcelableArrayListExtra("risultati");
        System.out.println("RicercaStruttura " + risultati.size());

        ListView risultati_ricerca_view = (ListView) findViewById(R.id.risultati_ricerca_view);
        Button visualizza_map_button=(Button)findViewById(R.id.visualizza_map_button);

        visualizza_map_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(RicercaStrutturaActivity.this, MapsActivity.class));
            }
        });

        for(int i=0; i<risultati.size();i++){
            risultati_ricerca.add(risultati.get(i).getNome()+"\n"+risultati.get(i).getIndirizzo()+"\n"+risultati.get(i).getTipo()+"\n"
                    +"€"+risultati.get(i).getPrezzo_min()+" - €"+risultati.get(i).getPrezzo_max());
        }

        risultati_ricerca_view.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,risultati_ricerca));

    }
}
