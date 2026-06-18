package kingo_bibio.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kingo_bibio.dto.LivrePopulaireDTO;
import kingo_bibio.repository.EmpruntRepository;

@Service
public class StatistiqueService {

    private final EmpruntRepository empruntRepository;

    public StatistiqueService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    @Transactional(readOnly = true)
    public List<LivrePopulaireDTO> obtenirTop5LivresPopulaires() {
        Pageable topCinq = PageRequest.of(0, 5);
        // La méthode du repository retourne une liste de Object[] : [titre, auteur, nbEmprunts]
        List<Object[]> resultats = empruntRepository.trouverLivresLesPlusPopulaires(topCinq);
        
        // Mapper chaque ligne en LivrePopulaireDTO
        return resultats.stream()
                .map(row -> new LivrePopulaireDTO(
                        (String) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long obtenirNombreEmpruntsCeMois() {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate debutMois = aujourdHui.withDayOfMonth(1);
        LocalDate finMois = aujourdHui.withDayOfMonth(aujourdHui.lengthOfMonth());
        
        return empruntRepository.compterEmpruntsSurPeriode(debutMois, finMois);
    }
}