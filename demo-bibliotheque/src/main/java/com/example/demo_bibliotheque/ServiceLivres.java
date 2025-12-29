package com.example.demo_bibliotheque;

import java.util.List;

import org.springframework.stereotype.Service;

@Service // Indique à Spring de créer et gérer cette classe (c'est notre "bean")
public class ServiceLivres {

    private final LivreRepository livreRepository;
    // Injection du repository par le constructeur
    public ServiceLivres(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> trouverTousLesLivres() {
        return livreRepository.findAll(); // Utilise la base de données !
    }

    public Livre ajouterLivre(Livre nouveauLivre) {
        return livreRepository.save(nouveauLivre); // Enregistre en base
    }

    public Livre trouverParId(long id) {
        return livreRepository.findById(id)
                .orElseThrow(() -> new LivreNonTrouveException(id));
    }

    public List<Livre> trouverParAuteur(String auteur) {
        return livreRepository.findByAuteurContainingIgnoreCase(auteur);
    }

    public void supprimerLivre(long id) {
        if (!livreRepository.existsById(id)) {
            throw new LivreNonTrouveException(id);
        }
        livreRepository.deleteById(id);
    }

    public Livre modifierLivre(long id, Livre livreModifie) {
        // 1. On cherche le livre existant
        return livreRepository.findById(id).map(livreExistant -> {
            // 2. On met à jour les champs manuellement
            livreExistant.setTitre(livreModifie.getTitre());
            livreExistant.setAuteur(livreModifie.getAuteur());
            livreExistant.setAnneePublication(livreModifie.getAnneePublication());
            
            // 3. On sauvegarde le livre qui a déjà le bon ID
            return livreRepository.save(livreExistant);
        }).orElseThrow(() -> new LivreNonTrouveException(id));
    }
}