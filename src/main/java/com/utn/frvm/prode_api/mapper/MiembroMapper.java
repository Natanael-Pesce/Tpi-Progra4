package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.MiemborGrupoResponceDto;
import com.utn.frvm.prode_api.models.MiembroGrupo;

public class MiembroMapper {
    
    public static MiembroGrupo toEntity(MiemborGrupoResponceDto dto){

        MiembroGrupo miembroGrupo = new MiembroGrupo();

        miembroGrupo.setIdMiembro(dto.getIdMiembroGrupo());
        miembroGrupo.setUsuario(dto.getUsuario());
        miembroGrupo.setGrupo(dto.getGrupo());
        miembroGrupo.setFechaIngreso(dto.getFechaIngreso());

        return miembroGrupo;
    }

    public static MiemborGrupoResponceDto toDto(MiembroGrupo miembroGrupo){
        MiemborGrupoResponceDto dto = new MiemborGrupoResponceDto();

        dto.setIdMiembroGrupo(miembroGrupo.getIdMiembro());
        dto.setUsuario(miembroGrupo.getUsuario());
        dto.setGrupo(miembroGrupo.getGrupo());
        dto.setFechaIngreso(miembroGrupo.getFechaIngreso());
        
        return dto;
    }
}
