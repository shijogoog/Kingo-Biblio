package kingo_bibio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kingo_bibio.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

    boolean existsByIsbn(String isbn);

    List<Livre> findByTitreContainingIgnoreCaseOrAuteurContainingIgnoreCase(
        String titre,
        String auteur
    );
}