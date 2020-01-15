package com.example.consigliaviaggi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Struttura implements Parcelable {

    private String nome;
    private Double valutazione_media;
    private String citta;
    private String indirizzo;
    private String descrizione;
    private String tipo;
    private int prezzo_min;
    private int prezzo_max;

    /*public enum Tipo_struttura{
        Hotel,
        Ristorante,
        Attrazione
    } */

    public Struttura(){}

    protected Struttura(Parcel in) {
        nome = in.readString();
        if (in.readByte() == 0) {
            valutazione_media = null;
        } else {
            valutazione_media = in.readDouble();
        }
        citta = in.readString();
        indirizzo = in.readString();
        descrizione = in.readString();
        tipo = in.readString();
        prezzo_min = in.readInt();
        prezzo_max = in.readInt();
    }

    public static final Creator<Struttura> CREATOR = new Creator<Struttura>() {
        @Override
        public Struttura createFromParcel(Parcel in) {
            return new Struttura(in);
        }

        @Override
        public Struttura[] newArray(int size) {
            return new Struttura[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(nome);
        if (valutazione_media == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(valutazione_media);
        }
        dest.writeString(citta);
        dest.writeString(indirizzo);
        dest.writeString(descrizione);
        dest.writeString(tipo);
        dest.writeInt(prezzo_min);
        dest.writeInt(prezzo_max);
    }

    public String getNome() {
        return nome;
    }

    public Double getValutazioneMedia() {
        return valutazione_media;
    }

    public String getCitta() {
        return citta;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPrezzo_min() {
        return prezzo_min;
    }

    public int getPrezzo_max() {
        return prezzo_max;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public void setValutazione_media(Double valutazione_media) {
        this.valutazione_media=valutazione_media;
    }

    public void setCitta(String citta) {
        this.citta=citta;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo=indirizzo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione=descrizione;
    }

    public void setTipo(String tipo) {
        this.tipo=tipo;
    }

    public void setPrezzo_min(int prezzo_min) {
        this.prezzo_min=prezzo_min;
    }

    public void setPrezzo_max(int prezzo_max) {
        this.prezzo_max=prezzo_max;
    }
}
