package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responcedto.MiemborGrupoResponceDto;
import com.utn.frvm.prode_api.models.MiembroGrupo;

public class MiembroMapper {
    
    public static MiembroGrupo toEntity(MiemborGrupoResponceDto dto){

        MiembroGrupo miembroGrupo = new MiembroGrupo();

        miembroGrupo.setIdMiembro(dto.getIdMiembroGrupo());
        miembroGrupo.setIdUsuario(dto.getIdUsuario());
        miembroGrupo.setIdGrupo(dto.getIdGrupo());
        miembroGrupo.setFechaIngreso(dto.getFechaIngreso());

        return miembroGrupo;
    }

    public static MiemborGrupoResponceDto toDto(MiembroGrupo miembroGrupo){
        MiemborGrupoResponceDto dto = new MiemborGrupoResponceDto();

        dto.setIdMiembroGrupo(miembroGrupo.getIdMiembro());
        dto.setIdUsuario(miembroGrupo.getIdUsuario());
        dto.setIdGrupo(miembroGrupo.getIdGrupo());
        dto.setFechaIngreso(miembroGrupo.getFechaIngreso());
        
        return dto;
    }
}
