package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.RankingItemResponse;
import com.utn.frvm.prode_api.services.RankingService;
import com.utn.frvm.prode_api.services.SesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final SesionService sesionService;

    @GetMapping("/global")
    public ResponseEntity<RespuestaApi<List<RankingItemResponse>>> getRankingGlobal(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(rankingService.getRankingGlobal(usuarioId)));
    }

    @GetMapping("/global/jornada/{jornadaId}")
    public ResponseEntity<RespuestaApi<List<RankingItemResponse>>> getRankingPorJornada(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable Long jornadaId) {
        Long usuarioId = sesionService.usuarioIdDesdeHeader(authorization);
        return ResponseEntity.ok(RespuestaApi.ok(
                rankingService.getRankingPorJornada(jornadaId, usuarioId)));
    }
}

