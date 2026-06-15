package kingo_bibio.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kingo_bibio.dto.AlerteRetardDTO;
import kingo_bibio.model.Emprunt;
import kingo_bibio.model.Livre;
import kingo_bibio.model.Membre;
import kingo_bibio.repository.EmpruntRepository;
import kingo_bibio.repository.LivreRepository;
import kingo_bibio.repository.MembreRepository;

@Service
public class BibliothequeService {

    private final LivreRepository livreRepository;
    private final MembreRepository membreRepository;
    private final EmpruntRepository empruntRepository;

    public BibliothequeService(LivreRepository livreRepository, 
                               MembreRepository membreRepository, 
                               EmpruntRepository empruntRepository) {
        this.livreRepository = livreRepository;
        this.membreRepository = membreRepository;
        this.empruntRepository = empruntRepository;
    }

    @Transactional(readOnly = true)
    public List<Livre> filtrerEtChercherLivres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return livreRepository.findAll();
        }
        return livreRepository.searchByKeyword(keyword);
    }

    @Transactional
    public Livre enregistrerLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    @Transactional
    public Membre inscrireMembre(Membre membre) {
        return membreRepository.save(membre);
    }

    @Transactional
    public Emprunt enregistrerEmprunt(Long livreId, Long membreId, int nombreDeJoursAutorises) {
        Livre livre = livreRepository.findById(livreId)
            .orElseThrow(() -> new RuntimeException("Échec : Aucun livre ne possède l'ID " + livreId));
        
        Membre membre = membreRepository.findById(membreId)
            .orElseThrow(() -> new RuntimeException("Échec : Aucun membre ne possède l'ID " + membreId));

        if (livre.getQuantiteDisponible() <= 0) {
            throw new RuntimeException("Impossible : Le livre '" + livre.getTitre() + "' est épuisé.");
        }

        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);

        Emprunt nouvelEmprunt = new Emprunt();
        nouvelEmprunt.setLivre(livre);
        nouvelEmprunt.setMembre(membre);
        nouvelEmprunt.setDateEmprunt(LocalDate.now());
        nouvelEmprunt.setDateRetourPrevue(LocalDate.now().plusDays(nombreDeJoursAutorises));
        nouvelEmprunt.setDateRetourReelle(null);

        return empruntRepository.save(nouvelEmprunt);
    }

    @Transactional
    public Emprunt restituerLivre(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
            .orElseThrow(() -> new RuntimeException("Erreur : Impossible de trouver cet emprunt."));

        if (emprunt.getDateRetourReelle() != null) {
            throw new RuntimeException("Erreur : Ce livre a déjà été déclaré comme rendu.");
        }

        emprunt.setDateRetourReelle(LocalDate.now());

        Livre livre = emprunt.getLivre();
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);

        return empruntRepository.save(emprunt);
    }

    @Transactional(readOnly = true)
    public List<Emprunt> recupererTousLesEmpruntsActifs() {
        return empruntRepository.findByDateRetourReelleIsNull();
    }

    @Transactional(readOnly = true)
    public List<AlerteRetardDTO> calculerLesAlertesDeRetardActuelles() {
        LocalDate aujourdHui = LocalDate.now();
        List<Emprunt> empruntsEnAnomalie = empruntRepository
            .findByDateRetourReelleIsNullAndDateRetourPrevueBefore(aujourdHui);
            
        List<AlerteRetardDTO> listeDesAlertes = new ArrayList<>();
        long tarifAmendeParJour = 500; // 500 FCFA par jour de retard

        for (Emprunt emp : empruntsEnAnomalie) {
            long joursDeRetard = ChronoUnit.DAYS.between(emp.getDateRetourPrevue(), aujourdHui);
            long totalAmende = joursDeRetard * tarifAmendeParJour;

            AlerteRetardDTO alerte = new AlerteRetardDTO(
                emp.getId(),
                emp.getMembre().getNom(),
                emp.getLivre().getTitre(),
                emp.getDateRetourPrevue(),
                joursDeRetard,
                totalAmende
            );
            listeDesAlertes.add(alerte);
        }
        return listeDesAlertes;
    }
}