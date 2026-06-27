package com.utn.frvm.prode_api.dtos.responsedto;

import com.utn.frvm.prode_api.utility.Rol;

public record AuthResponse(
    String token,
    String tipo,
    Long usuarioId,
    String nombre,
    String apellido,
    String correo,
    Rol rol
) {}
