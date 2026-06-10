package com.utn.frvm.prode_api.controller;

import com.utn.frvm.prode_api.dtos.registrardto.EquipoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponseDto;
import com.utn.frvm.prode_api.services.EquipoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    private final EquipoService equipoService;
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;

    }
    @GetMapping
    public List<EquipoResponseDto> listar(){
        return equipoService.listarEquipos();
    }
    @GetMapping("/{id}")
    public EquipoResponseDto buscarPorId(@PathVariable Integer id){
        return equipoService.buscarPorId(id);
    }
    @PostMapping
    public EquipoResponseDto crear(@Valid @RequestBody EquipoRegistrarDto dto){
        return equipoService.crear(dto);
    }
}
