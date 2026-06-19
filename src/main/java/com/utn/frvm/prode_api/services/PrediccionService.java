package com.utn.frvm.prode_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utn.frvm.prode_api.dtos.registrardto.PrediccionRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponseDto;
import com.utn.frvm.prode_api.mapper.PrediccionMapper;
import com.utn.frvm.prode_api.models.Prediccion;
import com.utn.frvm.prode_api.repositories.PrediccionRepository;

@Service
public class PrediccionService {

    private final PrediccionRepository prediccionRepository;

    public PrediccionService (PrediccionRepository prediccionRepository) {
        this.prediccionRepository = prediccionRepository;
    }

    public List<PrediccionResponseDto> listarPredicciones() {
        return prediccionRepository.findAll()
            .stream()
            .map(PrediccionMapper::toDto)
            .toList();
    }

    public PrediccionResponseDto buscarPorId(Integer id) {
        Prediccion prediccion = prediccionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prediccion no encontrada"));
        return PrediccionMapper.toDto(prediccion);
    }

    public PrediccionResponseDto crearPrediccion(PrediccionRegistrarDto dto) {
        Prediccion prediccion = PrediccionMapper.toEntity(dto);
        Prediccion guardado = prediccionRepository.save(prediccion);
        return PrediccionMapper.toDto(guardado);
    }
}
