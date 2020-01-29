package com.example.consigliaviaggi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.consigliaviaggi.Support.RecensioniStrutturaQuery;
import com.example.consigliaviaggi.Model.Recensione;
import com.example.consigliaviaggi.Model.Struttura;
import com.google.firebase.auth.FirebaseAuth;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisualizzaStrutturaActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private VisualizzaRecensioneActivity visualizzaRecensioneActivity;
    private AggiungiRecensioneActivity aggiungiRecensioneActivity;
    private RecensioniStrutturaQuery recensioniStrutturaQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_struttura);

        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
        miaAlert.setTitle("ATTENZIONE");
        miaAlert.setMessage("Effettuare il Login per aggiungere una recensione.");
        AlertDialog notLogged_alert = miaAlert.create();

        List<String> risultati_ricerca = new ArrayList<>();
        List<Recensione> risultati_no_order = new ArrayList<Recensione>();

        Button aggiungi_recensione_button = findViewById(R.id.aggiungi_recensione_button);
        TextView nome_struttura_text = findViewById(R.id.nome_struttura_text);
        TextView citta_struttura_text = findViewById(R.id.citta_struttura_text);
        TextView fascia_prezzo_struttura_text = findViewById(R.id.fascia_prezzo_struttura_text);
        TextView indirizzo_struttura_text = findViewById(R.id.indirizzo_struttura_text);
        TextView valutazione_media_struttura_text = findViewById(R.id.valutazione_media_struttura_text);
        TextView descrizione_struttura_text = findViewById(R.id.descrizione_struttura_text);
        TextView filtro_text = findViewById(R.id.filtro_text);
        descrizione_struttura_text.setMovementMethod(new ScrollingMovementMethod());
        ListView visualizza_recensioni_view = findViewById(R.id.visualizza_recensioni_view);
        Spinner seleziona_filtro_spinner = findViewById(R.id.seleziona_filtro_spinner);

        Struttura struttura_selezionata = getIntent().getParcelableExtra("struttura_selezionata");

        List<Recensione> risultati = RecensioniStrutturaQuery.RicercaRecensione(struttura_selezionata.getIndirizzo());

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

        //Picking the first decimal place of valutazioe_media after rounding off the value
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);
        String num = df.format(struttura_selezionata.getValutazione_media());

        //If there are reviews we show the average rating.
        //If there are any, we show another string instead (Nessuna Valutazione)
        if(struttura_selezionata.getValutazione_media()!=0){
            valutazione_media_struttura_text.setText(String.valueOf("Valutazione Media: "+num));
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

        aggiungi_recensione_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(VisualizzaStrutturaActivity.this, AggiungiRecensioneActivity.class)
                            .putExtra("struttura", struttura_selezionata));
                }
                else{
                    notLogged_alert.show();
                }

            }
        });

    }
}
