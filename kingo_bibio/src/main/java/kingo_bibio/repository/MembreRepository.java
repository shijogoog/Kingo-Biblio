package kingo_bibio.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kingo_bibio.model.Membre;

public interface MembreRepository extends JpaRepository<Membre, Long> {
    Optional<Membre> findByEmail(String email);
}