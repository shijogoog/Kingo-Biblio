package kingo_bibio.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kingo_bibio.dto.HistoriqueEmpruntDTO;
import kingo_bibio.service.BibliothequeService;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueRestController {

    private final BibliothequeService bibliothequeService;

    public HistoriqueRestController(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    // Obtenir tout l'historique d'un étudiant -> GET http://localhost:8080/api/historiques/membre/{id}
    @GetMapping("/membre/{membreId}")
    public ResponseEntity<List<HistoriqueEmpruntDTO>> obtenirHistoriqueEtudiant(@PathVariable Long membreId) {
        List<HistoriqueEmpruntDTO> historique = bibliothequeService.obtenirHistoriqueMembre(membreId);
        
        if (historique.isEmpty()) {
            return ResponseEntity.noContent().build(); // Renvoie un code 204 si aucun emprunt trouvé
        }
        return ResponseEntity.ok(historique);
    }
}