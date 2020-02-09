package com.example.dailyuadb.Model;

public class Idee {

    private String email;
    private String date;
    private String heure;
    private String id;
    private String description;
    private String prenom;
    private String nom;

    public Idee() {
    }

    public Idee(String id, String email, String description, String date, String heure, String prenom, String nom) {
        this.id = id;
        this.email = email;
        this.description = description;
        this.date = date;
        this.heure = heure;
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
