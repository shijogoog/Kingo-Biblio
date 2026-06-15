package kingo_bibio.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kingo_bibio.model.Livre;
import kingo_bibio.service.BibliothequeService;

@RestController
@RequestMapping("/api/livres")
public class LivreRestController {

    private final BibliothequeService bibliothequeService;

    public LivreRestController(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    @GetMapping
    public List<Livre> listerLesLivres(@RequestParam(value = "search", required = false) String search) {
        return bibliothequeService.filtrerEtChercherLivres(search);
    }

    @PostMapping
    public ResponseEntity<Livre> ajouterUnNouveauLivre(@RequestBody Livre livre) {
        Livre sauvegarde = bibliothequeService.enregistrerLivre(livre);
        return new ResponseEntity<>(sauvegarde, HttpStatus.CREATED);
    }
}