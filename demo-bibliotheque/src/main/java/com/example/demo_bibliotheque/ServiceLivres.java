package com.example.demo_bibliotheque;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service // Indique à Spring de créer et gérer cette classe (c'est notre "bean")
public class ServiceLivres {
    // Déclaration de la liste comme attribut de la classe
    private final List<Livre> livres = new ArrayList<>();
    // Compteur pour simuler un ID de base de données
    private long idCounter = 1;

    public ServiceLivres() {
        // Ajout des ID à l'initialisation (simulation)
        livres.add(new Livre(idCounter++, "Le Seigneur des anneaux", "J.R.R. Tolkien", 1954));
        livres.add(new Livre(idCounter++, "1984", "George Orwell", 1949));
        livres.add(new Livre(idCounter++, "Le Petit Prince", "Antoine de Saint-Exupéry", 1943));
    }

    // -------------------------------------------------------------------
    // CREATE (POST)
    public Livre ajouterLivre(Livre nouveauLivre) {
        nouveauLivre.setId(idCounter++); // On attribue le nouvel ID avant d'ajouter
        this.livres.add(nouveauLivre);
        return nouveauLivre;
    }

    // -------------------------------------------------------------------
    // READ par ID (GET /livres/{id})
    public Livre trouverParId(long id) {
        // On utilise la méthode de recherche, et si le livre n'est pas là (.orElseThrow), 
        // on lance notre exception personnalisée.
        return livres.stream()
                     .filter(livre -> livre.getId() == id)
                     .findFirst()
                     .orElseThrow(() -> new LivreNonTrouveException(id));
    }

    // -------------------------------------------------------------------
    // UPDATE (PUT /livres/{id})
    public Livre mettreAJourLivre(long id, Livre livreModifie) {
        Livre livreExistant = trouverParId(id); // Utilise la méthode qui lance l'exception

        // Si trouverParId ne lance pas d'exception, le livre existe
        livreExistant.setTitre((livreModifie.getTitre()));
        livreExistant.setAuteur(livreModifie.getAuteur());
        livreExistant.setAnneePublication(livreModifie.getAnneePublication());

        return livreExistant;
    }

    // -------------------------------------------------------------------
    // DELETE (DELETE /livres/{id})
    public void supprimerLivre(long id) {
        boolean supprimer = livres.removeIf(livre -> livre.getId() == id);
        if (!supprimer) {
            // Si la suppression n'a pas réussi, c'est que le livre n'existait pas
            throw new LivreNonTrouveException(id);
        }
    }

    // -------------------------------------------------------------------
    // GET (GET /livres/)
    public List<Livre> trouverTousLesLivres() {
        return livres;
    }

    // -------------------------------------------------------------------
    // NOUVELLE MÉTHODE : Filtrage par Auteur
    public List<Livre> trouverParAuteur(String auteur) {
        // Utilisation de l'API Stream de Java 8 pour filtrer la liste
        return livres.stream()
        // Garde uniquement les livres dont l'auteur contient le terme recherché (ignorant la casse)
        .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
        // Transforme le résultat en nouvelle liste
        .collect(Collectors.toList());
    }
}
