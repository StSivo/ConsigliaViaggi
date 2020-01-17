package com.example.consigliaviaggi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recensione implements Parcelable,Comparable<Recensione> {

    private String username;
    private String autore;
    private String commento;
    private Boolean pubblicata;
    private String struttura;
    private String titolo;
    private int voto;

    public Recensione(){}

    public Recensione (Recensione another){
        this.username = another.username;
        this.autore = another.autore;
        this.commento = another.commento;
        this.pubblicata = another.pubblicata;
        this.struttura =another.struttura;
        this.titolo=another.titolo;
        this.voto = another.voto;
    }

    public String getUsername() { return username; }

    public String getAutore() { return autore; }

    public String getCommento() { return commento; }

    public Boolean getPubblicata() { return pubblicata; }

    public String getStruttura() { return struttura; }

    public String getTitolo() { return titolo; }

    public int getVoto() { return voto; }

    public void setUsername (String username) { this.username = username; }

    public void setAutore(String autore) { this.autore = autore; }

    public void setCommento(String commento) { this.commento = commento; }

    public void setPubblicata(Boolean pubblicata) { this.pubblicata = pubblicata; }

    public void setStruttura(String struttura) { this.struttura = struttura; }

    public void setTitolo(String titolo) { this.titolo = titolo; }

    public void setVoto(int voto) { this.voto = voto; }

    protected Recensione(Parcel in) {
        username = in.readString();
        autore = in.readString();
        commento = in.readString();
        byte tmpPubblicata = in.readByte();
        pubblicata = tmpPubblicata == 0 ? null : tmpPubblicata == 1;
        struttura = in.readString();
        titolo = in.readString();
        voto = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(autore);
        dest.writeString(commento);
        dest.writeByte((byte) (pubblicata == null ? 0 : pubblicata ? 1 : 2));
        dest.writeString(struttura);
        dest.writeString(titolo);
        dest.writeInt(voto);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recensione> CREATOR = new Creator<Recensione>() {
        @Override
        public Recensione createFromParcel(Parcel in) {
            return new Recensione(in);
        }

        @Override
        public Recensione[] newArray(int size) {
            return new Recensione[size];
        }
    };

    @Override
    public int compareTo(Recensione o) {
        Integer temp = this.getVoto();
        return temp.compareTo(o.getVoto());
    }
}
