package kingo_bibio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kingo_bibio.model.Exemplaire;
import kingo_bibio.model.StatutExemplaire;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {
    
    // Pour scanner un livre au comptoir
    Optional<Exemplaire> findByCodeBarre(String codeBarre);
    
    // Pour lister tous les exemplaires d'un livre
    List<Exemplaire> findByLivreId(Long livreId);
    
    // Très utile pour les futures réservations : voir s'il reste des exemplaires 'DISPONIBLE' pour un livre
    List<Exemplaire> findByLivreIdAndStatut(Long livreId, StatutExemplaire statut);
}