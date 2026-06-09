package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.GrupoResponceDto;
import com.utn.frvm.prode_api.models.Grupo;

public class GrupoMapper {
    
    public static Grupo toEntity(GrupoResponceDto dto){

        Grupo grupo = new Grupo();

        grupo.setIdGrupo(dto.getIdGrupo());
        grupo.setNombreGrupo(dto.getNombreGrupo());
        grupo.setCreadorGrupo(dto.getCreadorGrupo());
        grupo.setMiembros(dto.getMiembros());

        return grupo;
    }

    public static GrupoResponceDto toDto(Grupo grupo){

        GrupoResponceDto dto = new GrupoResponceDto();

        dto.setIdGrupo(grupo.getIdGrupo());
        dto.setNombreGrupo(grupo.getNombreGrupo());
        dto.setCreadorGrupo(grupo.getCreadorGrupo());
        dto.setMiembros(grupo.getMiembros());
        
        return dto;
    }
}
