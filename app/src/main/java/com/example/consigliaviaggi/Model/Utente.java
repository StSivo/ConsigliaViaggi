package com.example.consigliaviaggi.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Utente implements Parcelable {

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String domanda_segreta;
    private String risposta;

    public Utente() {}

    public Utente(String username,String nome,String cognome,String email,String domanda_segreta,String risposta){
        this.username=username;
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.domanda_segreta=domanda_segreta;
        this.risposta=risposta;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getDomanda_segreta() {
        return domanda_segreta;
    }

    public String getRisposta() {
        return risposta;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDomanda_segreta(String domanda_segreta) {
        this.domanda_segreta = domanda_segreta;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    protected Utente(Parcel in) {
        username = in.readString();
        nome = in.readString();
        cognome = in.readString();
        email = in.readString();
        domanda_segreta = in.readString();
        risposta = in.readString();
    }

    public static final Creator<Utente> CREATOR = new Creator<Utente>() {
        @Override
        public Utente createFromParcel(Parcel in) {
            return new Utente(in);
        }

        @Override
        public Utente[] newArray(int size) {
            return new Utente[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(email);
        dest.writeString(domanda_segreta);
        dest.writeString(risposta);
    }
}
