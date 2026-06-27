package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.requestdto.PrediccionRequest;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponse;
import com.utn.frvm.prode_api.services.PrediccionService;
import com.utn.frvm.prode_api.services.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/predicciones")
@RequiredArgsConstructor
public class PrediccionController {

    private final PrediccionService prediccionService;
    private final SesionService sesionService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<PrediccionResponse>>> getMisPredicciones(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) Long jornadaId) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        List<PrediccionResponse> predicciones = jornadaId != null
                ? prediccionService.getMisPrediccionesPorJornada(usuarioId, jornadaId)
                : prediccionService.getMisPredicciones(usuarioId);
        return ResponseEntity.ok(RespuestaApi.ok(predicciones));
    }

    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<RespuestaApi<PrediccionResponse>> getPrediccionPropia(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long partidoId) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        Optional<PrediccionResponse> prediccion =
                prediccionService.getPrediccionPropia(usuarioId, partidoId);
        return prediccion
                .map(p -> ResponseEntity.ok(RespuestaApi.ok(p)))
                .orElse(ResponseEntity.ok(RespuestaApi.ok(null)));
    }

    @GetMapping("/partido/{partidoId}/todos")
    public ResponseEntity<RespuestaApi<List<PrediccionResponse>>> getPrediccionesDePartido(
            @PathVariable Long partidoId) {
        return ResponseEntity.ok(RespuestaApi.ok(
                prediccionService.getPrediccionesDePartido(partidoId)));
    }

    @PostMapping
    public ResponseEntity<RespuestaApi<PrediccionResponse>> crear(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody PrediccionRequest req) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        PrediccionResponse response = prediccionService.crear(usuarioId, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Prediccion registrada exitosamente", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaApi<PrediccionResponse>> modificar(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody PrediccionRequest req) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                "Prediccion actualizada",
                prediccionService.modificar(usuarioId, id, req)));
    }
}
