package com.example.consigliaviaggi.Model;

public class Utente {

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String domanda_segreta;
    private String risposta;

    public Utente() {}

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
}
