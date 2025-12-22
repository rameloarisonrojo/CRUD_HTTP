package com.example.demo_bibliotheque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livres")
public class ControleurBibliotheque {

    private final ServiceLivres serviceLivres;
    
    // ... Constructeur DI inchangé ...
    public ControleurBibliotheque(ServiceLivres serviceLivres) {
        this.serviceLivres = serviceLivres;
    }

    // -------------------------------------------------------------------
    // READ par ID : @GetMapping
    // URL : GET http://localhost:8080/api/v1/livres/1
    @GetMapping("/{id}")
    // @PathVariable récupère la valeur 'id' dans l'URL et la convertit en long
    public Livre trouverLivreParId(@PathVariable long id) {
        // Si le livre est trouvé, retourne 200 OK
        // S'il n'est pas trouvé, l'exception est lancée, et Spring la transforme en 404 NOT FOUND
       return serviceLivres.trouverParId(id);
    }

    // -------------------------------------------------------------------
    // UPDATE : @PutMapping
    // URL : PUT http://localhost:8080/api/v1/livres/1
    @PutMapping("/{id}")
    public Livre modifierLivre(@PathVariable long id, @RequestBody Livre livreModifie) {
        // Si le livre est trouvé et mis à jour, retourne 200 OK
        // S'il n'est pas trouvé, l'exception est lancée -> 404 NOT FOUND
        return serviceLivres.mettreAJourLivre(id, livreModifie);
    }

    // -------------------------------------------------------------------
    // DELETE : @DeleteMapping
    // URL : DELETE http://localhost:8080/api/v1/livres/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerLivre(@PathVariable long id) {
        serviceLivres.supprimerLivre(id);
        // Si la suppression réussit (car l'exception n'a pas été lancée), 
        // on retourne le statut 204 NO CONTENT (pas de corps à afficher)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    // -------------------------------------------------------------------
    // POST : @PostMapping
    // URL : POST http://localhost:8080/api/v1/livres
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Livre ajoutLivre(@RequestBody Livre nouveauLivre) {
        return serviceLivres.ajouterLivre(nouveauLivre);
    }

    // -------------------------------------------------------------------
    // GET : @GetMapping
    // URL : GET http://localhost:8080/api/v1/livres
    @GetMapping("/")
    public List<Livre> trouverTousLesLivres(
        // @RequestParam String auteur : Spring essaie de trouver ?auteur=...
        // required = false : le paramètre est OPTIONNEL. S'il n'est pas fourni, 'auteur' sera null.
        @RequestParam(required = false) String auteur
    ) {
        if (auteur != null) {
            // Si l'auteur est fourni dans l'URL, on filtre
            return serviceLivres.trouverParAuteur(auteur);
        } else {
            // Sinon, on retourne tous les livres
            return serviceLivres.trouverTousLesLivres();
        }
    }
}