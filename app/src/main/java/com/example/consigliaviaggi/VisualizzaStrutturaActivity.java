package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.consigliaviaggi.Controller.VisualizzaStrutturaController;
import com.example.consigliaviaggi.Model.Recensione;
import com.example.consigliaviaggi.Model.Struttura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VisualizzaStrutturaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_struttura);

        List<String> risultati_ricerca = new ArrayList<>();
        List<Recensione> risultati_no_order = new ArrayList<Recensione>();

        TextView nome_struttura_text = (TextView)findViewById(R.id.nome_struttura_text);
        TextView citta_struttura_text = (TextView)findViewById(R.id.citta_struttura_text);
        TextView fascia_prezzo_struttura_text = (TextView)findViewById(R.id.fascia_prezzo_struttura_text);
        TextView indirizzo_struttura_text = (TextView)findViewById(R.id.indirizzo_struttura_text);
        TextView valutazione_media_struttura_text = (TextView)findViewById(R.id.valutazione_media_struttura_text);
        TextView descrizione_struttura_text = (TextView)findViewById(R.id.descrizione_struttura_text);
        TextView filtro_text = (TextView)findViewById(R.id.filtro_text);
        descrizione_struttura_text.setMovementMethod(new ScrollingMovementMethod());
        ListView visualizza_recensioni_view = (ListView)findViewById(R.id.visualizza_recensioni_view);
        Spinner seleziona_filtro_spinner = (Spinner)findViewById(R.id.seleziona_filtro_spinner);

        Struttura struttura_selezionata = (Struttura) getIntent().getParcelableExtra("struttura_selezionata");

        List<Recensione> risultati = VisualizzaStrutturaController.RicercaRecensione(struttura_selezionata.getIndirizzo());

        //Fill a list to get a no order filter
        risultati_no_order.addAll(risultati);

        //Show results
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,risultati_ricerca);
        visualizza_recensioni_view.setAdapter(adapter);

        //Fill every text field
        nome_struttura_text.setText(struttura_selezionata.getNome());
        citta_struttura_text.setText(struttura_selezionata.getCitta());
        fascia_prezzo_struttura_text.setText("€"+struttura_selezionata.getPrezzo_min()+" - €"+struttura_selezionata.getPrezzo_max());
        indirizzo_struttura_text.setText(struttura_selezionata.getIndirizzo());
        descrizione_struttura_text.setText(struttura_selezionata.getDescrizione());
        if(struttura_selezionata.getValutazione_media()!=0){
            valutazione_media_struttura_text.setText(String.valueOf("Valutazione Media: "+struttura_selezionata.getValutazione_media()));
        }
        else{
            valutazione_media_struttura_text.setText("Nessuna Valutazione");
            filtro_text.setVisibility(View.GONE);
            seleziona_filtro_spinner.setVisibility(View.GONE);
        }

        //Selecting reviews filtering options
        seleziona_filtro_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleziona_filtro = seleziona_filtro_spinner.getSelectedItem().toString();
                if(seleziona_filtro.equals("Ordina per numero stelle crescente")) {
                    Collections.sort(risultati);
                }
                else if(seleziona_filtro.equals("Ordina per numero stelle decrescente")){
                    Collections.sort(risultati, Collections.reverseOrder());
                }
                else if(seleziona_filtro.equals("Nessuno")){
                    risultati.removeAll(risultati);
                    risultati.addAll(risultati_no_order);
                }
                //Prepare new sorted results
                risultati_ricerca.removeAll(risultati_ricerca);
                for(int i=0; i<risultati.size();i++){
                    risultati_ricerca.add("Voto: " + Integer.toString(risultati.get(i).getVoto())+" - "+risultati.get(i).getTitolo());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        visualizza_recensioni_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                Recensione recensione_selezionata = new Recensione(risultati.get(position));
                startActivity(new Intent(VisualizzaStrutturaActivity.this, VisualizzaRecensioneActivity.class)
                        .putExtra("recensione_selezionata",recensione_selezionata));
            }
        });
    }
}
