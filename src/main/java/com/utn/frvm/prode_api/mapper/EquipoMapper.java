package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.registrardto.EquipoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.EquipoResponseDto;
import com.utn.frvm.prode_api.models.Equipo;

public class EquipoMapper {
    
    public static Equipo toEntity(EquipoRegistrarDto dto){

        Equipo equipo = new Equipo();

        equipo.setNombreEquipo(dto.getNombreEquipo());
        equipo.setEstaActivo(true);
        return equipo;
    }

    public static EquipoResponseDto toDto(Equipo equipo){

        EquipoResponseDto dto = new EquipoResponseDto();
        dto.setIdEquipo(equipo.getIdEquipo());
        dto.setNombreEquipo(equipo.getNombreEquipo());
        dto.setEstaActivo(equipo.isEstaActivo());
        return dto;
    }
}
