package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.registrardto.JornadaRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.JornadaResponseDto;
import com.utn.frvm.prode_api.models.Jornada;

public class JornadaMapper {
    
    public static Jornada toEntity(JornadaRegistrarDto dto){
        Jornada jornada = new Jornada();

        jornada.setNombreJornada(dto.getNombreJornada());
        jornada.setFechaInicio(dto.getFechaInicio());
        jornada.setFechaFin(dto.getFechaFin());
        jornada.setEstaActivo(dto.isActivo());
        
        return jornada;
    }

    public static JornadaResponseDto toDto(Jornada jornada){
        JornadaResponseDto dto = new JornadaResponseDto();

        dto.setIdJornada(jornada.getIdJornada());
        dto.setNombreJornada(jornada.getNombreJornada());
        dto.setFechaInicio(jornada.getFechaInicio());
        dto.setFechaFin(jornada.getFechaFin());
        dto.setEstadoJornada(jornada.getEstadoJornada());
        dto.setPartidos(jornada.getPartidos());
        dto.setEstaActivo(jornada.isEstaActivo());

        return dto;
    }
}
