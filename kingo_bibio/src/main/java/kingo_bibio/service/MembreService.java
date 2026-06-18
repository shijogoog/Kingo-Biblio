package kingo_bibio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kingo_bibio.model.Membre;
import kingo_bibio.repository.MembreRepository;

@Service
public class MembreService {

    private final MembreRepository membreRepository;

    public MembreService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Transactional(readOnly = true)
    public List<Membre> listerTousLesMembres() {
        return membreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Membre> trouverParId(Long id) {
        return membreRepository.findById(id);
    }

    @Transactional
    public boolean ajouterMembre(Membre membre) {
        // Sécurité : On vérifie si un membre possède déjà cet email
        Optional<Membre> membreExistant = membreRepository.findByEmail(membre.getEmail());
        if (membreExistant.isPresent()) {
            return false; // Email déjà pris
        }
        membreRepository.save(membre);
        return true;
    }

    @Transactional
    public boolean modifierMembre(Long id, Membre detailsMembre) {
        Optional<Membre> membreExistant = membreRepository.findById(id);
        
        if (membreExistant.isPresent()) {
            Membre membreAModifier = membreExistant.get();
            membreAModifier.setNom(detailsMembre.getNom());
            membreAModifier.setEmail(detailsMembre.getEmail());
            // On conserve la date d'inscription d'origine
            
            membreRepository.save(membreAModifier);
            return true;
        }
        return false;
    }

    @Transactional
    public void supprimerMembre(Long id) {
        membreRepository.deleteById(id);
    }
}