package com.example.demo_bibliotheque;

public class Livre {
    private long id;
    private String titre;
    private String auteur;
    private int anneePublication;

    public Livre(long id,String titre, String auteur, int anneePublication) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnneePublication() {
        return anneePublication;
    }

    public void setAnneePublication(int anneePublication) {
        this.anneePublication = anneePublication;
    }

    @Override
    public String toString() {
        return "{Id : " + id + ", Titre : " + titre + ", Auteur : " + auteur + ", Annee de publication : " + anneePublication + "}";
    }
}
