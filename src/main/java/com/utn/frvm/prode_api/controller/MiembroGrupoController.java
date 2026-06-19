package com.utn.frvm.prode_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utn.frvm.prode_api.dtos.registrardto.MiembroGrupoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.MiembroGrupoResponseDto;
import com.utn.frvm.prode_api.services.MiembroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/miembroGrupo")
public class MiembroGrupoController {

    private final MiembroGrupoService miembroGrupoService;

    public MiembroGrupoController(MiembroGrupoService miembroGrupoService) {
        this.miembroGrupoService = miembroGrupoService;
    }

    @GetMapping
    public List<MiembroGrupoResponseDto> listar() {
        return miembroGrupoService.listarMiembros();
    }

    @GetMapping("/{id}")
    public MiembroGrupoResponseDto obtenerPorId(@PathVariable Integer id) {
        return miembroGrupoService.buscarPorId(id);
    }

    @PostMapping
    public MiembroGrupoResponseDto crear(@Valid @RequestBody MiembroGrupoRegistrarDto dto) {
        return miembroGrupoService.crearMiembro(dto);
    }
}
