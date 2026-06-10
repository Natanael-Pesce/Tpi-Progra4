package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.PartidoResponceDto;
import com.utn.frvm.prode_api.models.Partido;

public class PartidoMapper {
    
    public static Partido toEntity(PartidoResponceDto dto){
        Partido partido = new Partido();

        partido.setIdEquipoLocal(dto.getIdEquipoLocal());
        partido.setIdEquipoVisitante(dto.getIdEquipoVisitante());
        partido.setEstadoPartido(dto.getEstadoPartido());
        partido.setGolesLocal(dto.getGolesLocal());
        partido.setGolesVisitante(dto.getGolesVisitante());
        partido.setIdJornada(dto.getIdJornada());
        partido.setHoraInicio(dto.getHoraInicio());
        partido.setResultado(dto.getResultado());

        return partido;
    }

    public static PartidoResponceDto toDto(Partido partido){
        PartidoResponceDto dto = new PartidoResponceDto();

        dto.setIdPartido(partido.getIdPartido());
        dto.setIdEquipoLocal(partido.getIdEquipoLocal());
        dto.setIdEquipoVisitante(partido.getIdEquipoVisitante());
        dto.setEstadoPartido(partido.getEstadoPartido());
        dto.setGolesLocal(partido.getGolesLocal());
        dto.setGolesVisitante(partido.getGolesVisitante());
        dto.setHoraInicio(partido.getHoraInicio());
        dto.setIdJornada(partido.getIdJornada());
        dto.setResultado(partido.getResultado());

        return dto;
    }
}
