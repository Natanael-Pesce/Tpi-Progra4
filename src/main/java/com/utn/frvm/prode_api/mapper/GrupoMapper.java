package com.utn.frvm.prode_api.mapper;

import com.utn.frvm.prode_api.dtos.responsedto.GrupoResponse;
import com.utn.frvm.prode_api.models.Grupo;
import com.utn.frvm.prode_api.models.MiembroGrupo;

import java.util.List;

public class GrupoMapper {

    private GrupoMapper() {
    }

    public static GrupoResponse toResponse(Grupo grupo) {
        List<GrupoResponse.MiembroResponse> miembros = grupo.getMiembros().stream()
                .map(GrupoMapper::toMiembroResponse)
                .toList();

        return new GrupoResponse(
                grupo.getIdGrupo(),
                grupo.getNombre(),
                grupo.getDescripcion(),
                UsuarioMapper.toResponse(grupo.getCreador()),
                grupo.getCodigoInvitacion(),
                miembros.size(),
                miembros);
    }

    private static GrupoResponse.MiembroResponse toMiembroResponse(MiembroGrupo miembro) {
        return new GrupoResponse.MiembroResponse(
                miembro.getUsuario().getIdUsuario(),
                miembro.getUsuario().getNombre() + " " + miembro.getUsuario().getApellido(),
                miembro.getUsuario().getPuntos());
    }
}

