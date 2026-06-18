package kingo_bibio.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kingo_bibio.model.Membre;
import kingo_bibio.service.MembreService;

@RestController
@RequestMapping("/api/membres")
public class MembreRestController {

    private final MembreService membreService;

    public MembreRestController(MembreService membreService) {
        this.membreService = membreService;
    }

    // 1. Lister tous les membres -> GET http://localhost:8080/api/membres
    @GetMapping
    public List<Membre> listerTousLesMembres() {
        return membreService.listerTousLesMembres();
    }

    // 2. Trouver un membre par son ID -> GET http://localhost:8080/api/membres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Membre> obtenirMembreParId(@PathVariable Long id) {
        return membreService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Créer un membre -> POST http://localhost:8080/api/membres (avec un JSON dans le corps)
    @PostMapping
    public ResponseEntity<?> inscrireNouveauMembre(@RequestBody Membre membre) {
        boolean estAjoute = membreService.ajouterMembre(membre);
        if (estAjoute) {
            return new ResponseEntity<>(membre, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Cet email est déjà utilisé.", HttpStatus.BAD_REQUEST);
    }

    // 4. Modifier un membre -> PUT http://localhost:8080/api/membres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> mettreAJourMembre(@PathVariable Long id, @RequestBody Membre detailsMembre) {
        boolean estModifie = membreService.modifierMembre(id, detailsMembre);
        if (estModifie) {
            return ResponseEntity.ok("Informations du membre mises à jour avec succès.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Membre introuvable.");
    }

    // 5. Supprimer un membre -> DELETE http://localhost:8080/api/membres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerCompteMembre(@PathVariable Long id) {
        try {
            membreService.supprimerMembre(id);
            return ResponseEntity.ok("Le compte a été supprimé.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Impossible de supprimer ce membre (il a probablement des emprunts actifs).");
        }
    }
}