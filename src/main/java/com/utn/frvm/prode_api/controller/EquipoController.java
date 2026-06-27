package com.utn.frvm.prode_api.controller;


import com.utn.frvm.prode_api.dtos.responsedto.RespuestaApi;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponse;
import com.utn.frvm.prode_api.services.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping
    public ResponseEntity<RespuestaApi<List<EquipoResponse>>> listar(
            @RequestParam(required = false) String nombre) {
        List<EquipoResponse> equipos = nombre != null && !nombre.isBlank()
                ? equipoService.buscarPorNombre(nombre)
                : equipoService.listarActivos();
        return ResponseEntity.ok(RespuestaApi.ok(equipos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaApi<EquipoResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(RespuestaApi.ok(equipoService.getById(id)));
    }
}
