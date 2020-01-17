package com.example.consigliaviaggi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Struttura implements Parcelable {

    private String nome;
    private float valutazione_media;
    private String citta;
    private String indirizzo;
    private String descrizione;
    private String tipo;
    private int prezzo_min;
    private int prezzo_max;
    private Double lat;
    private Double lon;

    public String getNome() {
        return nome;
    }

    public float getValutazione_media() {
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

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setNome(String nome) {
        this.nome=nome;
    }

    public void setValutazione_media(float valutazione_media) {
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

    public void setPrezzo_max(int prezzo_max) { this.prezzo_max=prezzo_max; }

    public void setLat(Double lat) { this.lat=lat; }

    public void setLon(Double lon) { this.lon=lon; }

    public Struttura (Struttura another){
        this.nome = another.nome;
        this.valutazione_media = another.valutazione_media;
        this.citta = another.citta;
        this.indirizzo =another.indirizzo;
        this.descrizione=another.descrizione;
        this.tipo = another.tipo;
        this.prezzo_min = another.prezzo_min;
        this.prezzo_max = another.prezzo_max;
        this.lat = another.lat;
        this.lon = another.lon;

    }

    public Struttura(){}

    protected Struttura(Parcel in) {
        nome = in.readString();
        valutazione_media = in.readFloat();
        citta = in.readString();
        indirizzo = in.readString();
        descrizione = in.readString();
        tipo = in.readString();
        prezzo_min = in.readInt();
        prezzo_max = in.readInt();
        if (in.readByte() == 0) {
            lat = null;
        } else {
            lat = in.readDouble();
        }
        if (in.readByte() == 0) {
            lon = null;
        } else {
            lon = in.readDouble();
        }
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
        dest.writeFloat(valutazione_media);
        dest.writeString(citta);
        dest.writeString(indirizzo);
        dest.writeString(descrizione);
        dest.writeString(tipo);
        dest.writeInt(prezzo_min);
        dest.writeInt(prezzo_max);
        if (lat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lat);
        }
        if (lon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(lon);
        }
    }
}
