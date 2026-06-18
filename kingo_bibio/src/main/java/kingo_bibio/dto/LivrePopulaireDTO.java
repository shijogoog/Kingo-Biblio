package kingo_bibio.dto;

public record LivrePopulaireDTO(
    String titre,
    String auteur,
    Long nombreEmprunts
) {}