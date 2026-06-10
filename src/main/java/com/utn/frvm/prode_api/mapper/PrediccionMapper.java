package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.PrediccionResponseDto;
import com.utn.frvm.prode_api.models.Prediccion;

public class PrediccionMapper {
    
    public static Prediccion toEntity(PrediccionResponseDto dto){
        Prediccion prediccion = new Prediccion();

        prediccion.setIdPrediccion(dto.getIdPrediccion());
        prediccion.setUsuarioId(dto.getUsuarioId());
        prediccion.setPartidoId(dto.getPartidoId());
        prediccion.setGolesLocal(dto.getGolesLocal());
        prediccion.setGolesVisitante(dto.getGolesVisitante());
        prediccion.setResultadoPronosticado(dto.getResultadoPronosticado());
        prediccion.setFechaCreacion(dto.getFechaCreacion());
        prediccion.setResultadoPronosticado(dto.getResultadoPronosticado());

        return prediccion;
    }

    public static PrediccionResponseDto toDto(Prediccion prediccion){
        PrediccionResponseDto dto = new PrediccionResponseDto();

        dto.setIdPrediccion(prediccion.getIdPrediccion());
        dto.setUsuarioId(prediccion.getUsuarioId());
        dto.setPartidoId(prediccion.getPartidoId());
        dto.setGolesLocal(prediccion.getGolesLocal());
        dto.setGolesVisitante(prediccion.getGolesVisitante());
        dto.setResultadoPronosticado(prediccion.getResultadoPronosticado());
        dto.setFechaCreacion(prediccion.getFechaCreacion());
        dto.setPuntosObtenidos(prediccion.getPuntosObtenidos());

        return dto;
    }
}
