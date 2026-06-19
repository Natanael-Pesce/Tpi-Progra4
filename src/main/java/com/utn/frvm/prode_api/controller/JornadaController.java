package com.utn.frvm.prode_api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utn.frvm.prode_api.dtos.registrardto.JornadaRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponseDto;
import com.utn.frvm.prode_api.services.JornadaService;

@RestController
@RequestMapping("api/Jornada")
public class JornadaController {
    
    private final JornadaService jornadaService;

    public JornadaController (JornadaService jornadaService) {
        this.jornadaService = jornadaService;
    }

    @GetMapping
    public List<JornadaResponseDto> listar() {
        return jornadaService.listarJornadas();
    }

    @GetMapping("/{id}")
    public JornadaResponseDto buscarPorId(Integer id) {
        return jornadaService.buscarPorId(id);
    }

    @PostMapping
    public JornadaResponseDto crear (JornadaRegistrarDto dto) {
        return jornadaService.crearJornada(dto);
    }
}
