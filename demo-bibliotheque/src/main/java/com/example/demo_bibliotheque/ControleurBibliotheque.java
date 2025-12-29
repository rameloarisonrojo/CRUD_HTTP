package com.example.demo_bibliotheque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Livre mettreAJourLivre(@PathVariable long id, @Valid @RequestBody Livre livreModifie) {
        return serviceLivres.modifierLivre(id, livreModifie);
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livre ajoutLivre(@Valid @RequestBody Livre nouveauLivre) {
        return serviceLivres.ajouterLivre(nouveauLivre);
    }

    // -------------------------------------------------------------------
    // GET : @GetMapping
    // URL : GET http://localhost:8080/api/v1/livres
    @GetMapping
    public List<LivreDTO> listeLivres() {
        return serviceLivres.trouverTousLesLivres().stream()
                .map(l -> new LivreDTO(l.getId(), l.getTitre(), l.getAuteur(), l.getAnneePublication()))
                .toList();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> gererValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erreurs = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomChamp = ((FieldError) error).getField();
            String messageErreur = error.getDefaultMessage();
            erreurs.put(nomChamp, messageErreur);
        });
        return erreurs;
    }
}