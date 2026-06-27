package com.utn.frvm.prode_api.dtos.responsedto;

import java.util.List;

public record GrupoResponse(
    Long id,
    String nombre,
    String descripcion,
    UsuarioResponse creador,
    String codigoInvitacion,
    int totalMiembros,
    List<MiembroResponse> miembros
) {
    public record MiembroResponse(Long usuarioId, String nombreCompleto, Integer puntaje) {}
}

