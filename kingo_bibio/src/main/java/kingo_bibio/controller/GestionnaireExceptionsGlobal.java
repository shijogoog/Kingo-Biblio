package kingo_bibio.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import kingo_bibio.dto.ApiErreurDTO;

@RestControllerAdvice
public class GestionnaireExceptionsGlobal {

    // 1. Attrape les NullPointerException (très fréquent lors des mappings d'entités manquantes)
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiErreurDTO> gererNullPointer(NullPointerException exception, HttpServletRequest request) {
        ApiErreurDTO erreur = new ApiErreurDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Donnée manquante ou incohérente",
                "Une liaison requise (ex: Exemplaire ou Livre) est manquante pour cette ressource. " + exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 2. Attrape les RuntimeException génériques (comme "Livre introuvable", etc.)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErreurDTO> gererRuntimeException(RuntimeException exception, HttpServletRequest request) {
        ApiErreurDTO erreur = new ApiErreurDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erreur Opérationnelle",
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(erreur, HttpStatus.BAD_REQUEST);
    }

    // 3. Attrape TOUTES les autres erreurs imprévues
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErreurDTO> gererErreurGenerique(Exception exception, HttpServletRequest request) {
        ApiErreurDTO erreur = new ApiErreurDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erreur Interne du Serveur",
                "Une erreur imprévue est survenue : " + exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(erreur, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}