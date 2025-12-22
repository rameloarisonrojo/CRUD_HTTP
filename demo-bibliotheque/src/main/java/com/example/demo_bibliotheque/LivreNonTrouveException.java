package com.example.demo_bibliotheque;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Cette annotation est la clé ! Elle dit à Spring : "Si cette exception est lancée, 
// réponds au client avec le statut HTTP 404 NOT FOUND."
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LivreNonTrouveException extends RuntimeException {

    public LivreNonTrouveException(long id) {
        // Le message que le client verra si l'exception est lancée
        super("Le livre avec l'ID : " + id + "n'a pas été trouvé.");
    }
}
