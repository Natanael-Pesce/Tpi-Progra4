package com.utn.frvm.prode_api.controller;
import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponse;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.services.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jornadas")
@RequiredArgsConstructor
public class JornadaController {

    private final JornadaService jornadaService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<JornadaResponse>>> listar() {
        return ResponseEntity.ok(RespuestaApi.ok(jornadaService.listarTodas()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<JornadaResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(RespuestaApi.ok(jornadaService.getById(id)));
    }

    @GetMapping("/{id}/partidos")
    public ResponseEntity<RespuestaApi<List<PartidoResponse>>> getPartidos(@PathVariable Long id) {
        return ResponseEntity.ok(RespuestaApi.ok(jornadaService.getPartidosPorJornada(id)));
    }
}
