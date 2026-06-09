package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.PrediccionResponceDto;
import com.utn.frvm.prode_api.models.Prediccion;

public class PrediccionMapper {
    
    public static Prediccion toEntity(PrediccionResponceDto dto){
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

    public static PrediccionResponceDto toDto(Prediccion prediccion){
        PrediccionResponceDto dto = new PrediccionResponceDto();

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
