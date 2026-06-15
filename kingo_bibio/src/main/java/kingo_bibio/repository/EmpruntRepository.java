package kingo_bibio.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kingo_bibio.model.Emprunt;

public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {

    List<Emprunt> findByDateRetourReelleIsNull();

    List<Emprunt> findByDateRetourReelleIsNullAndDateRetourPrevueBefore(LocalDate dateCharniere);
}