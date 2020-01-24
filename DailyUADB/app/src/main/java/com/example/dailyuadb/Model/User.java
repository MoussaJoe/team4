package com.example.dailyuadb.Model;

public class User {

    String nom;
    String prenom;
    String email;
    String numCarte;
    String password;

    public User(String nom, String prenom, String email,String numCarte,String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numCarte = numCarte;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumCarte() {
        return numCarte;
    }

    public void setNumCarte(String numCarte) {
        this.numCarte = numCarte;
    }

}
