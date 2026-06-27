package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.ResultadoResponse;
import com.utn.frvm.prode_api.services.ResultadoService;
import com.utn.frvm.prode_api.services.SesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@RequiredArgsConstructor
public class ResultadoController {

    private final ResultadoService resultadoService;
    private final SesionService sesionService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<ResultadoResponse>>> getResultados(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(required = false) Long jornadaId) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                resultadoService.getResultados(usuarioId, jornadaId)));
    }
}