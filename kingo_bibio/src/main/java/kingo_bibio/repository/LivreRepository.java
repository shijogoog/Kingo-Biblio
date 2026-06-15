package kingo_bibio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kingo_bibio.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

    List<Livre> findByAuteurContainingIgnoreCase(String auteur);

    @Query("SELECT l FROM Livre l WHERE LOWER(l.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(l.auteur) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Livre> searchByKeyword(@Param("keyword") String keyword);
}