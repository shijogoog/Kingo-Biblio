package kingo_bibio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kingo_bibio.model.Livre;
import kingo_bibio.repository.LivreRepository;

@Service
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    // --- LISTE ---
    public List<Livre> listerTousLesLivres() {
        return livreRepository.findAll();
    }

    // --- GET BY ID ---
    public Optional<Livre> trouverParId(Long id) {
        return livreRepository.findById(id);
    }

    // --- AJOUT ---
    public boolean ajouterLivre(Livre livre) {

        if (livre.getIsbn() != null && livreRepository.existsByIsbn(livre.getIsbn())) {
            return false;
        }

        livreRepository.save(livre);
        return true;
    }

    // --- MODIFICATION ---
    public boolean modifierLivre(Long id, Livre livreModifie) {

        return livreRepository.findById(id)
                .map(livre -> {
                    livre.setTitre(livreModifie.getTitre());
                    livre.setAuteur(livreModifie.getAuteur());
                    livre.setIsbn(livreModifie.getIsbn());
                    livre.setCategorie(livreModifie.getCategorie());
                    livreRepository.save(livre);
                    return true;
                })
                .orElse(false);
    }

    // --- DELETE ---
    public void supprimerLivre(Long id) {
        livreRepository.deleteById(id);
    }

    // --- RECHERCHE ---
    public List<Livre> filtrerEtChercherLivres(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return livreRepository.findAll();
        }

        return livreRepository
                .findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(keyword, keyword);
    }
}