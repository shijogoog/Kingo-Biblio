package kingo_bibio.dto;

import java.time.LocalDate;

public record HistoriqueEmpruntDTO(
    Long idEmprunt,
    String titreLivre,
    String auteurLivre,
    String codeBarreExemplaire,
    LocalDate dateEmprunt,
    LocalDate dateRetourPrevue,
    LocalDate dateRetourReelle,
    String statut // "EN_COURS" ou "RESTITUÉ"
) {}