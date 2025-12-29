package com.example.demo_bibliotheque;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    // C'est tout
    // JpaRepository contient déjà les méthodes : save(), findAll(), findById(), deleteById() ...
    // On peut même ajouter des méthodes de recherche personnalisées par nom !

    List<Livre> findByAuteurContainingIgnoreCase(String auteur);
    
}
