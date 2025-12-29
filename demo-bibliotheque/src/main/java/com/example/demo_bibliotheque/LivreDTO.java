package com.example.demo_bibliotheque;

public record LivreDTO(Long id, String titre, String auteur, int anneePublication) {
    // Utiliser un 'record' en Java 21 est parfait pour les DTO :
    // c'est court, immuable, et génère automatiquement les getters !
}
