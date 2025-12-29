package com.example.demo_bibliotheque;

import jakarta.persistence.*; // Importations pour JPA
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Livre {
    @Id // Définit la clé primaire (l'ID unique)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementation de l'ID
    private long id;

    @NotBlank(message = "Le titre doit avoir une valeur.")
    @Size(min = 2, max = 100, message = "Le titre doit avoir entre 2 et 100 caractères."    )

    private String titre;

    @NotBlank(message = "L'auteur doit avoir une valeur.")

    private String auteur;

    @Min(value = 1450, message = "L'année doit être après l'invention de l'imprimerie (1450).")
    @Max(value = 2025, message = "L'annee doit etre avant 2025.")
    
    private int anneePublication;

    // IMPORTANT : JPA a besoin d'un constructeur vide pour fonctionner
    public Livre() {}

    public Livre(String titre, String auteur, int anneePublication) {
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
        return " { " +
                "Id : " + id +
                ", Titre : " + titre +
                ", Auteur : " + auteur +
                ", Annee de publication : " + anneePublication +
                " } ";
    }
}
