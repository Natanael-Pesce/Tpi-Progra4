package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.requestdto.CrearGrupoRequest;
import com.utn.frvm.prode_api.dtos.requestdto.UnirseGrupoRequest;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponse;
import com.utn.frvm.prode_api.dtos.responsedto.RankingItemResponse;
import com.utn.frvm.prode_api.services.GrupoService;
import com.utn.frvm.prode_api.services.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;
    private final SesionService sesionService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<GrupoResponse>>> getMisGrupos(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(grupoService.getMisGrupos(usuarioId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<GrupoResponse>> getById(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(grupoService.getById(id, usuarioId)));
    }

    @PostMapping
    public ResponseEntity<RespuestaApi<GrupoResponse>> crear(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody CrearGrupoRequest req) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Grupo creado exitosamente",
                        grupoService.crear(usuarioId, req)));
    }

    @PostMapping("/unirse")
    public ResponseEntity<RespuestaApi<GrupoResponse>> unirse(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody UnirseGrupoRequest req) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                "Te has unido al grupo exitosamente",
                grupoService.unirse(usuarioId, req)));
    }

    @DeleteMapping("/{id}/abandonar")
    public ResponseEntity<RespuestaApi<Void>> abandonar(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        grupoService.abandonar(id, usuarioId);
        return ResponseEntity.ok(RespuestaApi.ok("Has abandonado el grupo", null));
    }

    @GetMapping("/{id}/ranking")
    public ResponseEntity<RespuestaApi<List<RankingItemResponse>>> getRanking(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(grupoService.getRankingGrupo(id, usuarioId)));
    }
}
