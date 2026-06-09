package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.EquipoResponceDto;
import com.utn.frvm.prode_api.models.Equipo;

public class EquipoMapper {
    
    public static Equipo toEntity(EquipoResponceDto dto){

        Equipo equipo = new Equipo();

        equipo.setIdEquipo(dto.getIdEquipo());
        equipo.setNombreEquipo(dto.getNombreEquipo());
        equipo.setEstaActivo(dto.isEstaActivo());

        return equipo;
    }

    public static EquipoResponceDto toDto(Equipo equipo){

        EquipoResponceDto dto = new EquipoResponceDto();

        dto.setNombreEquipo(equipo.getNombreEquipo());
        dto.setEstaActivo(equipo.isEstaActivo());
        return dto;
    }
}
