package kingo_bibio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kingo_bibio.service.BibliothequeService;
import kingo_bibio.service.LivreService;

@RestController
@RequestMapping("/api/bibliotheque")
public class BibliothequeController {

    private final BibliothequeService bibliothequeService;
    private final LivreService livreService;

    public BibliothequeController(BibliothequeService bibliothequeService, LivreService livreService) {
        this.bibliothequeService = bibliothequeService;
        this.livreService = livreService;
    }

    @GetMapping("/emprunts-actifs")
    public Object empruntsActifs() {
        return bibliothequeService.recupererTousLesEmpruntsActifs();
    }

    @GetMapping("/alertes-retard")
    public Object alertes() {
        return bibliothequeService.calculerLesAlertesDeRetardActuelles();
    }

    @GetMapping("/livres")
    public Object livres(@RequestParam(required = false) String search) {
        return livreService.filtrerEtChercherLivres(search);
    }

    @PostMapping("/emprunter")
    public void emprunter(@RequestParam String codeBarre, @RequestParam Long idMembre) {
        bibliothequeService.enregistrerEmprunt(codeBarre, idMembre, 14);
    }

    @PostMapping("/retour/{id}")
    public void retour(@PathVariable Long id) {
        bibliothequeService.restituerLivre(id);
    }
}