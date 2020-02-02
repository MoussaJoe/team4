package com.example.dailyuadb.Model;

public class User {

<<<<<<< HEAD
    private String nom;
    private String prenom;
    private String email;
    private String numCarte;
    private String password;
    private String imageUrl;
    private String profils_user;

    public User(String nom, String prenom, String email,String numCarte,String password) {
=======
    String nom;
    String prenom;
    String email;
    String numCarte;
    String password;
    String imageUrl;
    String profil;

    public User(String nom, String prenom, String email,String numCarte,String password,String profil) {
>>>>>>> origin/master
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numCarte = numCarte;
        this.password = password;
<<<<<<< HEAD
=======
        this.profil=profil;
>>>>>>> origin/master
    }

    public User() {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.numCarte = numCarte;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public User(String imageUrl) {
        this.imageUrl = imageUrl;
    }

<<<<<<< HEAD
    public String getProfils_user() {
        return profils_user;
    }

    public void setProfils_user(String profils_user) {
        this.profils_user = profils_user;
    }

=======
>>>>>>> origin/master
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

<<<<<<< HEAD
=======
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
>>>>>>> origin/master
}
