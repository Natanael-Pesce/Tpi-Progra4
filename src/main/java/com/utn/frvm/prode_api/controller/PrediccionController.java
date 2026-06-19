package com.utn.frvm.prode_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.utn.frvm.prode_api.dtos.registrardto.PrediccionRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponseDto;
import com.utn.frvm.prode_api.services.PrediccionService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/prediccion")
public class PrediccionController {

    private final PrediccionService prediccionService;

    public PrediccionController(PrediccionService prediccionService){
        this.prediccionService = prediccionService;
    }

    @GetMapping
    public List<PrediccionResponseDto> listar(){
        return prediccionService.listarPredicciones();
    }

    @GetMapping("/{id}")
    public PrediccionResponseDto obtenerPorId(@PathVariable Integer id) {
        return prediccionService.buscarPorId(id);
    }

    @PostMapping
    public PrediccionResponseDto crearPrediccion(@Valid @RequestBody PrediccionRegistrarDto dto) {
        return prediccionService.crearPrediccion(dto);
    }

}
