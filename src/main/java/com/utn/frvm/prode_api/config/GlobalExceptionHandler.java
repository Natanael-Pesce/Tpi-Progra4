package com.utn.frvm.prode_api.config;

import com.utn.frvm.prode_api.config.exeptions.*;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RespuestaApi<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RespuestaApi.error(ex.getMessage()));
    }

    @ExceptionHandler(BadRequestExeption.class)
    public ResponseEntity<RespuestaApi<Void>> handleBusiness(BadRequestExeption ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaApi.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaApi<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(RespuestaApi.error(errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RespuestaApi<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(RespuestaApi.error("El recurso ya existe o viola una restriccion de integridad"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RespuestaApi<Void>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(RespuestaApi.error("Acceso denegado. No tienes permisos suficientes."));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<RespuestaApi<Void>> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RespuestaApi.error("No autorizado. Inicia sesion nuevamente."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaApi<Void>> handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(RespuestaApi.error("Error interno del servidor"));
    }
}
