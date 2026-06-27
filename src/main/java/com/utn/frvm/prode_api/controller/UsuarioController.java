package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.requestdto.EditarPerfilRequest;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.EstadisticasResponse;
import com.utn.frvm.prode_api.dtos.responsedto.UsuarioResponse;
import com.utn.frvm.prode_api.services.SesionService;
import com.utn.frvm.prode_api.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final SesionService sesionService;

    @GetMapping("/perfil")
    public ResponseEntity<RespuestaApi<UsuarioResponse>> getPerfil(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(usuarioService.getPerfil(usuarioId)));
    }

    @PutMapping("/perfil")
    public ResponseEntity<RespuestaApi<UsuarioResponse>> editarPerfil(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody EditarPerfilRequest req) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                "Perfil actualizado exitosamente",
                usuarioService.editarPerfil(usuarioId, req)));
    }

    @GetMapping("/{id}/perfil")
    public ResponseEntity<RespuestaApi<UsuarioResponse>> getPerfilPublico(@PathVariable Long id) {
        return ResponseEntity.ok(RespuestaApi.ok(usuarioService.getPerfilPublico(id)));
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<RespuestaApi<EstadisticasResponse>> getEstadisticas(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(usuarioService.getEstadisticas(usuarioId)));
    }
}
