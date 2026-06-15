package kingo_bibio.dto;


import java.time.LocalDate;

public record AlerteRetardDTO(
    Long idEmprunt,
    String nomEtudiant,
    String titreDuLivre,
    LocalDate dateLimiteDepassee,
    Long nombreDeJoursDeRetard,
    Long amendeAccumuleeFCFA
) {}