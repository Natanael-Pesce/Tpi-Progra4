package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponse;
import com.utn.frvm.prode_api.services.PartidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
@RequiredArgsConstructor
public class PartidoController {

    private final PartidoService partidoService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<PartidoResponse>>> listar(
            @RequestParam(required = false) Long jornadaId) {
        List<PartidoResponse> partidos = jornadaId != null
                ? partidoService.listarPorJornada(jornadaId)
                : partidoService.listarTodos();
        return ResponseEntity.ok(RespuestaApi.ok(partidos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<PartidoResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(RespuestaApi.ok(partidoService.getById(id)));
    }
}
