package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.registrardto.PartidoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.PartidoResponseDto;
import com.utn.frvm.prode_api.models.Partido;

public class PartidoMapper {
    
    public static Partido toEntity(PartidoRegistrarDto dto){
        Partido partido = new Partido();

        partido.setEquipoLocal(dto.getEquipoLocal());
        partido.setEquipoVisitante(dto.getEquipoVisitante());
        partido.setEstadoPartido(dto.getEstadoPartido());
        partido.setJornada(dto.getJornada());
        partido.setHoraInicio(dto.getFechaInicio());

        return partido;
    }

    public static PartidoResponseDto toDto(Partido partido){
        PartidoResponseDto dto = new PartidoResponseDto();

        dto.setIdPartido(partido.getIdPartido());
        dto.setEquipoLocal(partido.getEquipoLocal());
        dto.setEquipoVisitante(partido.getEquipoVisitante());
        dto.setEstadoPartido(partido.getEstadoPartido());
        dto.setGolesLocal(partido.getGolesLocal());
        dto.setGolesVisitante(partido.getGolesVisitante());
        dto.setHoraInicio(partido.getHoraInicio());
        dto.setJornada(partido.getJornada());
        dto.setResultado(partido.getResultado());

        return dto;
    }
}
