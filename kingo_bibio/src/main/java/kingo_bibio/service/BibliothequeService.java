package kingo_bibio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kingo_bibio.dto.HistoriqueEmpruntDTO;
import kingo_bibio.model.Emprunt;
import kingo_bibio.model.Exemplaire;
import kingo_bibio.model.Livre;
import kingo_bibio.repository.EmpruntRepository;
import kingo_bibio.repository.ExemplaireRepository;

@Service
public class BibliothequeService {

    private final EmpruntRepository empruntRepository;
    private final ExemplaireRepository exemplaireRepository;

    // Modifie le constructeur pour ajouter les deux nouveaux repositories
    public BibliothequeService(EmpruntRepository empruntRepository, 
                               ExemplaireRepository exemplaireRepository) {
        this.empruntRepository = empruntRepository;
        this.exemplaireRepository = exemplaireRepository;
    }

    // --- EMPRUNTS ACTIFS ---
    public List<Emprunt> recupererTousLesEmpruntsActifs() {
        return empruntRepository.findByDateRetourIsNull();
    }

    // --- ALERTES RETARD ---
    public List<Emprunt> calculerLesAlertesDeRetardActuelles() {
        LocalDate aujourdHui = LocalDate.now();
        return empruntRepository.findByDateRetourIsNull()
                .stream()
                .filter(e -> e.getDateRetourPrevue().isBefore(aujourdHui))
                .toList();
    }

    // --- EMPRUNTER ---
    public void enregistrerEmprunt(String codeBarre, Long membreId, int jours) {
        if (empruntRepository.existsByCodeBarreAndDateRetourIsNull(codeBarre)) {
            throw new RuntimeException("Livre déjà emprunté");
        }

        Emprunt emprunt = new Emprunt();
        emprunt.setCodeBarre(codeBarre);
        emprunt.setMembreId(membreId);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(LocalDate.now().plusDays(jours));

        empruntRepository.save(emprunt);
    }

    // --- RETOUR ---
    public void restituerLivre(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new RuntimeException("Emprunt introuvable"));
        emprunt.setDateRetour(LocalDate.now());
        empruntRepository.save(emprunt);
    }

    // --- NOUVELLE MÉTHODE : HISTORIQUE D'UN MEMBRE ---
    public List<HistoriqueEmpruntDTO> obtenirHistoriqueMembre(Long membreId) {
        // 1. Récupère tous les emprunts de ce membre
        List<Emprunt> emprunts = empruntRepository.findByMembreId(membreId);
        
        // 2. Transforme chaque emprunt en DTO
        return emprunts.stream()
                .map(this::convertirEnDTO)
                .collect(Collectors.toList());
    }

    // Méthode privée pour convertir un Emprunt en HistoriqueEmpruntDTO
    private HistoriqueEmpruntDTO convertirEnDTO(Emprunt emprunt) {
        // On récupère l'exemplaire à partir du code barre
        Exemplaire exemplaire = exemplaireRepository.findByCodeBarre(emprunt.getCodeBarre())
                .orElseThrow(() -> new RuntimeException("Exemplaire introuvable pour le code : " + emprunt.getCodeBarre()));
        
        // On récupère le livre à partir de l'exemplaire
        Livre livre = exemplaire.getLivre();

        // On détermine le statut
        String statut = (emprunt.getDateRetour() == null) ? "EN_COURS" : "RESTITUÉ";

        // On construit le DTO avec toutes les infos
        return new HistoriqueEmpruntDTO(
                emprunt.getId(),
                livre.getTitre(),
                livre.getAuteur(),
                emprunt.getCodeBarre(),
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue(),
                emprunt.getDateRetour(),
                statut
        );
    }
}