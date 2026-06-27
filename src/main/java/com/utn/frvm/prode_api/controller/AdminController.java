package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.requestdto.EquipoRequest;
import com.utn.frvm.prode_api.dtos.requestdto.FinalizarPartidoRequest;
import com.utn.frvm.prode_api.dtos.requestdto.JornadaRequest;
import com.utn.frvm.prode_api.dtos.requestdto.PartidoRequest;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponse;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponse;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.config.exeptions.BadRequestExeption;
import com.utn.frvm.prode_api.utility.Rol;
import com.utn.frvm.prode_api.services.EquipoService;
import com.utn.frvm.prode_api.services.JornadaService;
import com.utn.frvm.prode_api.services.PartidoService;
import com.utn.frvm.prode_api.services.SesionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final EquipoService equipoService;
    private final JornadaService jornadaService;
    private final PartidoService partidoService;
    private final SesionService sesionService;

    @PostMapping("/equipos")
    public ResponseEntity<RespuestaApi<EquipoResponse>> crearEquipo(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody EquipoRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Equipo creado", equipoService.crear(req)));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<RespuestaApi<EquipoResponse>> actualizarEquipo(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody EquipoRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(equipoService.actualizar(id, req)));
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<RespuestaApi<Void>> eliminarEquipo(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        validarAdmin(authorization);
        equipoService.eliminar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Equipo eliminado", null));
    }

    @PostMapping("/jornadas")
    public ResponseEntity<RespuestaApi<JornadaResponse>> crearJornada(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody JornadaRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Jornada creada", jornadaService.crear(req)));
    }

    @PutMapping("/jornadas/{id}")
    public ResponseEntity<RespuestaApi<JornadaResponse>> actualizarJornada(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody JornadaRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(jornadaService.actualizar(id, req)));
    }

    @DeleteMapping("/jornadas/{id}")
    public ResponseEntity<RespuestaApi<Void>> eliminarJornada(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        validarAdmin(authorization);
        jornadaService.eliminar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Jornada eliminada", null));
    }

    @PostMapping("/partidos")
    public ResponseEntity<RespuestaApi<PartidoResponse>> crearPartido(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @Valid @RequestBody PartidoRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RespuestaApi.ok("Partido creado", partidoService.crear(req)));
    }

    @PutMapping("/partidos/{id}")
    public ResponseEntity<RespuestaApi<PartidoResponse>> actualizarPartido(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody PartidoRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(partidoService.actualizar(id, req)));
    }

    @DeleteMapping("/partidos/{id}")
    public ResponseEntity<RespuestaApi<Void>> eliminarPartido(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        validarAdmin(authorization);
        partidoService.eliminar(id);
        return ResponseEntity.ok(RespuestaApi.ok("Partido eliminado", null));
    }

    @PatchMapping("/partidos/{id}/iniciar")
    public ResponseEntity<RespuestaApi<PartidoResponse>> iniciarPartido(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id) {
        validarAdmin(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(partidoService.iniciarPartido(id)));
    }

    @PatchMapping("/partidos/{id}/finalizar")
    public ResponseEntity<RespuestaApi<PartidoResponse>> finalizarPartido(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long id,
            @Valid @RequestBody FinalizarPartidoRequest req) {
        validarAdmin(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                "Partido finalizado y puntos calculados",
                partidoService.finalizarPartido(id, req)));
    }

    private void validarAdmin(String authorization) {
        if (sesionService.usuarioDesdeHeader(authorization).getRol() != Rol.ADMIN) {
            throw new BadRequestExeption("Solo un administrador puede realizar esta accion");
        }
    }
}
