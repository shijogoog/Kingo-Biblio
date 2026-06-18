package kingo_bibio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kingo_bibio.model.Livre;
import kingo_bibio.service.LivreService;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    @GetMapping
    public List<Livre> getAllLivres() {
        return livreService.listerTousLesLivres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livre = livreService.trouverParId(id);
        return livre.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Livre> ajouterLivre(@RequestBody Livre livre) {
        boolean ok = livreService.ajouterLivre(livre);
        if (!ok) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(livre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifierLivre(@PathVariable Long id, @RequestBody Livre livre) {
        boolean ok = livreService.modifierLivre(id, livre);
        return ok ? ResponseEntity.ok().build()
                  : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerLivre(@PathVariable Long id) {
        livreService.supprimerLivre(id);
        return ResponseEntity.ok().build();
    }
}