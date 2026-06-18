package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.registrardto.MiembroGrupoRegistrarDto;
import com.utn.frvm.prode_api.dtos.responsedto.MiembroGrupoResponseDto;
import com.utn.frvm.prode_api.models.MiembroGrupo;

public class MiembroMapper {
    
    public static MiembroGrupo toEntity(MiembroGrupoRegistrarDto dto){
        MiembroGrupo miembroGrupo = new MiembroGrupo();

        miembroGrupo.setUsuario(dto.getUsuario());
        miembroGrupo.setGrupo(dto.getGrupo());
        miembroGrupo.setFechaIngreso(dto.getFechaIngreso());

        return miembroGrupo;
    }

    public static MiembroGrupoResponseDto toDto(MiembroGrupo miembroGrupo){
        MiembroGrupoResponseDto dto = new MiembroGrupoResponseDto();

        dto.setIdMiembroGrupo(miembroGrupo.getIdMiembro());
        dto.setUsuario(miembroGrupo.getUsuario());
        dto.setGrupo(miembroGrupo.getGrupo());
        dto.setFechaIngreso(miembroGrupo.getFechaIngreso());
        
        return dto;
    }
}
