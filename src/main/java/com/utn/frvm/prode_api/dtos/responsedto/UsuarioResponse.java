package com.utn.frvm.prode_api.dtos.responsedto;

import com.utn.frvm.prode_api.utility.Rol;

public record UsuarioResponse(
    Long id,
    String nombre,
    String apellido,
    String correo,
    Integer puntaje,
    Rol rol
) {}
