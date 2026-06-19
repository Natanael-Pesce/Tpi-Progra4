package com.utn.frvm.prode_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utn.frvm.prode_api.services.GrupoService;
import jakarta.validation.Valid;
import com.utn.frvm.prode_api.dtos.registrardto.GrupoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponseDto;
import java.util.List;

@RestController
@RequestMapping("api/grupo")
public class GrupoController {
    
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping
    public List<GrupoResponseDto> listar() {
        return grupoService.listarGrupos();
    }

    @GetMapping("/{id}")
    public GrupoResponseDto buscarPorId(@PathVariable Integer id) {
        return grupoService.buscarPorId(id);
    }

    @PostMapping
    public GrupoResponseDto crearGrupo(@Valid @RequestBody GrupoRegistrarDto dto) {
        return grupoService.crear(dto);
    }
}
