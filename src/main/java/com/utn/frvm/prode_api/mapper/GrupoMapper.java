package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponseDto;
import com.utn.frvm.prode_api.models.Grupo;

public class GrupoMapper {
    
    public static Grupo toEntity(GrupoResponseDto dto){

        Grupo grupo = new Grupo();

        grupo.setIdGrupo(dto.getIdGrupo());
        grupo.setNombreGrupo(dto.getNombreGrupo());
        grupo.setCreadorGrupo(dto.getCreadorGrupo());
        grupo.setMiembros(dto.getMiembros());

        return grupo;
    }

    public static GrupoResponseDto toDto(Grupo grupo){

        GrupoResponseDto dto = new GrupoResponseDto();

        dto.setIdGrupo(grupo.getIdGrupo());
        dto.setNombreGrupo(grupo.getNombreGrupo());
        dto.setCreadorGrupo(grupo.getCreadorGrupo());
        dto.setMiembros(grupo.getMiembros());
        
        return dto;
    }
}
