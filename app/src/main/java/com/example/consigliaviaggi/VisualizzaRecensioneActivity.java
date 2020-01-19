package com.example.consigliaviaggi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.consigliaviaggi.Model.Recensione;

public class VisualizzaRecensioneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_recensione);

        TextView titolo_recensione_text = (TextView)findViewById(R.id.titolo_recensione_text);
        titolo_recensione_text.setMovementMethod(new ScrollingMovementMethod());
        TextView autore_recensione_text = (TextView)findViewById(R.id.autore_recensione_text);
        TextView commento_recensione_text = (TextView)findViewById(R.id.commento_recensione_form);
        commento_recensione_text.setMovementMethod(new ScrollingMovementMethod());
        RatingBar voto_recensione_text = (RatingBar) findViewById(R.id.voto_recensione_ratingBar);

        Recensione recensione_selezionata = (Recensione) getIntent().getParcelableExtra("recensione_selezionata");

        titolo_recensione_text.setText(recensione_selezionata.getTitolo());
        autore_recensione_text.setText("Autore: "+recensione_selezionata.getAutore());
        commento_recensione_text.setText(recensione_selezionata.getCommento());
        voto_recensione_text.setNumStars(recensione_selezionata.getVoto());

    }
}
