package com.utn.frvm.prode_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.utn.frvm.prode_api.services.PartidoService;
import jakarta.validation.Valid;
import com.utn.frvm.prode_api.dtos.registrardto.PartidoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponseDto;

@RestController
@RequestMapping("api/partido")
public class PartidoController {
    
    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService){
        this.partidoService = partidoService;
    }

    @GetMapping
    public List<PartidoResponseDto> listar() {
        return partidoService.listarPartidos();
    }

    @GetMapping("/{id}")
    public PartidoResponseDto obtenerPorId(@PathVariable Integer id){
        return partidoService.obtenerPorId(id);
    }

    @PostMapping
    public PartidoResponseDto crearPartido(@Valid @RequestBody PartidoRegistrarDto dto) {
        return partidoService.crearPartido(dto);
    }
}
