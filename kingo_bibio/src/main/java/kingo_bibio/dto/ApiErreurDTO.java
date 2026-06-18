package kingo_bibio.dto;

import java.time.LocalDateTime;

public record ApiErreurDTO(
    LocalDateTime timestamp,
    int status,
    String erreur,
    String message,
    String path
) {}