package kingo_bibio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kingo_bibio.model.Emprunt;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    // Récupère tous les emprunts dont la date de retour est nulle (emprunts actifs)
    List<Emprunt> findByDateRetourIsNull();

    // Vérifie si un exemplaire (identifié par son code barre) est actuellement emprunté
    boolean existsByCodeBarreAndDateRetourIsNull(String codeBarre);
// Dans EmpruntRepository.java
List<Emprunt> findByMembreId(Long membreId);
    /**
     * Retourne les livres les plus empruntés avec leur nombre d'emprunts.
     * Utilise une requête native car les entités ne sont pas directement reliées.
     */
    @Query(value = "SELECT l.titre, l.auteur, COUNT(e.id) AS nb_emprunts " +
                   "FROM emprunts e " +
                   "JOIN exemplaires ex ON e.code_barre = ex.code_barre " +
                   "JOIN livres l ON ex.livre_id = l.id " +
                   "GROUP BY l.id " +
                   "ORDER BY nb_emprunts DESC",
           nativeQuery = true)
    List<Object[]> trouverLivresLesPlusPopulaires(Pageable pageable); // pageable gère le LIMIT de manière dynamique

    /**
     * Compte le nombre d'emprunts effectués entre deux dates.
     */
    @Query("SELECT COUNT(e) FROM Emprunt e WHERE e.dateEmprunt BETWEEN :debut AND :fin")
    long compterEmpruntsSurPeriode(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}